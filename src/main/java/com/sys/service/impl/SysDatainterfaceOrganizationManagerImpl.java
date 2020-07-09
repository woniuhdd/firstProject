package com.sys.service.impl;

import com.common.service.impl.GenericManagerImpl;
import com.sys.dao.SysDatainterfaceOrganizationDao;
import com.sys.model.SysDatainterfaceOrganization;
import com.sys.service.SysDatainterfaceOrganizationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysDatainterfaceOrganizationManagerImpl extends GenericManagerImpl<SysDatainterfaceOrganization, Integer> implements SysDatainterfaceOrganizationManager {

	@Autowired
	private SysDatainterfaceOrganizationDao dao;

	@Override
	public List<String> getData(String orgId) {
		return dao.getData(orgId);
	}

}