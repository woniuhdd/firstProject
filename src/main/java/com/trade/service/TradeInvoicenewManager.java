package com.trade.service;

import com.common.service.GenericManager;
import com.model.ComInterfaceInvoice;
import com.trade.model.TradeInvoicenew;

import java.util.List;
import java.util.Map;

public interface TradeInvoicenewManager extends GenericManager<TradeInvoicenew, String> {
	// 扩展接口

    List<Map<String, Object>> checkInvoiceForInterFace(List<ComInterfaceInvoice> invoiceInfoList);

    int addInvoiceInfo(Map<String, Object> params);
}