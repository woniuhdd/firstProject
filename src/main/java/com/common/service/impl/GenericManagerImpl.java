package com.common.service.impl;

import com.common.dao.GenericDao;
import com.common.model.SaveInfoInterface;
import com.common.model.UpdateInfoInterface;
import com.common.service.GenericManager;
import com.common.utils.Pagination;
import com.common.utils.SessionUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sys.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class GenericManagerImpl<T, PK extends Serializable> implements GenericManager<T, PK> {
    @Autowired
    private GenericDao<T, PK> dao;

    public <T> T getDao() {
        return (T) dao;
    }

    @Override
    public T getById(PK id) {
        return dao.getById(id);
    }

    @Override
    public int countByParams(Map<String, Object> params) {
        return dao.countByParams(params);
    }

    @Override
    public List<T> getListByParams(Map<String, Object> params) {
        return dao.getListByParams(params);
    }

    @Override
    public Pagination getList(Pagination page) {
        PageHelper.startPage(page.getPage(), page.getCount(), page.getOrderby());
        Page<T> models = (Page<T>) dao.getListByParams(page.getConditions());
        page.setRows(models);
        page.setRecords(models.getTotal());
        return page;
    }

    @Override
    public boolean save(T model) {
        if (model instanceof SaveInfoInterface) {
            model = this.formatSaveModel(model);
        }
        return dao.save(model) > 0;
    }

    @Override
    public boolean saveBatch(List<T> models) {
        for (T model : models) {
            model = this.formatSaveModel(model);
        }
        return dao.saveBatch(models) > 0;
    }


    @Override
    public boolean updateById(T model) {
        if (model instanceof UpdateInfoInterface) {
            model = this.formatUpdModel(model);
        }
        return dao.updateById(model) > 0;
    }


    @Override
    public boolean updateBySelective(T model) {
        if (model instanceof UpdateInfoInterface) {
            model = this.formatUpdModel(model);
        }
        return dao.updateBySelective(model) > 0;
    }

    @Override
    public boolean deleteById(PK id) {
        return dao.deleteById(id) > 0;
    }


    private <M> M formatSaveModel(M model) {
        SysUser user = SessionUtils.getSysUser();
        if (user != null) {
            ((SaveInfoInterface) model).setAddUserId(user.getUserid());
            ((SaveInfoInterface) model).setAddUser(user.getUsername() + "(" + user.getUsername() + ")");
            ((SaveInfoInterface) model).setAddDttm(new Date());
            ((SaveInfoInterface) model).setLastUpdUserId(user.getUserid());
            ((SaveInfoInterface) model).setLastUpdUser(user.getUsername() + "(" + user.getUsername() + ")");
            ((SaveInfoInterface) model).setLastUpdDttm(new Date());
        }
        return model;
    }

    private <M> M formatUpdModel(M model) {
        SysUser user = SessionUtils.getSysUser();
        if (user != null) {
            ((UpdateInfoInterface) model).setLastUpdUserId(user.getUserid());
            ((UpdateInfoInterface) model).setLastUpdUser(user.getUsername() + "(" + user.getUsername() + ")");
            ((UpdateInfoInterface) model).setLastUpdDttm(new Date());
        }
        return model;
    }
}
