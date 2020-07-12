package com.model;

import java.util.Map;

public class DistributeInfo {

    private String companyDistributeId;
    private String orderDetailId;
    private String disTime;
    private Map<String,Object> batchList;
    private  Integer discount;
    private String distributeId;

    private String firstInviceID;
    private  String middleInviceID;
    private String secondInviceID;

    public String getCompanyDistributeId() {
        return companyDistributeId;
    }

    public void setCompanyDistributeId(String companyDistributeId) {
        this.companyDistributeId = companyDistributeId;
    }

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getDisTime() {
        return disTime;
    }

    public void setDisTime(String disTime) {
        this.disTime = disTime;
    }

    public Map<String, Object> getBatchList() {
        return batchList;
    }

    public void setBatchList(Map<String, Object> batchList) {
        this.batchList = batchList;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getDistributeId() {
        return distributeId;
    }

    public void setDistributeId(String distributeId) {
        this.distributeId = distributeId;
    }

    public String getFirstInviceID() {
        return firstInviceID;
    }

    public void setFirstInviceID(String firstInviceID) {
        this.firstInviceID = firstInviceID;
    }

    public String getMiddleInviceID() {
        return middleInviceID;
    }

    public void setMiddleInviceID(String middleInviceID) {
        this.middleInviceID = middleInviceID;
    }

    public String getSecondInviceID() {
        return secondInviceID;
    }

    public void setSecondInviceID(String secondInviceID) {
        this.secondInviceID = secondInviceID;
    }
}
