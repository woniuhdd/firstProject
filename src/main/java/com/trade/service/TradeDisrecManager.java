package com.trade.service;


import com.common.service.GenericManager;
import com.common.utils.Pagination;
import com.trade.model.TradeDisrec;

import java.util.List;
import java.util.Map;

public interface TradeDisrecManager extends GenericManager<TradeDisrec, String> {

    int addDistributeDataByDistributeProvince(Map<String, Object> map);

    Pagination getVerificationInvoiceResultList(Pagination page);

    List<Map<String,Object>> checkDistributeInvoiceDataByInterface(Map<String, Object> map);

    int addInvoiceDistributeData(Map<String, Object> map);
}