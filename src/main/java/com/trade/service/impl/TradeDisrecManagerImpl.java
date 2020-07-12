package com.trade.service.impl;

import com.common.service.impl.GenericManagerImpl;
import com.trade.dao.TradeDisrecDao;
import com.trade.model.TradeDisrec;
import com.trade.model.TradeInvoiceDis;
import com.trade.service.TradeDisrecManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TradeDisrecManagerImpl extends GenericManagerImpl<TradeDisrec, String> implements TradeDisrecManager {

    @Autowired
    private TradeDisrecDao tradeDisrecDao;

    @Override
    public int addDistributeDataByDistributeProvince(Map<String, Object> map) {

        int count = tradeDisrecDao.addInsertDistributeByDistribute(map);
        if (count > 0) {
            //增加配送批次
            tradeDisrecDao.addInsertDistributeBatch(map);
            List<TradeInvoiceDis> invoiceList = (List<TradeInvoiceDis>) map.get("invoiceList");
            if (invoiceList.size() > 0) {
                tradeDisrecDao.addInsertDistributeInvoice(map);
            }
            tradeDisrecDao.updateOrderDetailByDistribute(map);
        }
        return count;
    }
}