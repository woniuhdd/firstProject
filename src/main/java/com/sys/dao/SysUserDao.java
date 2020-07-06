package com.sys.dao;

import com.common.dao.GenericDao;
import com.sys.model.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 内容摘要：TODO
 *
 * @author baohaitao
 * @date
 */
@Mapper
public interface SysUserDao extends GenericDao<SysUser, Integer> {

    /**
     * 登录时根据登录帐号查询用户信息
     * @param username
     * @return
     */
    SysUser getUserByUsername(String username);

    List<SysUser> getData(Map<String, Object> map);
}
