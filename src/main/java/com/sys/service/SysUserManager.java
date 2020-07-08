package com.sys.service;

import com.common.service.GenericManager;
import com.common.utils.Pagination;
import com.sys.model.SysUser;

public interface SysUserManager extends GenericManager<SysUser, String> {
	// 扩展接口

    /**
     * 登录时根据登录帐号查询用户信息
     * @param username
     * @return
     */
    SysUser getUserByUsername(String username);

    Pagination getData(Pagination page);
}