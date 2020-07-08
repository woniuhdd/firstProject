package com.common.controller;

import com.common.model.SysDatainterfaceOrganization;
import com.common.service.SysDatainterfaceOrganizationManager;
import com.common.utils.Pagination;
import com.enums.ReturnCode;
import com.sys.model.SysUser;
import com.sys.service.SysUserManager;
import com.validator.DESTool;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class validateToken {
    @Autowired
    private SysDatainterfaceOrganizationManager sysDatainterfaceOrganizationManager;
    @Autowired
    private SysUserManager sysUserManager;

    public Map<String, Object> validateAccessToken(String accessToken) {
        Map<String, Object> map = new HashMap<>();

        if (StringUtils.isNotEmpty(accessToken)) {
            //解析令牌,规格为 机构编码+机构密码+当前日期yyyy-MM-dd
            String accessTokenStr = DESTool.decrypt(accessToken, DESTool.PASSWORD_SECRET);
            String[] tokenArr = accessTokenStr.split("\\|");

            String orgUserName = "";
            String requsetTime = "";

            //判断解析的数据是否为空
            if (tokenArr.length > 0) {
                orgUserName = tokenArr[0];
                requsetTime = tokenArr[2];
            } else {
                //判断令牌无效
                map.put("returnCode", ReturnCode.TOKEN_INVALID.getKey());
                map.put("returnMsg", ReturnCode.TOKEN_INVALID.getValue());
                return map;
            }
            map.put("orgUserName", orgUserName);
            SysDatainterfaceOrganization sysDatainterfaceOrganization = new SysDatainterfaceOrganization();
            Pagination pageMap = new Pagination();
            pageMap.setCount(200);
            pageMap.getConditions().putAll(map);
            Pagination pagination = sysDatainterfaceOrganizationManager.getList(pageMap);
            List<SysDatainterfaceOrganization> conList = (List<SysDatainterfaceOrganization>) pagination.getRows();
            if (conList.size() > 0) {
                sysDatainterfaceOrganization = conList.get(0);
                Boolean bool = false;
                Boolean isBool = false;

                isBool = sysDatainterfaceOrganization.getIsUsing().equals(1);
                if (!isBool) {
                    map.put("returnCode", ReturnCode.UNAUTHORIZED.getKey());
                    map.put("returnMsg", ReturnCode.UNAUTHORIZED.getValue());
                }

                Calendar nowTime = Calendar.getInstance();
                SimpleDateFormat sdf    =new SimpleDateFormat("yyyy-MM-dd");
                DateFormat df =new SimpleDateFormat("yyyy-MM-dd");
                try {
                    bool = DateUtils.isSameDay(df.parse(requsetTime), df.parse(sdf.format(nowTime.getTime())));

                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    bool=false;
                }

                if (!bool) {
                    map.put("returnCode", ReturnCode.TOKEN_INVALID.getKey());
                    map.put("returnMsg", ReturnCode.TOKEN_INVALID.getValue());
                }
                if (bool && isBool) {
                    map.clear();
                    map.put("returnCode", ReturnCode.SUCCESS.getKey());
                    map.put("returnMsg", ReturnCode.SUCCESS.getValue());
                    Map<String, Object> params = new HashMap<>();
                    params.put("userid", sysDatainterfaceOrganization.getOrgUserName());
                    Pagination pageMapT = new Pagination();
                    pageMapT.getConditions().putAll(params);
                    sysUserManager.getData(pageMapT);
                    List<SysUser> userList = (List<SysUser>) pageMapT.getRows();
                    if (userList.size() > 0) {
                        SysUser  sysUser=userList.get(0);
                        map.put("orgId",sysUser.getOrgid());
                        map.put("userId", sysUser.getUserid());
                        map.put("userName", sysUser.getUsername());
                        map.put("type", sysUser.getUsertype());
                    } else {
                        map.put("returnCode", ReturnCode.UNAUTHORIZED.getKey());
                        map.put("returnMsg", ReturnCode.UNAUTHORIZED.getValue());
                    }
                }
            } else {
                map.put("returnCode", ReturnCode.TOKEN_INVALID.getKey());
                map.put("returnMsg", ReturnCode.TOKEN_INVALID.getValue());
            }
        } else {
            map.put("returnCode", ReturnCode.TOKEN_INVALID.getKey());
            map.put("returnMsg", ReturnCode.TOKEN_INVALID.getValue());
        }
        return map;
    }
}
