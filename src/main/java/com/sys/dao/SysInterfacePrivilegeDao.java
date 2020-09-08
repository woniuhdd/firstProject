package com.sys.dao;

import com.common.dao.GenericDao;
import com.sys.model.SysInterfacePrivilege;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysInterfacePrivilegeDao extends GenericDao<SysInterfacePrivilege, String> {
}