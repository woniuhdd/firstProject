package com.model;

public class ComInterfaceSaleList {
    /**
     *企业发票主键
     */
    private  String companyPrimaryKey;
    /**
     * @Fields invoicePrimaryKeyId:发票唯一主键
     */
    private String invoicePrimaryID;

    /**
     * @Fields invoiceId:发票号码
     */
    private String invoiceID;

    /**
     * @Fields invoiceCode:发票代码
     */
    private String invoiceCode;

    /**
     * @Fields drugId:药品编码
     */
    private String goodsID;

    /**
     * @Fields batchCode:批号
     */
    private String batchCode;

    /**
     * @Fields periodTime:效期
     */
    private String periodDate;

    /**
     * @Fields purchaseCount:采购数量
     */
    private Integer saleNumber;


    public String getCompanyPrimaryKey() {
        return companyPrimaryKey;
    }

    public Integer getSaleNumber() {
        return saleNumber;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public String getGoodsID() {
        return goodsID;
    }

    public void setCompanyPrimaryKey(String companyPrimaryKey) {
        this.companyPrimaryKey = companyPrimaryKey;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public void setGoodsID(String drugId) {
        this.goodsID = drugId;
    }

    public void setSaleNumber(Integer saleNumber) {
        this.saleNumber = saleNumber;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public String getInvoicePrimaryID() {
        return invoicePrimaryID;
    }

    public void setInvoicePrimaryID(String invoicePrimaryID) {
        this.invoicePrimaryID = invoicePrimaryID;
    }

    public String getPeriodDate() {
        return periodDate;
    }

    public void setPeriodDate(String periodDate) {
        this.periodDate = periodDate;
    }
}
