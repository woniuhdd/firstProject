package com.model;

public class ComInterfaceImage {
    private String companyPrimaryKey;
    private String invoicePrimaryID;
    private String imgPrimaryID;
    private String imageType;
    private String imgUrl;


    public void setCompanyPrimaryKey(String companyPrimaryKey) {
        this.companyPrimaryKey = companyPrimaryKey;
    }

    public String getCompanyPrimaryKey() {
        return companyPrimaryKey;
    }

    public void setInvoicePrimaryID(String invoicePrimaryID) {
        this.invoicePrimaryID = invoicePrimaryID;
    }

    public String getInvoicePrimaryID() {
        return invoicePrimaryID;
    }

    public String getImgPrimaryID() {
        return imgPrimaryID;
    }

    public void setImgPrimaryID(String imgPrimaryID) {
        this.imgPrimaryID = imgPrimaryID;
    }

    public String getImageType() {
        return imageType;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

}
