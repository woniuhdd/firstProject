package com.trade.model;

import java.math.BigDecimal;
import java.util.Date;

public class TradeInvoicenew {
	
	//alias
	public static final String TABLE_ALIAS = "TradeInvoicenew";
	
	//columns START
	/**
	 * @Fields id:id
	 */
	private String id;
	
	/**
	 * @Fields invoiceid:invoiceid
	 */
	private String invoiceid;
	
	/**
	 * @Fields invoicecode:invoicecode
	 */
	private String invoicecode;
	
	/**
	 * @Fields invoicedate:invoicedate
	 */
	private Date invoicedate;
	
	/**
	 * @Fields totaltaxprice:totaltaxprice
	 */
	private BigDecimal totaltaxprice;
	
	/**
	 * @Fields invoicetype:invoicetype
	 */
	private String invoicetype;
	
	/**
	 * @Fields fatherid:fatherid
	 */
	private String fatherid;
	
	/**
	 * @Fields companyidSell:companyidSell
	 */
	private String companyidSell;
	
	/**
	 * @Fields companynameSell:companynameSell
	 */
	private String companynameSell;
	
	/**
	 * @Fields selltaxpayernumber:selltaxpayernumber
	 */
	private String selltaxpayernumber;
	
	/**
	 * @Fields sellremark:sellremark
	 */
	private String sellremark;
	
	/**
	 * @Fields companyidBuy:companyidBuy
	 */
	private String companyidBuy;
	
	/**
	 * @Fields companynameBuy:companynameBuy
	 */
	private String companynameBuy;
	
	/**
	 * @Fields buytaxpayernumber:buytaxpayernumber
	 */
	private String buytaxpayernumber;
	
	/**
	 * @Fields buyremark:buyremark
	 */
	private String buyremark;
	
	/**
	 * @Fields changeperson:changeperson
	 */
	private String changeperson;
	
	/**
	 * @Fields changetime:changetime
	 */
	private Date changetime;
	
	/**
	 * @Fields changereason:changereason
	 */
	private String changereason;
	
	/**
	 * @Fields invoiceimgid:invoiceimgid
	 */
	private String invoiceimgid;
	
	/**
	 * @Fields adduserid:adduserid
	 */
	private String adduserid;
	
	/**
	 * @Fields addtime:addtime
	 */
	private Date addtime;
	
	/**
	 * @Fields submitstatus:submitstatus
	 */
	private String submitstatus;
	
	/**
	 * @Fields submittime:submittime
	 */
	private Date submittime;
	
	/**
	 * @Fields invoiceimgid2:invoiceimgid2
	 */
	private String invoiceimgid2;
	
	/**
	 * @Fields flag:flag
	 */
	private BigDecimal flag;
	
	/**
	 * @Fields invoicepayserialid:结算单ID
	 */
	private Object invoicepayserialid;
	
	/**
	 * @Fields isshow:isshow
	 */
	private BigDecimal isshow;
	
	//columns END

	public TradeInvoicenew(){
	}

	public TradeInvoicenew(String id){
		this.id = id;
	}

	
	public void setId(String id){
		this.id = id;
	}
	
	public String getId(){
		return id;
	}
	
	public void setInvoiceid(String invoiceid){
		this.invoiceid = invoiceid;
	}
	
	public String getInvoiceid(){
		return invoiceid;
	}
	
	public void setInvoicecode(String invoicecode){
		this.invoicecode = invoicecode;
	}
	
	public String getInvoicecode(){
		return invoicecode;
	}
	
	public void setInvoicedate(Date invoicedate){
		this.invoicedate = invoicedate;
	}
	
	public Date getInvoicedate(){
		return invoicedate;
	}
	
	public void setTotaltaxprice(BigDecimal totaltaxprice){
		this.totaltaxprice = totaltaxprice;
	}
	
	public BigDecimal getTotaltaxprice(){
		return totaltaxprice;
	}
	
	public void setInvoicetype(String invoicetype){
		this.invoicetype = invoicetype;
	}
	
	public String getInvoicetype(){
		return invoicetype;
	}
	
	public void setFatherid(String fatherid){
		this.fatherid = fatherid;
	}
	
	public String getFatherid(){
		return fatherid;
	}
	
	public void setCompanyidSell(String companyidSell){
		this.companyidSell = companyidSell;
	}
	
	public String getCompanyidSell(){
		return companyidSell;
	}
	
	public void setCompanynameSell(String companynameSell){
		this.companynameSell = companynameSell;
	}
	
	public String getCompanynameSell(){
		return companynameSell;
	}
	
	public void setSelltaxpayernumber(String selltaxpayernumber){
		this.selltaxpayernumber = selltaxpayernumber;
	}
	
	public String getSelltaxpayernumber(){
		return selltaxpayernumber;
	}
	
	public void setSellremark(String sellremark){
		this.sellremark = sellremark;
	}
	
	public String getSellremark(){
		return sellremark;
	}
	
	public void setCompanyidBuy(String companyidBuy){
		this.companyidBuy = companyidBuy;
	}
	
	public String getCompanyidBuy(){
		return companyidBuy;
	}
	
	public void setCompanynameBuy(String companynameBuy){
		this.companynameBuy = companynameBuy;
	}
	
	public String getCompanynameBuy(){
		return companynameBuy;
	}
	
	public void setBuytaxpayernumber(String buytaxpayernumber){
		this.buytaxpayernumber = buytaxpayernumber;
	}
	
	public String getBuytaxpayernumber(){
		return buytaxpayernumber;
	}
	
	public void setBuyremark(String buyremark){
		this.buyremark = buyremark;
	}
	
	public String getBuyremark(){
		return buyremark;
	}
	
	public void setChangeperson(String changeperson){
		this.changeperson = changeperson;
	}
	
	public String getChangeperson(){
		return changeperson;
	}
	
	public void setChangetime(Date changetime){
		this.changetime = changetime;
	}
	
	public Date getChangetime(){
		return changetime;
	}
	
	public void setChangereason(String changereason){
		this.changereason = changereason;
	}
	
	public String getChangereason(){
		return changereason;
	}
	
	public void setInvoiceimgid(String invoiceimgid){
		this.invoiceimgid = invoiceimgid;
	}
	
	public String getInvoiceimgid(){
		return invoiceimgid;
	}
	
	public void setAdduserid(String adduserid){
		this.adduserid = adduserid;
	}
	
	public String getAdduserid(){
		return adduserid;
	}
	
	public void setAddtime(Date addtime){
		this.addtime = addtime;
	}
	
	public Date getAddtime(){
		return addtime;
	}
	
	public void setSubmitstatus(String submitstatus){
		this.submitstatus = submitstatus;
	}
	
	public String getSubmitstatus(){
		return submitstatus;
	}
	
	public void setSubmittime(Date submittime){
		this.submittime = submittime;
	}
	
	public Date getSubmittime(){
		return submittime;
	}
	
	public void setInvoiceimgid2(String invoiceimgid2){
		this.invoiceimgid2 = invoiceimgid2;
	}
	
	public String getInvoiceimgid2(){
		return invoiceimgid2;
	}
	
	public void setFlag(BigDecimal flag){
		this.flag = flag;
	}
	
	public BigDecimal getFlag(){
		return flag;
	}
	
	public void setInvoicepayserialid(Object invoicepayserialid){
		this.invoicepayserialid = invoicepayserialid;
	}
	
	public Object getInvoicepayserialid(){
		return invoicepayserialid;
	}
	
	public void setIsshow(BigDecimal isshow){
		this.isshow = isshow;
	}
	
	public BigDecimal getIsshow(){
		return isshow;
	}

}