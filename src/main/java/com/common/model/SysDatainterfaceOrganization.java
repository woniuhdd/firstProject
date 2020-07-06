package com.common.model;

import java.io.Serializable;
import java.util.Date;

public class SysDatainterfaceOrganization implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//alias
	public static final String TABLE_ALIAS = "SysDatainterfaceOrganization";
	
	//columns START
	/**
	 * @Fields id:id
	 */
	private Integer id;
	
	/**
	 * @Fields orgId:org_id
	 */
	private String orgId;
	
	/**
	 * @Fields orgUserName:机构用户名
	 */
	private String orgUserName;
	
	/**
	 * @Fields departmentUserName:部门用户名
	 */
	private String departmentUserName;
	
	/**
	 * @Fields orgType:机构类型
	 */
	private Integer orgType;
	
	/**
	 * @Fields departmentId:部门编号
	 */
	private Integer departmentId;
	
	/**
	 * @Fields publicKey:公钥
	 */
	private String publicKey;
	
	/**
	 * @Fields privateKey:私钥
	 */
	private String privateKey;
	
	/**
	 * @Fields ipv4:ipv4
	 */
	private String ipv4;
	
	/**
	 * @Fields accessToken:访问令牌
	 */
	private String accessToken;
	
	/**
	 * @Fields expireTime:令牌失效时间
	 */
	private Date expireTime;
	
	private String expireTimeStr;
	
	/**
	 * @Fields isUsing:是否有效(0:无效,1:有效)
	 */
	private Integer isUsing;
	
	/**
	 * @Fields lastAccessTime:最后访问时间
	 */
	private Date lastAccessTime;
	
	private String lastAccessTimeStr;
	
	/**
	 * @Fields lastUpdateUserId:最后一次更新记录人id
	 */
	private String lastUpdateUserId;
	
	/**
	 * @Fields lastUpdateUserName:最后一次更新记录人
	 */
	private String lastUpdateUserName;
	
	/**
	 * @Fields lastUpdateTime:最后一次更新记录时间
	 */
	private Date lastUpdateTime;

	private String fatherId;
	
	//columns END
	

	
	
	public SysDatainterfaceOrganization(){
	}
	




	public SysDatainterfaceOrganization(Integer id){
		this.id = id;
	}

	
	public void setId(Integer id){
		this.id = id;
	}
	
	public Integer getId(){
		return id;
	}
	
	public void setOrgId(String orgId){
		this.orgId = orgId;
	}
	
	public String getOrgId(){
		return orgId;
	}
	
	public void setOrgUserName(String orgUserName){
		this.orgUserName = orgUserName;
	}
	
	public String getOrgUserName(){
		return orgUserName;
	}
	
	public void setDepartmentUserName(String departmentUserName){
		this.departmentUserName = departmentUserName;
	}
	
	public String getDepartmentUserName(){
		return departmentUserName;
	}
	
	public void setOrgType(Integer orgType){
		this.orgType = orgType;
	}
	
	public Integer getOrgType(){
		return orgType;
	}
	
	public void setDepartmentId(Integer departmentId){
		this.departmentId = departmentId;
	}
	
	public Integer getDepartmentId(){
		return departmentId;
	}
	
	public void setPublicKey(String publicKey){
		this.publicKey = publicKey;
	}
	
	public String getPublicKey(){
		return publicKey;
	}
	
	public void setPrivateKey(String privateKey){
		this.privateKey = privateKey;
	}
	
	public String getPrivateKey(){
		return privateKey;
	}
	
	public void setIpv4(String ipv4){
		this.ipv4 = ipv4;
	}
	
	public String getIpv4(){
		return ipv4;
	}
	
	public void setAccessToken(String accessToken){
		this.accessToken = accessToken;
	}
	
	public String getAccessToken(){
		return accessToken;
	}
	
	public void setExpireTime(Date expireTime){
		this.expireTime = expireTime;
	}
	
	public Date getExpireTime(){
		return expireTime;
	}
	
	public void setIsUsing(Integer isUsing){
		this.isUsing = isUsing;
	}
	
	public Integer getIsUsing(){
		return isUsing;
	}
	
	public void setLastAccessTime(Date lastAccessTime){
		this.lastAccessTime = lastAccessTime;
	}
	
	public Date getLastAccessTime(){
		return lastAccessTime;
	}
	
	public void setLastUpdateUserId(String lastUpdateUserId){
		this.lastUpdateUserId = lastUpdateUserId;
	}
	
	public String getLastUpdateUserId(){
		return lastUpdateUserId;
	}
	
	public void setLastUpdateUserName(String lastUpdateUserName){
		this.lastUpdateUserName = lastUpdateUserName;
	}
	
	public String getLastUpdateUserName(){
		return lastUpdateUserName;
	}
	
	public void setLastUpdateTime(Date lastUpdateTime){
		this.lastUpdateTime = lastUpdateTime;
	}
	
	public Date getLastUpdateTime(){
		return lastUpdateTime;
	}


	public void setLastAccessTimeStr(String lastAccessTimeStr) {
		this.lastAccessTimeStr = lastAccessTimeStr;
	}

	public String getFatherId() {
		return fatherId;
	}

	public void setFatherId(String fatherId) {
		this.fatherId = fatherId;
	}
}