package com.en.adback.serviceimp.dic;

import com.en.adback.entity.dic.Trade;
import com.en.adback.mapper.dic.TradeMapper;
import com.en.adback.service.dic.ITradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/7.
 */
@Service
public class TradeServiceImp implements ITradeService{

    @Autowired private TradeMapper mapper;

    @Override
    public int insertTrade(Trade trade) {
        return mapper.insertTrade(trade);
    }

    @Override
    public String getTradeMaxTradeId() {
        return mapper.getTradeMaxTradeId();
    }

    @Override
    public List<Trade> getTradeList(Map<String, Object> re) {
        return mapper.getTradeList(re);
    }

    @Override
    public int getTradeListTotal(Map<String, Object> re) {
        return mapper.getTradeListTotal(re);
    }

    @Override
    public int upsertTrade(Trade trade) {
        return mapper.upsertTrade(trade);
    }

    @Override
    public int deteleTrade(Map<String,Object> re) {
        return mapper.deteleTrade(re);
    }
}
