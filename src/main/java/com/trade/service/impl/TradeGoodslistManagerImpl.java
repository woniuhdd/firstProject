package com.trade.service.impl;

import com.common.service.impl.GenericManagerImpl;
import com.common.utils.ToolUtils;
import com.model.ComInterfaceSaleList;
import com.trade.dao.TradeDruginfoDao;
import com.trade.dao.TradeGoodslistDao;
import com.trade.dao.TradeInvoicenewDao;
import com.trade.model.TradeGoodslist;
import com.trade.service.TradeGoodslistManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TradeGoodslistManagerImpl extends GenericManagerImpl<TradeGoodslist, String> implements TradeGoodslistManager {
	// 扩展接口实现

    @Autowired
    private TradeGoodslistDao tradeGoodslistDao;
    @Autowired
    private TradeDruginfoDao tradeDruginfoDao;
    @Autowired
    private TradeInvoicenewDao tradeInvoicenewDao;

    @Override
    public List<Map<String, Object>> checkSaleIsExistsByInterface(List<ComInterfaceSaleList> saleList) {
        return tradeGoodslistDao.checkSaleIsExistsByInterface(saleList);
    }

    @Override
    public List<Map<String, Object>> checkSaleDataByInterface(List<ComInterfaceSaleList> saleList) {
        List<Map<String, Object>> results=new ArrayList<>();
        for (ComInterfaceSaleList sale:saleList) {
            Map<String, Object> result=new HashMap<>();
            if(tradeDruginfoDao.getById(sale.getGoodsID())==null){
                result.put("GOODSISEXISTS","0");
            }else{
                result.put("GOODSISEXISTS","1");
            }
            if(tradeInvoicenewDao.getById(sale.getInvoicePrimaryID())==null){
                result.put("INVOICEISEXISTS","0");
            }else{
                result.put("INVOICEISEXISTS","1");
            }
            result.put("COMPANYPRIMARYKEY",sale.getCompanyPrimaryKey());
            result.put("sale",sale);
            results.add(result);
        }
        return results;
    }

    @Override
    public int addSaleInfo(Map<String, Object> params) {
        List<Map<String, Object>> list = (List<Map<String, Object>>) params.get("list");
        String orgId = params.get("orgId").toString();
        List<TradeGoodslist> tradeGoodslists=new ArrayList<>();
        ComInterfaceSaleList sale;
        TradeGoodslist goodslist;
        for (Map<String, Object> map:list) {
            goodslist=new TradeGoodslist();
            sale=(ComInterfaceSaleList)map.get("sale");
            goodslist.setAddtime(new Date());
            goodslist.setAdduserid(orgId);
            goodslist.setBatchid(sale.getBatchCode());
            goodslist.setId(map.get("ID").toString());
            goodslist.setDrugid(sale.getGoodsID());
            goodslist.setInvoiceid(sale.getInvoicePrimaryID());
            goodslist.setCount(sale.getSaleNumber());
            goodslist.setDrugvaliditydate1(ToolUtils.fromShortFormat(sale.getPeriodDate()));
            tradeGoodslists.add(goodslist);
        }
        return tradeGoodslistDao.addSaleInfo(tradeGoodslists);
    }
}