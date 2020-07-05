//package com.common.controller;
//
//import com.alibaba.fastjson.JSONObject;
//import com.common.model.SysDatainterfaceOrganization;
//import com.common.service.SysDatainterfaceOrganizationManager;
//import com.common.utils.Pagination;
//import com.controller.ComTokenController;
//import com.enums.ErrorCodeCompany;
//import com.enums.ReturnCode;
//import com.enums.Status;
//import com.sys.model.SysUser;
//import com.sys.service.SysUserManager;
//import com.validator.DESTool;
//import com.validator.FormatDate;
//import com.validator.UrlUtil;
//import org.apache.commons.lang.StringUtils;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//@Component
//public class CheckDataAccess {
//    private static final Logger log = Logger.getLogger(CheckDataAccess.class);
//
//    @Autowired
//    private SysDatainterfaceOrganizationManager sysDatainterfaceOrganizationManager;
//    @Autowired
//    private SysUserManager sysUserManager;
//
//    @Value("${interface.page.pageSize}")
//    private String pageSize;
//
//    @Value("${interface.inputDataCountLimit}")
//    private String defaultInputDataCountLimit;
//
//    @Value("${com.identity.passWord}")
//    private String passWord;
//
//    public JSONObject getToken(String orgUserName, String params, Integer isTest) {
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("orgUserName", orgUserName);
//        JSONObject returnJsonObj = new JSONObject();
//
//        if (StringUtils.isEmpty(orgUserName) || StringUtils.isEmpty(params)) {
//            returnJsonObj.put("returnCode", ReturnCode.PARAMETERS_FORMAT_ERROR.getKey());
//            returnJsonObj.put("returnMsg", ReturnCode.PARAMETERS_FORMAT_ERROR.getValue());
//            returnJsonObj.put("accessToken", "");
//            returnJsonObj.put("expiresIn", "");
//            return returnJsonObj;
//        }
//
//        SysDatainterfaceOrganization sysDatainterfaceOrganization = new SysDatainterfaceOrganization();
//        Pagination pageMap = new Pagination();
//        pageMap.getConditions().putAll(map);
//        Pagination pagination = sysDatainterfaceOrganizationManager.getList(pageMap);// 查询接口配置表
//        List<SysDatainterfaceOrganization> conList = (List<SysDatainterfaceOrganization>) pagination.getRows();
//        if (conList.size() > 0) {
//            sysDatainterfaceOrganization = conList.get(0);
//            Boolean bool = false;
//            bool = sysDatainterfaceOrganization.getIsUsing().equals(1);
//            if (!bool) {
//                log.info("isUser 0");
//                returnJsonObj.put("returnCode", ReturnCode.UNAUTHORIZED.getKey());
//                returnJsonObj.put("returnMsg", ReturnCode.UNAUTHORIZED.getValue());
//                returnJsonObj.put("accessToken", "");
//                returnJsonObj.put("expiresIn", "");
//                return returnJsonObj;
//            }
//            try {
//                Map<String, Object> paramMap = UrlUtil.getUrlParams(params);
//
//                SysUser user = sysUserManager.getUserByUsername(orgUserName);
//                String encodedPwd = (String) paramMap.get("secret");
//
//
//                if (user == null) {
//                    log.info("user == null");
//                    bool = false;
//                    returnJsonObj.put("returnCode", ReturnCode.UNAUTHORIZED.getKey());
//                    returnJsonObj.put("returnMsg", ReturnCode.UNAUTHORIZED.getValue());
//                    returnJsonObj.put("accessToken", "");
//                    returnJsonObj.put("expiresIn", "");
//                    return returnJsonObj;
//                } else {
//                    //是否启用
//                    if (user.getUserstatus().equals(Status.invalid.getKey())) {  //  账户是启用
//                        log.info("user.getIsUsing().equals(Status.invalid.getKey())");
//                        bool = false;
//                        returnJsonObj.put("returnCode", ReturnCode.UNAUTHORIZED.getKey());
//                        returnJsonObj.put("returnMsg", ReturnCode.UNAUTHORIZED.getValue());
//                        returnJsonObj.put("accessToken", "");
//                        returnJsonObj.put("expiresIn", "");
//                        return returnJsonObj;
//                    }
//                    //密码是否相等
//                    if (isTest.equals(1)) {
//                        if (!encodedPwd.equals(passWord)) {
//                            bool = false;
//                            returnJsonObj.put("returnCode", ReturnCode.ORG_PWD_ERROR.getKey());
//                            returnJsonObj.put("returnMsg", ReturnCode.ORG_PWD_ERROR.getValue());
//                            returnJsonObj.put("accessToken", "");
//                            returnJsonObj.put("expiresIn", "");
//                            return returnJsonObj;
//                        }
//                    } else if (!DESTool.encryptMD5(encodedPwd,true).equals(user.getUserpwd())) { // 密码是ok
//                        bool = false;
//                        returnJsonObj.put("returnCode", ReturnCode.ORG_PWD_ERROR.getKey());
//                        returnJsonObj.put("returnMsg", ReturnCode.ORG_PWD_ERROR.getValue());
//                        returnJsonObj.put("accessToken", "");
//                        returnJsonObj.put("expiresIn", "");
//                        return returnJsonObj;
//                    }
//                }
//
//            } catch (Exception e1) {
//                log.error("Failed to getToken", e1);
//                bool = false;
//            }
//            if (bool) {
//                try {
//                    //取密码
//                    Map<String, Object> paramMap = UrlUtil.getUrlParams(params);
//                    String encodedPwd = isTest == 1 ? (String) paramMap.get("secret") : DESTool.encryptMD5((String) paramMap.get("secret"),true);
//                    Calendar nowTime = Calendar.getInstance();
//                    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//                    String accessToken = DESTool.encrypt(orgUserName + "|" + encodedPwd + "|" + sf.format(nowTime.getTime()) + "|" + isTest, DESTool.PASSWORD_SECRET);
//                    nowTime.add(Calendar.DAY_OF_MONTH, 1);
//
//                    returnJsonObj.put("returnCode", ReturnCode.SUCCESS.getKey());
//                    returnJsonObj.put("returnMsg", ReturnCode.SUCCESS.getValue());
//                    returnJsonObj.put("accessToken", accessToken);
//                    returnJsonObj.put("expiresIn", sf.format(nowTime.getTime()));
//                    returnJsonObj.put("currentTime", new FormatDate().toFullFormat(new Date()));
//
//                } catch (Exception e) {
//                    log.error("Failed to getToken", e);
//                }
//            } else {
//                returnJsonObj.put("errorCode", ErrorCodeCompany.DATA_STATUS_ERROR.getKey());
//                returnJsonObj.put("errorMsg", ErrorCodeCompany.DATA_STATUS_ERROR.getValue());
//                returnJsonObj.put("accessToken", "");
//                returnJsonObj.put("expiresIn", "");
//                return returnJsonObj;
//            }
//        } else {
//            returnJsonObj.put("returnCode", ReturnCode.UNAUTHORIZED.getKey());
//            returnJsonObj.put("returnMsg", ReturnCode.UNAUTHORIZED.getValue());
//            returnJsonObj.put("accessToken", "");
//            returnJsonObj.put("expiresIn", "");
//        }
//
//        return returnJsonObj;
//    }
//}
