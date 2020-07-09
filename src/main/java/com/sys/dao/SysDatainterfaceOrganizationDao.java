package com.sys.dao;

import com.common.dao.GenericDao;
import com.sys.model.SysDatainterfaceOrganization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysDatainterfaceOrganizationDao extends GenericDao<SysDatainterfaceOrganization, Integer> {

	List<String> getData(@Param("orgId") String orgId);

}