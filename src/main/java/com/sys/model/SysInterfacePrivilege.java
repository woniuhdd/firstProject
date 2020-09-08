package com.sys.model;

import java.util.Date;
import java.math.BigDecimal;

/**
 *
 * @Since 2010-2020
 * @Description: TODO
 * @author ***
 * @date 2020-09-08 15:37:39
 *
 */
public class SysInterfacePrivilege {
	
	//alias
	public static final String TABLE_ALIAS = "接口权限配置表";
	
	//columns START
	/**
	 * @Fields interfacePrivilegeId:主键
	 */
	private String interfacePrivilegeId;
	
	/**
	 * @Fields orgId:企业ID
	 */
	private String orgId;
	
	/**
	 * @Fields orgName:企业名称
	 */
	private String orgName;
	
	/**
	 * @Fields urlPath:接口访问路径
	 */
	private String urlPath;
	
	/**
	 * @Fields interfaceName:接口名称
	 */
	private String interfaceName;
	
	/**
	 * @Fields isUsing:是否有权访问（0：否，1：是）
	 */
	private String isUsing;
	
	//columns END

	public SysInterfacePrivilege(){
	}

	public SysInterfacePrivilege(String interfacePrivilegeId){
		this.interfacePrivilegeId = interfacePrivilegeId;
	}

	
	public void setInterfacePrivilegeId(String interfacePrivilegeId){
		this.interfacePrivilegeId = interfacePrivilegeId;
	}
	
	public String getInterfacePrivilegeId(){
		return interfacePrivilegeId;
	}
	
	public void setOrgId(String orgId){
		this.orgId = orgId;
	}
	
	public String getOrgId(){
		return orgId;
	}
	
	public void setOrgName(String orgName){
		this.orgName = orgName;
	}
	
	public String getOrgName(){
		return orgName;
	}
	
	public void setUrlPath(String urlPath){
		this.urlPath = urlPath;
	}
	
	public String getUrlPath(){
		return urlPath;
	}
	
	public void setInterfaceName(String interfaceName){
		this.interfaceName = interfaceName;
	}
	
	public String getInterfaceName(){
		return interfaceName;
	}
	
	public void setIsUsing(String isUsing){
		this.isUsing = isUsing;
	}
	
	public String getIsUsing(){
		return isUsing;
	}

}