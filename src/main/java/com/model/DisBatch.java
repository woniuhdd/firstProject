package com.model;

public class DisBatch {
    private String companyDistributeId;
    private String distributeId;
    private  String batchcode;
    private Integer batchcount;


    public void setCompanyDistributeId(String companyDistributeId) {
        this.companyDistributeId = companyDistributeId;
    }

    public String getCompanyDistributeId() {
        return companyDistributeId;
    }

    public void setDistributeId(String distributeId) {
        this.distributeId = distributeId;
    }

    public String getDistributeId() {
        return distributeId;
    }

    public void setBatchcode(String batchcode) {
        this.batchcode = batchcode;
    }

    public String getBatchcode() {
        return batchcode;
    }


    public void setBatchcount(Integer batchcount) {
        this.batchcount = batchcount;
    }


    public Integer getBatchcount() {
        return batchcount;
    }
}
