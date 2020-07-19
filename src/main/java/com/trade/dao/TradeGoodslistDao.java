package com.trade.dao;

import com.common.dao.GenericDao;
import com.model.ComInterfaceSaleList;
import com.trade.model.TradeGoodslist;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TradeGoodslistDao extends GenericDao<TradeGoodslist, String> {
    List<Map<String, Object>> checkSaleIsExistsByInterface(List<ComInterfaceSaleList> map);

    int addSaleInfo(List<TradeGoodslist> list);
}