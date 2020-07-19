package com.trade.model;

import java.math.BigDecimal;
import java.util.Date;

public class TradeGoodslist {
	
	//alias
	public static final String TABLE_ALIAS = "TradeGoodslist";
	
	//columns START
	/**
	 * @Fields id:id
	 */
	private String id;
	
	/**
	 * @Fields batchid:batchid
	 */
	private String batchid;
	
	/**
	 * @Fields drugvaliditydate:drugvaliditydate
	 */
	private BigDecimal drugvaliditydate;
	
	/**
	 * @Fields count:count
	 */
	private Integer count;
	
	/**
	 * @Fields drugid:drugid
	 */
	private String drugid;
	
	/**
	 * @Fields invoiceid:invoiceid
	 */
	private String invoiceid;
	
	/**
	 * @Fields adduserid:adduserid
	 */
	private String adduserid;
	
	/**
	 * @Fields addtime:addtime
	 */
	private Date addtime;
	
	/**
	 * @Fields changeperson:changeperson
	 */
	private String changeperson;
	
	/**
	 * @Fields changereason:changereason
	 */
	private String changereason;
	
	/**
	 * @Fields changetime:changetime
	 */
	private Date changetime;
	
	/**
	 * @Fields drugvaliditydate1:drugvaliditydate1
	 */
	private Date drugvaliditydate1;
	
	//columns END

	public TradeGoodslist(){
	}

	public TradeGoodslist(String id){
		this.id = id;
	}

	
	public void setId(String id){
		this.id = id;
	}
	
	public String getId(){
		return id;
	}
	
	public void setBatchid(String batchid){
		this.batchid = batchid;
	}
	
	public String getBatchid(){
		return batchid;
	}
	
	public void setDrugvaliditydate(BigDecimal drugvaliditydate){
		this.drugvaliditydate = drugvaliditydate;
	}
	
	public BigDecimal getDrugvaliditydate(){
		return drugvaliditydate;
	}
	
	public void setCount(Integer count){
		this.count = count;
	}
	
	public Integer getCount(){
		return count;
	}
	
	public void setDrugid(String drugid){
		this.drugid = drugid;
	}
	
	public String getDrugid(){
		return drugid;
	}
	
	public void setInvoiceid(String invoiceid){
		this.invoiceid = invoiceid;
	}
	
	public String getInvoiceid(){
		return invoiceid;
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
	
	public void setChangeperson(String changeperson){
		this.changeperson = changeperson;
	}
	
	public String getChangeperson(){
		return changeperson;
	}
	
	public void setChangereason(String changereason){
		this.changereason = changereason;
	}
	
	public String getChangereason(){
		return changereason;
	}
	
	public void setChangetime(Date changetime){
		this.changetime = changetime;
	}
	
	public Date getChangetime(){
		return changetime;
	}
	
	public void setDrugvaliditydate1(Date drugvaliditydate1){
		this.drugvaliditydate1 = drugvaliditydate1;
	}
	
	public Date getDrugvaliditydate1(){
		return drugvaliditydate1;
	}

}