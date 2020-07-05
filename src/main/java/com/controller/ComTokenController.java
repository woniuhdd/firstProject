//package com.controller;
//
//import com.alibaba.fastjson.JSONObject;
//import com.annotation.IRequestRecord;
//import com.common.controller.CheckDataAccess;
//import com.enums.ReturnCode;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//@Controller
//@RequestMapping(ComTokenController.ACTION_PATH)
//public class ComTokenController {
//    protected static final String ACTION_PATH = "/v1/companyInterface";
//    private static final Logger log = Logger.getLogger(ComTokenController.class);
//
//    @Autowired
//    private HttpServletRequest request;
//    @Autowired
//    private HttpServletResponse response;
//    @Autowired
//    private HttpSession session;
//
//    @Autowired
//    private CheckDataAccess checkDataAccess;
//
//
//    @IRequestRecord(isRecord = true, interfaceUrl = ComTokenController.ACTION_PATH + "/accessToken/getToken")
//    @RequestMapping(value = "/accessToken/getToken", method = {RequestMethod.POST})
//    @ResponseBody
//    public JSONObject getToken(String orgUserName, String params) {
////        return checkDataAccess.getToken(orgUserName, params,0);
//        JSONObject returnJsonObj = new JSONObject();
//        returnJsonObj.put("returnCode", ReturnCode.PARAMETERS_FORMAT_ERROR.getKey());
//        returnJsonObj.put("returnMsg", ReturnCode.PARAMETERS_FORMAT_ERROR.getValue());
//        returnJsonObj.put("accessToken", "");
//        returnJsonObj.put("expiresIn", "");
//        return returnJsonObj;
//    }
//
//    @IRequestRecord(isRecord = true, interfaceUrl = ComTokenController.ACTION_PATH + "/accessToken/test/getToken")
//    @RequestMapping(value = "/accessToken/test/getToken", method = {RequestMethod.POST})
//    @ResponseBody
//    public JSONObject getTestToken(String orgUserName, String params) {
//        return checkDataAccess.getToken(orgUserName, params,1);
//    }
//
//}