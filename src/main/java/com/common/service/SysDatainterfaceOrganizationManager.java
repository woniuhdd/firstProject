package com.common.service;

import com.common.model.SysDatainterfaceLastRequest;
import com.common.model.SysDatainterfaceOrganization;

import java.util.List;
import java.util.Map;

public interface SysDatainterfaceOrganizationManager extends GenericManager<SysDatainterfaceOrganization, Integer> {
	// 扩展接口

	/**
	 *
	 * @param
	 * @return
	 */
	int add(SysDatainterfaceOrganization sdo, List<SysDatainterfaceLastRequest> reList);
	/**
	 *
	 * @param
	 * @return
	 */
	int add(List<SysDatainterfaceOrganization> sdoList, List<SysDatainterfaceLastRequest> reList);

	/**
	 * 根据id修改不为空的数据
	 * @param
	 * @return
	 */
	int updateBySelective(SysDatainterfaceOrganization sdo, List<SysDatainterfaceLastRequest> reList);


	
	List<String> getData(Map<String, Object> params);

}