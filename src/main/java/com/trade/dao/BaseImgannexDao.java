package com.trade.dao;

import com.common.dao.GenericDao;
import com.trade.model.BaseImgannex;

import java.util.Map;

public interface BaseImgannexDao extends GenericDao<BaseImgannex, String> {

    /**
     * 添加发票图片数据（省平台）
     *
     * @param map
     * @return
     */
    int addInvoiceImageProvince(Map<String, Object> map);
}