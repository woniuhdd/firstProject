package com.sys.service.impl;

import com.common.service.impl.GenericManagerImpl;
import com.common.utils.Pagination;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sys.dao.SysUserDao;
import com.sys.model.SysUser;
import com.sys.service.SysUserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserManagerImpl extends GenericManagerImpl<SysUser,String> implements SysUserManager  {
	// 扩展接口实现
    @Autowired
    private SysUserDao dao;

    @Override
    public SysUser getUserByUsername(String username) {
        return dao.getUserByUsername(username);
    }

    @Override
    public Pagination getData(Pagination page) {
        PageHelper.startPage(page.getPage(), page.getCount(), page.getOrderby());
        Page<SysUser> models = (Page<SysUser>) dao.getListByParams(page.getConditions());
        page.setRows(models);
        page.setRecords(models.getTotal());
        return page;
    }
}