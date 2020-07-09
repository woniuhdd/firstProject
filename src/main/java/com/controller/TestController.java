package com.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(TestController.ACTION_PATH)
public class TestController {
    protected static final String ACTION_PATH = "/test";
    private static final Logger log = Logger.getLogger(TestController.class);

    /**
     * 连通性测试：测试是否能调用到接口
     * @return
     */
    @RequestMapping(value = "/testConnect", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject sayHello() {
        JSONObject returnJsonObj = new JSONObject();
        returnJsonObj.put("resultCode", "1");
        returnJsonObj.put("resultMsg", "业务执行成功");
        return returnJsonObj;
    }



}