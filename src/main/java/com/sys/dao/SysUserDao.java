package com.sys.dao;

import com.common.dao.GenericDao;
import com.sys.model.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserDao  extends GenericDao<SysUser,String>{

    SysUser getUserByUsername(String username);
}
