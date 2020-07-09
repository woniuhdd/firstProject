package com.common.controller;

import com.common.utils.Base64Util;
import com.common.utils.JwtTokenUtil;
import com.enums.ResultCode;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class ValidateToken {
    @Autowired
    private ValidateUser validateUser;

    public Map<String, Object> validateToken(String token) {
        Map<String, Object> map = new HashMap<>();

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

        map.put("userName", username);
        map.put("resultCode", ResultCode.SUCCESS.getCode());
        map.put("resultMsg", ResultCode.SUCCESS.getMessage());
        return map;
    }
}
