package com.sys.service;

import com.common.service.GenericManager;
import com.sys.model.SysDatainterfaceOrganization;

import java.util.List;

public interface SysDatainterfaceOrganizationManager extends GenericManager<SysDatainterfaceOrganization, Integer> {
	
	List<String> getData(String orgId);

}