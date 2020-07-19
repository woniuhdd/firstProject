package com.model;

import java.math.BigDecimal;

public class ComInterfaceInvoice {

    /**
     *企业发票主键
     */
    private  String companyPrimaryKey;
    /**
     *发票号码
     */
    private  String invoiceId;
    /**
     *发票代码
     */
    private String invoiceCode;
    /**
     *销方
     */
    private String saleId;

    /**
     *销方名称
     */
    private String saleName;

    /**
     * 销方纳税识别号
     */
    private String saleTaxPayerNumber;
    /**
     * 销方备注
     */
    private String saleRemarks;
    /**
     *购方
     */
    private String  buyId;

    /**
     *购方
     */
    private String  buyName;
    /**
     * 购方纳税识别号
     */
    private String buyTaxPayerNumber;
    /**
     * 购方备注
     */
    private String buyRemarks;

    /**
     * 开票日期
     */
    private String invoiceDate;
    /**
     * 发票类型
     */
    private Integer invoiceType;

    /**
     * 价格合计
     */
    private BigDecimal totaltaxprice;

    /**
     * 发票所属企业
     */
    private String orgId;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public void setCompanyPrimaryKey(String companyPrimaryKey) {
        this.companyPrimaryKey = companyPrimaryKey;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public void setBuyId(String buyId) {
        this.buyId = buyId;
    }

    public void setBuyName(String buyName) { this.buyName = buyName; }

    public void setBuyTaxPayerNumber(String buyTaxPayerNumber) {
        this.buyTaxPayerNumber = buyTaxPayerNumber;
    }

    public void setBuyRemarks(String buyRemarks) {
        this.buyRemarks = buyRemarks;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public void setSaleName(String saleName) { this.saleName = saleName; }

    public void setSaleRemarks(String saleRemarks) {
        this.saleRemarks = saleRemarks;
    }

    public void setSaleTaxPayerNumber(String saleTaxPayerNumber) {
        this.saleTaxPayerNumber = saleTaxPayerNumber;
    }

    public void setTotaltaxprice(BigDecimal totaltaxprice) {
        this.totaltaxprice = totaltaxprice;
    }

    public String getSaleTaxPayerNumber() {
        return saleTaxPayerNumber;
    }

    public BigDecimal getTotaltaxprice() {
        return totaltaxprice;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public String getBuyId() {
        return buyId;
    }
    public String getBuyName() { return buyName; }


    public String getBuyRemarks() {
        return buyRemarks;
    }

    public String getBuyTaxPayerNumber() {
        return buyTaxPayerNumber;
    }

    public String getCompanyPrimaryKey() {
        return companyPrimaryKey;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public String getSaleId() {
        return saleId;
    }

    public String getSaleName() { return saleName; }

    public String getSaleRemarks() {
        return saleRemarks;
    }

}
