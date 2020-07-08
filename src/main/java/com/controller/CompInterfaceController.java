package com.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(CompInterfaceController.ACTION_PATH)
public class CompInterfaceController {
    private static final Logger log = Logger.getLogger(CompInterfaceController.class);

    protected static final String ACTION_PATH = "/v1/companyInterface";

    /**
     * 内容摘要：获取采购订单数据
     *
     * @param accessToken 授权令牌, startTime 订单开始时间, endTime 订单结束时间, currentPageNumber 分页数
     * @return com.alibaba.fastjson.JSONObject
     * @author baohaitao
     * @date 2019/5/13 11:17
     */
    @RequestMapping(value = "/drug/purchaseOrder/getOrder", method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject getOrder(String accessToken, String startTime, String endTime, String currentPageNumber) {
        JSONObject resultMap = new JSONObject();
        return resultMap;
    }

}
