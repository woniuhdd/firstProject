package com.trade.dao;

import com.common.dao.GenericDao;
import com.trade.model.TradeDisrec;

import java.util.Map;

public interface TradeDisrecDao extends GenericDao<TradeDisrec, String> {

    int addInsertDistributeByDistribute(Map<String, Object> map);

    int addInsertDistributeBatch(Map<String, Object> map);

    int addInsertDistributeInvoice(Map<String, Object> map);

    int updateOrderDetailByDistribute(Map<String, Object> map);
}