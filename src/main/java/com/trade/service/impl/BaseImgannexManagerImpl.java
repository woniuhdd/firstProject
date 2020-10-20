package com.trade.service.impl;

import com.common.service.impl.GenericManagerImpl;
import com.trade.dao.BaseImgannexDao;
import com.trade.model.BaseImgannex;
import com.trade.service.BaseImgannexManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BaseImgannexManagerImpl extends GenericManagerImpl<BaseImgannex, String> implements BaseImgannexManager {
	// 扩展接口实现


    @Autowired
     private BaseImgannexDao imgannexDao;

    @Override
    public int addInvoiceImageProvince(Map<String, Object> map) {
        return imgannexDao.addInvoiceImageProvince(map);
    }

    @Override
    public String getFolderId(Map<String, Object> folder){
        return imgannexDao.getFolderId(folder);
    }
}