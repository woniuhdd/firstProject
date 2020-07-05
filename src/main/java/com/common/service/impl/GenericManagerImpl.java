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

/**
 * 内容摘要：公共基础Service实现类
 *
 * @author 王幸蔚
 * @date 2017-07-18
 */
public class GenericManagerImpl<T, PK extends Serializable> implements GenericManager<T, PK> {
    @Autowired
    private GenericDao<T, PK> dao;

    public <T> T getDao() {
        return (T) dao;
    }

    /**
     * 内容摘要：通过主键id获取数据
     *
     * @param id 主键id
     * @return 泛型T对象
     * @author 王幸蔚
     * @date 2017-07-18
     */
    @Override
    public T getById(PK id) {
        return dao.getById(id);
    }

    /**
     * 内容摘要：根据参数获取数量
     *
     * @param params Map封装的查询参数
     * @return 查询行数
     * @author 王幸蔚
     * @date 2017-07-18
     */
    @Override
    public int countByParams(Map<String, Object> params) {
        return dao.countByParams(params);
    }

    /**
     * 内容摘要：根据参数条件获取数据
     *
     * @param params Map封装的查询参数
     * @return List封装的对象T集合
     * @author 王幸蔚
     * @date 2017-07-18
     */
    @Override
    public List<T> getListByParams(Map<String, Object> params) {
        return dao.getListByParams(params);
    }

    /**
     * 内容摘要：获取分页数据
     *
     * @param page 分页对象封装的查询参数
     * @return Page 分页对象
     * @author 王幸蔚
     * @date 2017-07-18
     */
    @Override
    public Pagination getList(Pagination page) {
        PageHelper.startPage(page.getPage(), page.getCount(), page.getOrderby());
        Page<T> models = (Page<T>) dao.getListByParams(page.getConditions());
        page.setRows(models);
        page.setRecords(models.getTotal());
        return page;
    }

    /**
     * 内容摘要：获取导入数据
     *
     * @param params Map封装的查询参数
     * @return List封装的对象T集合
     * @author 王幸蔚
     * @date 2017-07-18
     */
    @Override
    public List<T> getListWithImport(Map<String, Object> params) {
        return dao.getListWithImport(params);
    }

    /**
     * 内容摘要：获取导入数据
     *
     * @param page 分页对象封装的查询参数
     * @return 分页对象集合
     * @author 王幸蔚
     * @date 2017-07-18
     */
    @Override
    public Pagination getListWithImport(Pagination page) {
        PageHelper.startPage(page.getPage(), page.getCount(), page.getOrderby());
        Page<T> models = (Page<T>) dao.getListWithImport(page.getConditions());
        page.setRows(models);
        page.setRecords(models.getTotal());
        return page;
    }

    /**
     * 内容摘要：插入数据
     *
     * @param model 实体对象
     * @return boolean值
     * @author 王幸蔚
     * @date 2017-07-18
     */
    @Override
    public boolean save(T model) {
        if (model instanceof SaveInfoInterface) {
            model = this.formatSaveModel(model);
        }
        return dao.save(model) > 0;
    }

    /**
     * 内容摘要：批量插入数据
     *
     * @param models 实体对象集合
     * @return boolean值
     * @author 王幸蔚
     * @date 2017-07-18
     */
    @Override
    public boolean saveBatch(List<T> models) {
        for (T model : models) {
            model = this.formatSaveModel(model);
        }
        return dao.saveBatch(models) > 0;
    }

    /**
     * 内容摘要：根据id修改数据
     *
     * @param model 实体对象
     * @return boolean值
     * @author 王幸蔚
     * @date 2017-07-18
     */
    @Override
    public boolean updateById(T model) {
        if (model instanceof UpdateInfoInterface) {
            model = this.formatUpdModel(model);
        }
        return dao.updateById(model) > 0;
    }

    /**
     * 内容摘要：根据id修改某些条件字段
     *
     * @param model 实体对象
     * @return boolean值
     * @author 王幸蔚
     * @date 2017-07-18
     */
    @Override
    public boolean updateBySelective(T model) {
        if (model instanceof UpdateInfoInterface) {
            model = this.formatUpdModel(model);
        }
        return dao.updateBySelective(model) > 0;
    }

    /**
     * 内容摘要：根据id删除数据
     *
     * @param id 主键id
     * @return boolean值
     * @author 王幸蔚
     * @date 2017-07-18
     */
    @Override
    public boolean deleteById(PK id) {
        return dao.deleteById(id) > 0;
    }

    /**
     * 内容摘要：批量插入指定表
     *
     * @param datas     List封装的对象集合
     * @param tableName 表名
     * @return boolean值
     * @author 王幸蔚
     * @date 2017-07-18
     */
    @Override
    public boolean saveBatchTable(List<T> datas, String tableName) {
        SysUser user;
        for (T model : datas) {
            if (model instanceof SaveInfoInterface) {
                model = this.formatUpdModel(model);
            }
        }
        return dao.saveBatchTable(datas, tableName) > 0;
    }

    /**
     * 内容摘要：格式化默认插入对象
     *
     * @param model model对象
     * @return model对象
     * @author 王幸蔚
     * @date 2017-07-26
     */
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

    /**
     * 内容摘要：格式化默认修改对象
     *
     * @param model model对象
     * @return model对象
     * @author 王幸蔚
     * @date 2017-07-26
     */
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
