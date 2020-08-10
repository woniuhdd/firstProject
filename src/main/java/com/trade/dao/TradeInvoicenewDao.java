package com.trade.dao;

import com.common.dao.GenericDao;
import com.trade.model.TradeInvoicenew;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TradeInvoicenewDao extends GenericDao<TradeInvoicenew, String> {

    int addInvoiceInfo(List<TradeInvoicenew> list);

    int getNameCount(Map<String, Object> params);
}