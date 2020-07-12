package com.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

public class ValidateResult {
    /**
     * 验证信息
     */
    private String msg;
    /**
     * 验证是否通过
     */
    private boolean success;
    /**
     * 返回的jsonObject
     */
    private JSONObject jsonObject;
    /**
     * 返回的map
     */
    private Map<String, Object> map;
    /**
     * 返回的json array
     */
    private JSONArray jsonArray;
    /**
     * 企业配送接口信息封装集合
     */
    private List<DistributeInfo> distributeInfoList;

    /**
     * 企业响应订单接口明细集合
     */
    private List<String> orderDetailIdList;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public List<DistributeInfo> getDistributeInfoList() {
        return distributeInfoList;
    }

    public void setDistributeInfoList(List<DistributeInfo> distributeInfoList) {
        this.distributeInfoList = distributeInfoList;
    }

    public List<String> getOrderDetailIdList() {
        return orderDetailIdList;
    }

    public void setOrderDetailIdList(List<String> orderDetailIdList) {
        this.orderDetailIdList = orderDetailIdList;
    }
}