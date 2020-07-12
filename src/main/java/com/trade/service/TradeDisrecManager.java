package com.trade.service;


import com.common.service.GenericManager;
import com.trade.model.TradeDisrec;

import java.util.Map;

public interface TradeDisrecManager extends GenericManager<TradeDisrec, String> {

    int addDistributeDataByDistributeProvince(Map<String, Object> map);
}