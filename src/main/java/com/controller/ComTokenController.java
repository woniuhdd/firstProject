package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.common.controller.ValidateUser;
import com.common.utils.JwtTokenUtil;
import com.enums.Result;
import com.enums.ResultCode;
import com.validator.FormatDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(ComTokenController.ACTION_PATH)
public class ComTokenController {
    protected static final String ACTION_PATH = "/compInterface";
    private static final Logger log = LoggerFactory.getLogger(ComTokenController.class);

    @Autowired
    private ValidateUser validateUser;


    @RequestMapping("/getToken")
    @ResponseBody
    public JSONObject adminLogin(HttpServletResponse response, String username, String password) {
        // 这里模拟测试, 默认登录成功，返回用户ID和角色信息
        String userId = UUID.randomUUID().toString();
        JSONObject returnJsonObj = new JSONObject();
        //验证用户名密码
        Map<String, Object> map = validateUser.validateUser(username,password);

        Integer resultCode = (Integer) map.get("resultCode");
        if (resultCode.equals(ResultCode.SUCCESS.getCode())) {
            // 创建token
            String token = JwtTokenUtil.createJWT(username, password);
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar nowTime = Calendar.getInstance();
            returnJsonObj.put("resultCode", ResultCode.SUCCESS.getCode());
            returnJsonObj.put("resultMsg", ResultCode.SUCCESS.getMessage());
            returnJsonObj.put("accessToken", token);
            returnJsonObj.put("expiresIn", sf.format(nowTime.getTime()));
            returnJsonObj.put("currentTime", new FormatDate().toFullFormat(new Date()));
        }else {
            returnJsonObj.put("resultCode", map.get("resultCode"));
            returnJsonObj.put("resultMsg", map.get("resultMsg"));
        }
        return returnJsonObj;
    }

    @GetMapping("/users")
    public Result userList() {
        log.info("### 查询所有用户列表 ###");
        return Result.SUCCESS();
    }
}