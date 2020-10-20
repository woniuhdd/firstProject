package com.trade.service;

import com.common.service.GenericManager;
import com.trade.model.BaseImgannex;

import java.util.Map;

public interface BaseImgannexManager extends GenericManager<BaseImgannex, String> {
	// 扩展接口

    int addInvoiceImageProvince(Map<String, Object> map);

    String getFolderId(Map<String, Object> folder);
}