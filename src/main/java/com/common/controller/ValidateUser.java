package com.common.controller;

import com.enums.ResultCode;
import com.enums.UserStatus;
import com.sys.model.SysUser;
import com.sys.service.SysUserManager;
import com.validator.DESTool;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ValidateUser {
    @Autowired
    private SysUserManager sysUserManager;
    @Value("${user.password}")
    private String userPassword;

    public  Map<String, Object> validateUser(String userName, String password) {
        Map<String, Object> map = new HashMap<>();

        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)){
            map.put("resultCode", ResultCode.PARAM_IS_BLANK.getCode());
            map.put("resultMsg", ResultCode.PARAM_IS_BLANK.getMessage());
            return map;
        }

        //验证用户（状态、用户名、密码）
        SysUser sysUser = sysUserManager.getUserByUsername(userName);
        //用户不存在
        if (sysUser == null){
            map.put("resultCode", ResultCode.USER_NOT_EXIST.getCode());
            map.put("resultMsg", ResultCode.USER_NOT_EXIST.getMessage());
            return map;
        }
        //用户已禁用
        if (sysUser.getUserstatus().equals(UserStatus.invalid.getKey())){
            map.put("resultCode", ResultCode.USER_ACCOUNT_FORBIDDEN.getCode());
            map.put("resultMsg", ResultCode.USER_ACCOUNT_FORBIDDEN.getMessage());
            return map;
        }

        //验证密码
//        if (!DESTool.encryptMD5(password,true).equals(sysUser.getUserpwd())){
//            map.put("resultCode", ResultCode.USER_LOGIN_ERROR.getCode());
//            map.put("resultMsg", ResultCode.USER_LOGIN_ERROR.getMessage());
//            return map;
//        }
        //验证密码
        if (!password.equals(userPassword)){
            map.put("resultCode", ResultCode.USER_LOGIN_ERROR.getCode());
            map.put("resultMsg", ResultCode.USER_LOGIN_ERROR.getMessage());
            return map;
        }
        map.put("resultCode", ResultCode.SUCCESS.getCode());
        map.put("resultMsg", ResultCode.SUCCESS.getMessage());
        return map;
    }
}
