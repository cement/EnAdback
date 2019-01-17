package com.en.adback.service.dic;

import com.en.adback.entity.dic.Trade;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/7.
 */
public interface ITradeService {

    int insertTrade(Trade trade);

    String  getTradeMaxTradeId();

    List<Trade> getTradeList(Map<String,Object> re);

    int getTradeListTotal(Map<String,Object> re);

    int upsertTrade(Trade trade);

    int deteleTrade(Map<String,Object> re);
}
