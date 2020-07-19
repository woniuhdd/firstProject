package com.trade.service.impl;

import com.common.service.impl.GenericManagerImpl;
import com.common.utils.ToolUtils;
import com.model.ComInterfaceInvoice;
import com.trade.dao.TradeInvoicenewDao;
import com.trade.model.TradeInvoicenew;
import com.trade.service.TradeInvoicenewManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TradeInvoicenewManagerImpl extends GenericManagerImpl<TradeInvoicenew, String> implements TradeInvoicenewManager {
	// 扩展接口实现

    @Autowired
    private TradeInvoicenewDao tradeInvoicenewDao;


    @Override
    public List<Map<String, Object>> checkInvoiceForInterFace(List<ComInterfaceInvoice> invoiceInfoList) {
        List<Map<String, Object>> results=new ArrayList<>();
        Map<String, Object> params;
        Map<String, Object> result;
        for (ComInterfaceInvoice invoice:invoiceInfoList) {
            params=new HashMap<>();
            result=new HashMap<>();
            params.put("invoiceid",invoice.getInvoiceCode());
            params.put("invoicecode",invoice.getInvoiceId());
            if(1==invoice.getInvoiceType()){
                params.put("adduserid",invoice.getOrgId());
            }
            if(tradeInvoicenewDao.countByParams(params)>0){
                result.put("ISEXISTS","1");
            }else{
                result.put("ISEXISTS","0");
            }
            result.put("COMPANYPRIMARYKEY",invoice.getCompanyPrimaryKey());
            result.put("invoice",invoice);
            results.add(result);
        }
        return results;
    }

    @Override
    public int addInvoiceInfo(Map<String, Object> params){
        List<Map<String, Object>> list = (List<Map<String, Object>>) params.get("list");
        // String orgId = params.get("orgId").toString();
        List<TradeInvoicenew> invoicenewList=new ArrayList<>();
        ComInterfaceInvoice invoice;
        TradeInvoicenew tradeInvoicenew;
        for (Map<String, Object> map:list) {
            tradeInvoicenew=new TradeInvoicenew();
            tradeInvoicenew.setId(map.get("invoicePrimaryId").toString());
            invoice=(ComInterfaceInvoice)map.get("invoice");
            tradeInvoicenew.setInvoiceid(invoice.getInvoiceCode());
            tradeInvoicenew.setInvoicecode(invoice.getInvoiceId());
            tradeInvoicenew.setAddtime(new Date());
            tradeInvoicenew.setAdduserid(invoice.getOrgId());
            tradeInvoicenew.setCompanyidBuy(invoice.getBuyId());
            tradeInvoicenew.setCompanyidSell(invoice.getSaleId());
            tradeInvoicenew.setCompanynameBuy(invoice.getBuyName());
            tradeInvoicenew.setCompanynameSell(invoice.getSaleName());
            tradeInvoicenew.setBuyremark(invoice.getBuyRemarks());
            tradeInvoicenew.setSellremark(invoice.getSaleRemarks());
            tradeInvoicenew.setBuytaxpayernumber(invoice.getBuyTaxPayerNumber());
            tradeInvoicenew.setSelltaxpayernumber(invoice.getSaleTaxPayerNumber());
            tradeInvoicenew.setInvoicedate(ToolUtils.fromShortFormat(invoice.getInvoiceDate()));
            tradeInvoicenew.setInvoiceimgid(map.get("invoiceImgId").toString());
            tradeInvoicenew.setInvoiceimgid2(map.get("saleImgId").toString());
            tradeInvoicenew.setInvoicetype(invoice.getInvoiceType().toString());
            tradeInvoicenew.setTotaltaxprice(invoice.getTotaltaxprice());
            tradeInvoicenew.setSubmitstatus("1");
            tradeInvoicenew.setSubmittime(new Date());
            invoicenewList.add(tradeInvoicenew);
        }
        return tradeInvoicenewDao.addInvoiceInfo(invoicenewList);
    }

}