package com.sys;

import java.math.BigDecimal;
import java.util.Date;

public class SysInterfacePrivilege {

	//alias
	public static final String TABLE_ALIAS = "SysInterfacePrivilege";

	//columns START
	private String interfacePrivilegeId;
	private String orgId;
	private String orgName;
	private String urlPath;
	private String interfaceName;
	private String isUsing;

	public SysInterfacePrivilege(){
	}

	public SysInterfacePrivilege(String interfacePrivilegeId) {
		this.interfacePrivilegeId = interfacePrivilegeId;
	}

	public String getInterfacePrivilegeId() {
		return interfacePrivilegeId;
	}

	public void setInterfacePrivilegeId(String interfacePrivilegeId) {
		this.interfacePrivilegeId = interfacePrivilegeId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getUrlPath() {
		return urlPath;
	}

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getIsUsing() {
		return isUsing;
	}

	public void setIsUsing(String isUsing) {
		this.isUsing = isUsing;
	}
}