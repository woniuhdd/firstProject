package com.trade.dao;

import com.common.dao.GenericDao;
import com.trade.model.TradeDisrec;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TradeDisrecDao extends GenericDao<TradeDisrec, String> {

    int addInsertDistributeByDistribute(Map<String, Object> map);

    int addInsertDistributeBatch(Map<String, Object> map);

    int addInsertDistributeInvoice(Map<String, Object> map);

    int updateOrderDetailByDistribute(Map<String, Object> map);

    List<Map<String,Object>> getInvoiceCheckInfo(Map<String, Object> map);

    List<Map<String,Object>>  checkDistributeInvoiceDataByInterface(Map<String, Object> map);

    int deleteOldDistributeInvoice(Map<String, Object> map);

    int updateDistributeInvoiceFlag(Map<String, Object> map);

    int  insertDistributeInvoice(Map<String, Object> map);
}