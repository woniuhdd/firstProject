package com.trade.service;

import com.common.service.GenericManager;
import com.model.ComInterfaceSaleList;
import com.trade.model.TradeGoodslist;

import java.util.List;
import java.util.Map;

public interface TradeGoodslistManager extends GenericManager<TradeGoodslist, String> {
	// 扩展接口

    List<Map<String, Object>> checkSaleIsExistsByInterface(List<ComInterfaceSaleList> saleList);

    List<Map<String, Object>> checkSaleDataByInterface(List<ComInterfaceSaleList> saleList);

    int addSaleInfo(Map<String, Object> map);
}