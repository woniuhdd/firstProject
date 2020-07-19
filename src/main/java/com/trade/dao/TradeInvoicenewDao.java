package com.trade.dao;

import com.common.dao.GenericDao;
import com.trade.model.TradeInvoicenew;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TradeInvoicenewDao extends GenericDao<TradeInvoicenew, String> {

    int addInvoiceInfo(List<TradeInvoicenew> list);

    int getNameCount(Map<String, Object> params);
}