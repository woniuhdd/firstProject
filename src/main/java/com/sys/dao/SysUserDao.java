package com.sys.dao;

import com.common.dao.GenericDao;
import com.sys.model.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysUserDao  extends GenericDao<SysUser,String>{

    SysUser getUserByUsername(@Param("userId") String username);
}
