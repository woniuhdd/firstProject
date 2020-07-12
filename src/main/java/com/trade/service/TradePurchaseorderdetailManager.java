package com.trade.service;

import com.common.service.GenericManager;
import com.common.utils.Pagination;
import com.trade.model.TradePurchaseorderdetail;

import java.util.List;
import java.util.Map;

public interface TradePurchaseorderdetailManager extends GenericManager<TradePurchaseorderdetail, String> {

    Pagination queryAllOrderDetailRecentForInterface(Pagination page);

    Pagination  getCancelPurchaseOrderForInterface(Pagination page);

    List<Map<String, Object>> checkDistributeData(Map<String, Object> disDataList);
}