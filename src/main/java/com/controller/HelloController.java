package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.enums.ReturnCode;
import com.sys.model.SysUser;
import com.sys.service.SysUserManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(HelloController.ACTION_PATH)
public class HelloController {
    protected static final String ACTION_PATH = "/hello";
    private static final Logger log = Logger.getLogger(HelloController.class);

    @Autowired
    private SysUserManager sysUserManager;

    @RequestMapping(value = "/sayHello", method = {RequestMethod.POST})
    @ResponseBody
    public SysUser sayHello() {
        SysUser sysUser = sysUserManager.getById("ZBZX");
        return sysUser;
//        return "returnJsonObj";
    }



}