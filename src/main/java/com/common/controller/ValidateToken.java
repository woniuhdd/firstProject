package com.common.controller;

import com.alibaba.fastjson.JSONObject;
import com.common.utils.Base64Util;
import com.common.utils.JwtTokenUtil;
import com.enums.ResultCode;
import com.sys.model.SysInterfacePrivilege;
import com.sys.model.SysUser;
import com.sys.service.SysUserManager;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ValidateToken {
    @Autowired
    private ValidateUser validateUser;
    @Autowired
    private SysUserManager sysUserManager;
    @Autowired
    private HttpServletRequest request;
    @Value("${interface.Privilege}")
    private String interfacePrivilege;

    public Map<String, Object> validateToken(String token) {
        Map<String, Object> map = new HashMap<>();
        List<SysInterfacePrivilege>  sysInterfacePrivilegeList= JSONObject.parseArray(interfacePrivilege, SysInterfacePrivilege.class);

        //验证token数据格式
        if (StringUtils.isEmpty(token)){
            map.put("resultCode", ResultCode.PERMISSION_TOKEN_INVALID.getCode());
            map.put("resultMsg", ResultCode.PERMISSION_TOKEN_INVALID.getMessage());
            return map;
        }

        Claims claims =  JwtTokenUtil.parseJWT(token);
        String username = (String) claims.get("sub");
        String password = Base64Util.decode((String) claims.get("password"));

        //验证tocken是否过期
        Date now = new Date(System.currentTimeMillis());
        Date endTime = claims.getExpiration();
        Date startTime = claims.getNotBefore();


        if (now.before(startTime)) {
            map.put("resultCode", ResultCode.PERMISSION_SIGNATURE_NOVALID.getCode());
            map.put("resultMsg", ResultCode.PERMISSION_SIGNATURE_NOVALID.getMessage());
            return map;
        }
        if (now.after(endTime)) {
            map.put("resultCode", ResultCode.PERMISSION_TOKEN_EXPIRED.getCode());
            map.put("resultMsg", ResultCode.PERMISSION_TOKEN_EXPIRED.getMessage());
            return map;
        }

        //验证用户名密码
        Map<String, Object> userMap = validateUser.validateUser(username,password);
        Integer resultCode = (Integer) userMap.get("resultCode");
        if (!resultCode.equals(ResultCode.SUCCESS.getCode())) {
            map.put("resultCode", userMap.get("resultCode"));
            map.put("resultMsg", userMap.get("resultMsg"));
            return map;
        }
        SysUser sysUser = sysUserManager.getUserByUsername(username);

        //验证是否有接口访问权限
//        String urlPath = request.getServletPath();
//        Map<String ,Object> params = new HashMap<>();
//        params.put("orgId",sysUser.getOrgid());
//        params.put("urlPath",urlPath);
//        List<SysInterfacePrivilege> sysInterfacePrivilegeList = sysInterfacePrivilegeManager.getListByParams(params);
//        if (sysInterfacePrivilegeList.size() != 1){
//            map.put("resultCode", ResultCode.PERMISSION_UNAUTHORISE.getCode());
//            map.put("resultMsg", ResultCode.PERMISSION_UNAUTHORISE.getMessage());
//            return map;
//        }else {
//            if ("0".equals(sysInterfacePrivilegeList.get(0).getIsUsing())){
//                map.put("resultCode", ResultCode.PERMISSION_UNAUTHORISE.getCode());
//                map.put("resultMsg", ResultCode.PERMISSION_UNAUTHORISE.getMessage());
//                return map;
//            }
//        }

        //验证是否有接口访问权限
        String urlPath = request.getServletPath();
        boolean privilege = false;
        for (SysInterfacePrivilege sysInterfacePrivilege : sysInterfacePrivilegeList){
            if (sysUser.getOrgid().equals(sysInterfacePrivilege.getOrgId()) && urlPath.equals(sysInterfacePrivilege.getUrlPath())){
                privilege = true;
            }
        }
        if (!privilege){
            map.put("resultCode", ResultCode.PERMISSION_UNAUTHORISE.getCode());
            map.put("resultMsg", ResultCode.PERMISSION_UNAUTHORISE.getMessage());
            return map;
        }

        map.put("orgId",sysUser.getOrgid());
        map.put("userId", sysUser.getUserid());
        map.put("userName", sysUser.getUsername());
        map.put("resultCode", ResultCode.SUCCESS.getCode());
        map.put("resultMsg", ResultCode.SUCCESS.getMessage());
        return map;
    }
}
