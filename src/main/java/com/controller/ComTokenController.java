package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.common.annotation.JwtIgnore;
import com.common.model.Audience;
import com.common.utils.JwtTokenUtil;
import com.enums.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping(ComTokenController.ACTION_PATH)
public class ComTokenController {
    protected static final String ACTION_PATH = "/compInterface";
    private static final Logger log = LoggerFactory.getLogger(ComTokenController.class);

//    @Autowired
//    private CheckDataAccess checkDataAccess;
    @Autowired
    private Audience audience;

//    @RequestMapping(value = "/accessToken/getToken", method = {RequestMethod.POST})
//    @ResponseBody
//    public JSONObject getToken(String orgUserName, String params) {
//        return checkDataAccess.getToken(orgUserName, params,0);
//    }
//
//    @RequestMapping(value = "/accessToken/test/getToken", method = {RequestMethod.POST})
//    @ResponseBody
//    public JSONObject getTestToken(String orgUserName, String params) {
//        return checkDataAccess.getToken(orgUserName, params,1);
//    }



    @PostMapping("/login")
    @JwtIgnore
    public Result adminLogin(HttpServletResponse response, String username, String password) {
        // 这里模拟测试, 默认登录成功，返回用户ID和角色信息
        String userId = UUID.randomUUID().toString();
        String role = "admin";

        // 创建token
        String token = JwtTokenUtil.createJWT(userId, username, role, audience);
        log.info("### 登录成功, token={} ###", token);
        // 将token放在响应头
        response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY, JwtTokenUtil.TOKEN_PREFIX + token);
        // 将token响应给客户端
        JSONObject result = new JSONObject();
        result.put("token", token);
        return Result.SUCCESS(result);
    }

    @GetMapping("/users")
    public Result userList() {
        log.info("### 查询所有用户列表 ###");
        return Result.SUCCESS();
    }
}