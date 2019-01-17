package com.en.adback.mapper.dic;

import com.en.adback.entity.dic.Trade;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/7.
 */
@Mapper
public interface TradeMapper {

    //插入行业信息
    @Insert("<script>" +
            " upsert into ad.t_trade(tradeId,tradeName,memo) values('${tradeId}','${tradeName}','${memo}') " +
            "</script>")
    int insertTrade(Trade trade);

    //查询行业表的tradeId最大值
    @Select("<script>" +
            " select tradeId from ad.t_trade order by tradeId desc limit 1 " +
            "</script>")
    String getTradeMaxTradeId();

    //分页查询行业信息
    @Select("<script>" +
            " select tradeId,tradeName,memo from ad.t_trade " +
            " where 1=1 " +
            "<if test='tradeId!=\"\" and tradeId!= null '>" +
            " and tradeId = '${tradeId}'" +
            "</if>" +
            "<if test='tradeName!=\"\" and tradeName!=null'>" +
            " and tradeName like '%${tradeName}%'" +
            "</if>" +
            " limit ${pageSize} offset ${pageBegin} " +
            "</script>")
    List<Trade> getTradeList(Map<String,Object> re);

    @Select("<script>" +
            " select count(*) from ad.t_trade " +
            " where 1=1 " +
            "<if test='tradeId!=\"\" and tradeId!= null '>" +
            " and tradeId = '${tradeId}'" +
            "</if>" +
            "<if test='tradeName!=\"\" and tradeName!=null'>" +
            " and tradeName like '%${tradeName}%'" +
            "</if>" +
            "</script>")
    int getTradeListTotal(Map<String,Object> re);

    //更新行业信息
    @Update("<script>" +
            " upsert into ad.t_trade(tradeId,tradeName,memo) " +
            " values('${tradeId}','${tradeName}','${memo}')" +
            "</script>")
    int upsertTrade(Trade trade);

    //根据tradeId删除行业信息
    @Delete("<script>" +
            " delete from ad.t_trade where tradeId = '${tradeId}'" +
            "</script>")
    int deteleTrade(Map<String,Object> re);
}
