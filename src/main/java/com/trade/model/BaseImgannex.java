package com.trade.model;

import java.util.Date;

public class BaseImgannex {
	
	//alias
	public static final String TABLE_ALIAS = "BaseImgannex";
	
	//columns START
	/**
	 * @Fields imgannexid:imgannexid
	 */
	private String imgannexid;
	
	/**
	 * @Fields imgannextype:imgannextype
	 */
	private String imgannextype;
	
	/**
	 * @Fields comid:comid
	 */
	private String comid;
	
	/**
	 * @Fields comregid:comregid
	 */
	private String comregid;
	
	/**
	 * @Fields imgannexname:imgannexname
	 */
	private String imgannexname;
	
	/**
	 * @Fields imgannexurl:imgannexurl
	 */
	private String imgannexurl;
	
	/**
	 * @Fields imgannexstatus:imgannexstatus
	 */
	private String imgannexstatus;
	
	/**
	 * @Fields imgannexremark:imgannexremark
	 */
	private String imgannexremark;
	
	/**
	 * @Fields dataclassid:dataclassid
	 */
	private String dataclassid;
	
	/**
	 * @Fields adduserid:adduserid
	 */
	private String adduserid;
	
	/**
	 * @Fields addtime:addtime
	 */
	private Date addtime;
	
	/**
	 * @Fields narrowimgurl:narrowimgurl
	 */
	private String narrowimgurl;
	
	/**
	 * @Fields folderid:文件夹编号
	 */
	private String folderid;
	
	//columns END

	public BaseImgannex(){
	}

	public BaseImgannex(String imgannexid){
		this.imgannexid = imgannexid;
	}

	
	public void setImgannexid(String imgannexid){
		this.imgannexid = imgannexid;
	}
	
	public String getImgannexid(){
		return imgannexid;
	}
	
	public void setImgannextype(String imgannextype){
		this.imgannextype = imgannextype;
	}
	
	public String getImgannextype(){
		return imgannextype;
	}
	
	public void setComid(String comid){
		this.comid = comid;
	}
	
	public String getComid(){
		return comid;
	}
	
	public void setComregid(String comregid){
		this.comregid = comregid;
	}
	
	public String getComregid(){
		return comregid;
	}
	
	public void setImgannexname(String imgannexname){
		this.imgannexname = imgannexname;
	}
	
	public String getImgannexname(){
		return imgannexname;
	}
	
	public void setImgannexurl(String imgannexurl){
		this.imgannexurl = imgannexurl;
	}
	
	public String getImgannexurl(){
		return imgannexurl;
	}
	
	public void setImgannexstatus(String imgannexstatus){
		this.imgannexstatus = imgannexstatus;
	}
	
	public String getImgannexstatus(){
		return imgannexstatus;
	}
	
	public void setImgannexremark(String imgannexremark){
		this.imgannexremark = imgannexremark;
	}
	
	public String getImgannexremark(){
		return imgannexremark;
	}
	
	public void setDataclassid(String dataclassid){
		this.dataclassid = dataclassid;
	}
	
	public String getDataclassid(){
		return dataclassid;
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
	
	public void setNarrowimgurl(String narrowimgurl){
		this.narrowimgurl = narrowimgurl;
	}
	
	public String getNarrowimgurl(){
		return narrowimgurl;
	}
	
	public void setFolderid(String folderid){
		this.folderid = folderid;
	}
	
	public String getFolderid(){
		return folderid;
	}

}