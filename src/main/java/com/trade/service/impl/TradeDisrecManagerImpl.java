package com.trade.service.impl;

import com.common.service.impl.GenericManagerImpl;
import com.common.utils.Pagination;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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

    @Override
    public Pagination getVerificationInvoiceResultList(Pagination page) {
        PageHelper pageHelper = new PageHelper();
        pageHelper.startPage(page.getPage(), page.getCount(), page.getOrderby());
        Page<Map<String,Object>> models = (Page<Map<String,Object>>) tradeDisrecDao.getInvoiceCheckInfo(page.getConditions());
        page.setRows(models);
        page.setRecords(models.getTotal());
        return page;
    }

    @Override
    public List<Map<String, Object>> checkDistributeInvoiceDataByInterface(Map<String, Object> map) {
        return tradeDisrecDao.checkDistributeInvoiceDataByInterface(map);
    }

    @Override
    public int addInvoiceDistributeData(Map<String, Object> map) {

        int count = tradeDisrecDao.deleteOldDistributeInvoice(map);
        if (count >= 0) {
            count=tradeDisrecDao.updateDistributeInvoiceFlag(map);
            tradeDisrecDao.insertDistributeInvoice(map);
        }
        return count;
    }
}