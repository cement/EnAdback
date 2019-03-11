package com.en.adback.mapper.Adorder;

import com.en.adback.entity.Adorder.*;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

// 广告策略
@Mapper
public interface AdorderPolicyMapper {


    // 读取订单单个广告策略主表
    @Select(
            " <script>" +
            "   select *  from ( " +
            "   select a.orderId as orderId,a.adCorpId as adCorpId,a.tradeId as tradeId,a.blankId as blankId,a.maker as maker,a.makeTime as makeTime,a.advertPolicysId as advertPolicysId,a.screenPolicyId as screenPolicyId,a.screenId as screenId, " +
            "   a.playDates as playDates,a.playTimeBegin as playTimeBegin,a.playTimeEnd as playTimeEnd,a.devices as devices,a.playAlone  as playAlone,a.putInKind as putInKind,a.systems as systems,a.effect as effect,a.effecter as effecter,a.effectTime  as effectTime, " +
            "   a.payMoney as payMoney,a.orderMemo as orderMemo,a.orderFirmTime as orderFirmTime,a.affirmer as affirmer, b.blankName as blankName, c.adcorpName as adcorpName, d.tradeName as tradeName , sub.adtotal advertCounts , '1' orderStateId " +
            "   from ad.T_ADVERT_ORDER_POLICYS as a inner join ad.T_BLANK as b on a.blankId = b.blankId inner join ad.T_ADVERTCORP as c on a.adcorpId=c.adcorpId inner join ad.T_TRADE as d on a.tradeId = d.tradeId " +
            "    left join " +
            "    ( " +
            "    select orderid, case when count(*) is null then 0 else count(*) end adtotal   from ad.T_ADVERT_sub_ORDER_POLICYS where advertid like 'or%' or  advertid like 'ad%'  group by orderid   " +
            "   )sub on a.orderid=sub.orderid " +

            " where a.effect=1 and a.orderid in (select orderid from ad.T_ADVERT_sub_ORDER_POLICYS  where  advertid like 'or%') " +

            "  union all " +
            "  select a.orderId as orderId,a.adCorpId as adCorpId,a.tradeId as tradeId,a.blankId as blankId,a.maker as maker,a.makeTime as makeTime,a.advertPolicysId as advertPolicysId,a.screenPolicyId as screenPolicyId,a.screenId as screenId, " +
            "  a.playDates as playDates,a.playTimeBegin as playTimeBegin,a.playTimeEnd as playTimeEnd,a.devices as devices,a.playAlone  as playAlone,a.putInKind as putInKind,a.systems as systems,a.effect as effect,a.effecter as effecter,a.effectTime  as effectTime, " +
            "  a.payMoney as payMoney,a.orderMemo as orderMemo,a.orderFirmTime as orderFirmTime, a.affirmer as affirmer, b.blankName as blankName, c.adcorpName as adcorpName, d.tradeName as tradeName , sub.adtotal advertCounts,'2' orderStateId " +
            "  from ad.T_ADVERT_ORDER_POLICYS as a inner join ad.T_BLANK as b on a.blankId = b.blankId inner join ad.T_ADVERTCORP as c on a.adcorpId=c.adcorpId inner join ad.T_TRADE as d on a.tradeId = d.tradeId " +
            " left join " +
            "  ( " +
            "          select orderid, case when count(*) is null then 0 else count(*) end adtotal   from ad.T_ADVERT_sub_ORDER_POLICYS where advertid like 'or%' or  advertid like 'ad%'  group by orderid   " +
            "  )sub on a.orderid=sub.orderid " +
            "  where a.effect=1 and a.orderid in (select orderid from ad.T_ADVERT_sub_ORDER_POLICYS  where  advertid like 'ad%') and  substr(a.playDates,0,10)  <![CDATA[ > ]]> to_char(CONVERT_TZ(CURRENT_DATE(), 'UTC', 'Asia/Shanghai'),'yyyy-MM-dd') " +

            "  union all " +
            "  select a.orderId as orderId,a.adCorpId as adCorpId,a.tradeId as tradeId,a.blankId as blankId,a.maker as maker,a.makeTime as makeTime,a.advertPolicysId as advertPolicysId,a.screenPolicyId as screenPolicyId,a.screenId as screenId, " +
            "  a.playDates as playDates,a.playTimeBegin as playTimeBegin,a.playTimeEnd as playTimeEnd,a.devices as devices,a.playAlone  as playAlone,a.putInKind as putInKind,a.systems as systems,a.effect as effect,a.effecter as effecter,a.effectTime  as effectTime, " +
            "  a.payMoney as payMoney,a.orderMemo as orderMemo,a.orderFirmTime as orderFirmTime,  a.affirmer as affirmer,b.blankName as blankName, c.adcorpName as adcorpName, d.tradeName as tradeName , sub.adtotal advertCounts,'3' orderStateId " +
            "  from ad.T_ADVERT_ORDER_POLICYS as a inner join ad.T_BLANK as b on a.blankId = b.blankId inner join ad.T_ADVERTCORP as c on a.adcorpId=c.adcorpId inner join ad.T_TRADE as d on a.tradeId = d.tradeId " +
            " left join " +
            "  ( " +
            "        select orderid, case when count(*) is null then 0 else count(*) end adtotal   from ad.T_ADVERT_sub_ORDER_POLICYS where advertid like 'or%' or  advertid like 'ad%'  group by orderid   " +
            "  )sub on a.orderid=sub.orderid " +
            " where a.effect=1 and a.orderid in (select orderid from ad.T_ADVERT_sub_ORDER_POLICYS  where  advertid like 'ad%') and  substr(a.playDates,0,10)  <![CDATA[ <= ]]> to_char(CONVERT_TZ(CURRENT_DATE(), 'UTC', 'Asia/Shanghai'),'yyyy-MM-dd') " +
            "  union all " +
            "  select a.orderId as orderId,a.adCorpId as adCorpId,a.tradeId as tradeId,a.blankId as blankId,a.maker as maker,a.makeTime as makeTime,a.advertPolicysId as advertPolicysId,a.screenPolicyId as screenPolicyId,a.screenId as screenId, " +
            "  a.playDates as playDates,a.playTimeBegin as playTimeBegin,a.playTimeEnd as playTimeEnd,a.devices as devices,a.playAlone  as playAlone,a.putInKind as putInKind,a.systems as systems,a.effect as effect,a.effecter as effecter,a.effectTime  as effectTime, " +
            "  a.payMoney as payMoney,a.orderMemo as orderMemo,a.orderFirmTime as orderFirmTime,   a.affirmer as affirmer,b.blankName as blankName, c.adcorpName as adcorpName, d.tradeName as tradeName , sub.adtotal advertCounts,'4' orderStateId " +
            "    from ad.T_ADVERT_ORDER_POLICYS as a inner join ad.T_BLANK as b on a.blankId = b.blankId inner join ad.T_ADVERTCORP as c on a.adcorpId=c.adcorpId inner join ad.T_TRADE as d on a.tradeId = d.tradeId " +
            "  left join " +
            " ( " +
            "        select orderid, case when count(*) is null then 0 else count(*) end adtotal   from ad.T_ADVERT_sub_ORDER_POLICYS where advertid like 'or%' or  advertid like 'ad%'  group by orderid   " +
            " )sub on a.orderid=sub.orderid " +
            " where a.effect=0 " +
            "  ) t  where 1=1 " +
            "  <if test='orderId!=\"\" and orderId!=null '> " +
            "    and t.orderId like '%${orderId}%' " +
            " </if> " +
            "  <if test='orderMemo!=\"\" and orderMemo!=null '> " +
            "    and t.orderMemo like '%${orderMemo}%' " +
            " </if> " +
           " <if test='tradeId!=\"\" and tradeId!=null and tradeId!=\"0\"'> " +
            "    and t.tradeId='${tradeId}'" +
            " </if> " +
            "  <if test='blankId!=\"\" and blankId!=null and blankId!=\"0\"'> " +
            "    and t.blankId='${blankId}'" +
            " </if> " +
            "  <if test='orderStateId!=\"\" and orderStateId!=null and orderStateId!=\"0\" '> " +
            "    and t.orderStateId='${orderStateId}'" +
            " </if> " +
            "  <if test='orderCorpName!=\"\" and orderCorpName!=null '> " +
            "    and t.adcorpName  like '%${orderCorpName}%' " +
            " </if> " +
            "  <if test='putinBeginTimeStart!=\"\" and putinBeginTimeStart!=null '> " +
            "     <![CDATA[ and substr(t.playDates,0,10) >='${putinBeginTimeStart}']]> " +
            " </if>" +
            "  <if test='putinBeginTimeClose!=\"\" and putinBeginTimeClose!=null '> " +
            "     <![CDATA[ and substr(t.playDates,0,10) <= '${putinBeginTimeClose} ']]>  " +
            " </if>" +
            "  <if test='putinEndTimeStart!=\"\" and putinEndTimeStart!=null '> " +
            "     <![CDATA[ and substr(t.playDates,-10) >='${putinEndTimeStart}']]> " +
            " </if> " +
            "  <if test='putinEndTimeClose!=\"\" and putinEndTimeClose!=null '> " +
            "     <![CDATA[ and  substr(t.playDates,-10) <='${putinEndTimeClose}']]>  " +
            " </if> " +
            "  <if test='inputBeginTime!=\"\" and inputBeginTime!=null '> " +
            "    <![CDATA[ and  t.makeTime >= to_date('${inputBeginTime} 08','yyyy-MM-dd hh') ]]>  " +
            " </if> " +
            "  <if test='inputEndTime!=\"\" and inputEndTime!=null '> " +
            "    <![CDATA[ and  t.makeTime <= to_date('${inputEndTime} 08','yyyy-MM-dd hh') ]]>   " +
            "  </if>" +
            "  <if test='invalidBeginTime!=\"\" and invalidBeginTime!=null '> " +
            "    <![CDATA[ and  t.effectTime >= to_date('${invalidBeginTime} 08','yyyy-MM-dd hh') ]]>   " +
            "  </if>" +
            "  <if test='invalidEndTime!=\"\" and invalidEndTime!=null '> " +
            "    <![CDATA[ and  t.effectTime <= to_date('${invalidEndTime} 08','yyyy-MM-dd hh') ]]>   " +
            "  </if>" +
            "  order by t.orderStateId asc,t.makeTime desc limit ${pageSize} offset ${pageBegin}" +
            " </script>")



    public List<OrderQueryList> mainOrderBillList(Map<String, Object> params);

// 读取订单总数
    @Select(
            " <script>" +
            "   select count(t.orderId)  from ( " +
            "   select a.orderId as orderId,a.adCorpId as adCorpId,a.tradeId as tradeId,a.blankId as blankId,a.maker as maker,a.makeTime as makeTime,a.advertPolicysId as advertPolicysId,a.screenPolicyId as screenPolicyId,a.screenId as screenId, " +
            "   a.playDates as playDates,a.playTimeBegin as playTimeBegin,a.playTimeEnd as playTimeEnd,a.devices as devices,a.playAlone  as playAlone,a.putInKind as putInKind,a.systems as systems,a.effect as effect,a.effecter as effecter,a.effectTime  as effectTime, " +
            "   a.payMoney as payMoney,a.orderMemo as orderMemo,a.orderFirmTime as orderFirmTime,   a.affirmer as affirmer,b.blankName as blankName, c.adcorpName as adcorpName, d.tradeName as tradeName , sub.adtotal advertCounts , '1' orderStateId " +
            "   from ad.T_ADVERT_ORDER_POLICYS as a inner join ad.T_BLANK as b on a.blankId = b.blankId inner join ad.T_ADVERTCORP as c on a.adcorpId=c.adcorpId inner join ad.T_TRADE as d on a.tradeId = d.tradeId " +
            "    left join " +
            "    ( " +
            "    select orderid, case when count(*) is null then 0 else count(*) end adtotal   from ad.T_ADVERT_sub_ORDER_POLICYS where advertid like 'or%' or  advertid like 'ad%'  group by orderid   " +
            "   )sub on a.orderid=sub.orderid " +

            " where a.effect=1 and a.orderid in (select orderid from ad.T_ADVERT_sub_ORDER_POLICYS  where  advertid like 'or%') " +

            "  union all " +
            "  select a.orderId as orderId,a.adCorpId as adCorpId,a.tradeId as tradeId,a.blankId as blankId,a.maker as maker,a.makeTime as makeTime,a.advertPolicysId as advertPolicysId,a.screenPolicyId as screenPolicyId,a.screenId as screenId, " +
            "  a.playDates as playDates,a.playTimeBegin as playTimeBegin,a.playTimeEnd as playTimeEnd,a.devices as devices,a.playAlone  as playAlone,a.putInKind as putInKind,a.systems as systems,a.effect as effect,a.effecter as effecter,a.effectTime  as effectTime, " +
            "  a.payMoney as payMoney,a.orderMemo as orderMemo,a.orderFirmTime as orderFirmTime,   a.affirmer as affirmer,b.blankName as blankName, c.adcorpName as adcorpName, d.tradeName as tradeName , sub.adtotal advertCounts,'2' orderStateId " +
            "  from ad.T_ADVERT_ORDER_POLICYS as a inner join ad.T_BLANK as b on a.blankId = b.blankId inner join ad.T_ADVERTCORP as c on a.adcorpId=c.adcorpId inner join ad.T_TRADE as d on a.tradeId = d.tradeId " +
            " left join " +
            "  ( " +
            "          select orderid, case when count(*) is null then 0 else count(*) end adtotal   from ad.T_ADVERT_sub_ORDER_POLICYS where advertid like 'or%' or  advertid like 'ad%'  group by orderid   " +
            "  )sub on a.orderid=sub.orderid " +
            "  where a.effect=1 and a.orderid in (select orderid from ad.T_ADVERT_sub_ORDER_POLICYS  where  advertid like 'ad%') and  substr(a.playDates,0,10)  <![CDATA[ > ]]> to_char(CONVERT_TZ(CURRENT_DATE(), 'UTC', 'Asia/Shanghai'),'yyyy-MM-dd') " +

            "  union all " +
            "  select a.orderId as orderId,a.adCorpId as adCorpId,a.tradeId as tradeId,a.blankId as blankId,a.maker as maker,a.makeTime as makeTime,a.advertPolicysId as advertPolicysId,a.screenPolicyId as screenPolicyId,a.screenId as screenId, " +
            "  a.playDates as playDates,a.playTimeBegin as playTimeBegin,a.playTimeEnd as playTimeEnd,a.devices as devices,a.playAlone  as playAlone,a.putInKind as putInKind,a.systems as systems,a.effect as effect,a.effecter as effecter,a.effectTime  as effectTime, " +
            "  a.payMoney as payMoney,a.orderMemo as orderMemo,a.orderFirmTime as orderFirmTime,   a.affirmer as affirmer,b.blankName as blankName, c.adcorpName as adcorpName, d.tradeName as tradeName , sub.adtotal advertCounts,'3' orderStateId " +
            "  from ad.T_ADVERT_ORDER_POLICYS as a inner join ad.T_BLANK as b on a.blankId = b.blankId inner join ad.T_ADVERTCORP as c on a.adcorpId=c.adcorpId inner join ad.T_TRADE as d on a.tradeId = d.tradeId " +
            " left join " +
            "  ( " +
            "        select orderid, case when count(*) is null then 0 else count(*) end adtotal   from ad.T_ADVERT_sub_ORDER_POLICYS where advertid like 'or%' or  advertid like 'ad%'  group by orderid   " +
            "  )sub on a.orderid=sub.orderid " +
            " where a.effect=1 and a.orderid in (select orderid from ad.T_ADVERT_sub_ORDER_POLICYS  where  advertid like 'ad%') and  substr(a.playDates,0,10)  <![CDATA[ <= ]]> to_char(CONVERT_TZ(CURRENT_DATE(), 'UTC', 'Asia/Shanghai'),'yyyy-MM-dd') " +
            "  union all " +
            "  select a.orderId as orderId,a.adCorpId as adCorpId,a.tradeId as tradeId,a.blankId as blankId,a.maker as maker,a.makeTime as makeTime,a.advertPolicysId as advertPolicysId,a.screenPolicyId as screenPolicyId,a.screenId as screenId, " +
            "  a.playDates as playDates,a.playTimeBegin as playTimeBegin,a.playTimeEnd as playTimeEnd,a.devices as devices,a.playAlone  as playAlone,a.putInKind as putInKind,a.systems as systems,a.effect as effect,a.effecter as effecter,a.effectTime  as effectTime, " +
            "  a.payMoney as payMoney,a.orderMemo as orderMemo,a.orderFirmTime as orderFirmTime,  a.affirmer as affirmer, b.blankName as blankName, c.adcorpName as adcorpName, d.tradeName as tradeName , sub.adtotal advertCounts,'4' orderStateId " +
            "    from ad.T_ADVERT_ORDER_POLICYS as a inner join ad.T_BLANK as b on a.blankId = b.blankId inner join ad.T_ADVERTCORP as c on a.adcorpId=c.adcorpId inner join ad.T_TRADE as d on a.tradeId = d.tradeId " +
            "  left join " +
            " ( " +
            "        select orderid, case when count(*) is null then 0 else count(*) end adtotal   from ad.T_ADVERT_sub_ORDER_POLICYS where advertid like 'or%' or  advertid like 'ad%'  group by orderid   " +
            " )sub on a.orderid=sub.orderid " +
            " where a.effect=0 " +
            "  ) t  where 1=1 " +
            "  <if test='orderId!=\"\" and orderId!=null '> " +
            "    and t.orderId like '%${orderId}%' " +
            " </if> " +
            "  <if test='orderMemo!=\"\" and orderMemo!=null '> " +
            "    and t.orderMemo like '%${orderMemo}%' " +
            " </if> " +
           " <if test='tradeId!=\"\" and tradeId!=null and tradeId!=\"0\"'> " +
            "    and t.tradeId='${tradeId}'" +
            " </if> " +
            "  <if test='blankId!=\"\" and blankId!=null and blankId!=\"0\"'> " +
            "    and t.blankId='${blankId}'" +
            " </if> " +
            "  <if test='orderStateId!=\"\" and orderStateId!=null and orderStateId!=\"0\" '> " +
            "    and t.orderStateId='${orderStateId}'" +
            " </if> " +
            "  <if test='orderCorpName!=\"\" and orderCorpName!=null '> " +
            "    and t.adcorpName  like '%${orderCorpName}%' " +
            " </if> " +
            "  <if test='putinBeginTimeStart!=\"\" and putinBeginTimeStart!=null '> " +
            "     <![CDATA[ and substr(t.playDates,0,10) >='${putinBeginTimeStart}']]> " +
            " </if>" +
            "  <if test='putinBeginTimeClose!=\"\" and putinBeginTimeClose!=null '> " +
            "     <![CDATA[ and substr(t.playDates,0,10) <= '${putinBeginTimeClose} ']]>  " +
            " </if>" +
            "  <if test='putinEndTimeStart!=\"\" and putinEndTimeStart!=null '> " +
            "     <![CDATA[ and substr(t.playDates,-10) >='${putinEndTimeStart}']]> " +
            " </if> " +
            "  <if test='putinEndTimeClose!=\"\" and putinEndTimeClose!=null '> " +
            "     <![CDATA[ and  substr(t.playDates,-10) <='${putinEndTimeClose}']]>  " +
            " </if> " +
            "  <if test='inputBeginTime!=\"\" and inputBeginTime!=null '> " +
            "    <![CDATA[ and  t.makeTime >= to_date('${inputBeginTime} 08','yyyy-MM-dd hh') ]]>  " +
            " </if> " +
            "  <if test='inputEndTime!=\"\" and inputEndTime!=null '> " +
            "    <![CDATA[ and  t.makeTime <= to_date('${inputEndTime} 08','yyyy-MM-dd hh') ]]>   " +
            "  </if>" +

            "  <if test='invalidBeginTime!=\"\" and invalidBeginTime!=null '> " +
            "    <![CDATA[ and  t.effectTime >= to_date('${invalidBeginTime} 08','yyyy-MM-dd hh') ]]>   " +
            "  </if>" +
            "  <if test='invalidEndTime!=\"\" and invalidEndTime!=null '> " +
            "    <![CDATA[ and  t.effectTime <= to_date('${invalidEndTime} 08','yyyy-MM-dd hh') ]]>   " +
            "  </if>" +
            " </script>")

    public int mainOrderTotalCount(Map<String, Object> params);


    /**
     * 查询待广告名称的订单子表
     *
     */
    @Select("<script>" +
            " select   id as id, sub.orderId as orderId," +
            "   sub.screenCutId as screenCutId," +
            "   sub.advertId as advertId," +
            "   a.advertName as advertName   from ad.T_ADVERT_sub_ORDER_POLICYS sub left outer join ad.t_advert a on sub.advertId=a.advertId " +
            "   where sub.orderId ='${orderId}'" +
            "</script>")
   public  List<SubOrderBill> subOrderBillList(Map<String,Object> params);


    /***
     * 获取最大单号
     */

    @Select("select max(orderId) as maxorderid from ad.T_ADVERT_ORDER_POLICYS where orderId like 'or${baseDate}-%'")
    public List<Map<String,Object>>  maxOrderId(Map<String,Object> params);







    /**
     * 订单主表保存(新增订单)
     * @param orderBill
     * @return
     */
    @Insert("<script>" +
            " upsert into ad.t_advert_order_policys" +
            " (orderId,adCorpId,tradeId,blankId,maker,makeTime,advertPolicysId,screenPolicyId,screenId,playDates,playTimeBegin,playTimeEnd,devices,playAlone,putInKind,systems,effect,effecter,effectTime,payMoney,orderMemo) " +
            "  values " +
            " ('${orderId}','${adCorpId}','${tradeId}','${blankId}','${maker}',CONVERT_TZ(CURRENT_DATE(), 'UTC', 'Asia/Shanghai'),'${advertPolicysId}','${screenPolicyId}','${screenId}'," +
            " '${playDates}','${playTimeBegin}','${playTimeEnd}','${devices}',${playAlone},${putInKind},'${systems}',1,'',null,${payMoney},'${orderMemo}')" +
            "</script>")
   public int saveMainOrderBill(OrderBill orderBill);

    /**
     * 订单主表保存(确认订单)
     * @param orderBill
     * @return
     */
    @Insert("<script>" +
            " upsert into ad.t_advert_order_policys" +
            " (orderId,adCorpId,tradeId,blankId,maker,makeTime,advertPolicysId,screenPolicyId,screenId,playDates,playTimeBegin,playTimeEnd,devices,playAlone,putInKind,systems,effect,effecter,effectTime,payMoney,orderMemo,orderFirmtime,affirmer) " +
            "  values " +
            " ('${orderId}' ,'${adCorpId}','${tradeId}','${blankId}','${maker}',CONVERT_TZ(CURRENT_DATE(), 'UTC', 'Asia/Shanghai'),'${advertPolicysId}','${screenPolicyId}','${screenId}'," +
            " '${playDates}','${playTimeBegin}','${playTimeEnd}','${devices}',${playAlone},${putInKind},'${systems}',1,'',null,${payMoney},'${orderMemo}',CONVERT_TZ(CURRENT_DATE(), 'UTC', 'Asia/Shanghai'),'${affirmer}')" +
            "</script>")
    public int confirmOrderBill(OrderBill orderBill);
    /**
     * 订单子表插入
     * @param subOrderBill
     * @return
     */

    @Insert("upsert into ad.t_advert_sub_order_policys(id, orderId ,screenCutId,advertId) " +
            "values(next value for ad.t_advert_sub_order_policys_seq, '${orderId}','${screenCutId}','${advertId}')")
    public int saveSubOrderBill(SubOrderBill subOrderBill);

    /**
     * 订单子表更新
     * @param subOrderBill
     * @return
     */

    @Insert("upsert into ad.t_advert_sub_order_policys(id, orderId ,screenCutId,advertId) " +
            "values(${id}, '${orderId}','${screenCutId}','${advertId}')")
    public int updateSubOrderBill(SubOrderBill subOrderBill);


    /**
     * 设置订单失效
     * @param paras
     * @return
     */
    @Update("upsert into ad.T_ADVERT_ORDER_POLICYS(orderId , effect,effecter,effecttime)\n" +
            "values('${orderId}',0,'${effecter}',CONVERT_TZ(CURRENT_DATE(), 'UTC', 'Asia/Shanghai'))")
    public int setOrderEffect(Map<String,Object> paras);



    /**
     * 查询所有到当前为止要失效的订单(下单超7天未确认的，离开始播放还有5天的订单)
     * @return
     */

    @Select("select orderid from ad.T_ADVERT_ORDER_POLICYS where( maketime <to_date('${sevenDayBefore}','yyyy-MM-dd')  or substr(playdates,0,10)<='${fiveDayBefore}') " +
            " and   orderid in (select orderId from  ad.t_advert_sub_order_policys where advertid like 'or%') and effect=1")
    public List<Map<String,Object>> willEffectOrder(Map<String,Object> paras);



    @Delete("delete from ad.T_ADVERT_SUB_ORDER_POLICYS where orderId = '${orderId}'")
    public int deleteSubOrder(@Param("orderId") String orderId);


    @Select(
            " <script>" +
                "select t.orderStateId as orderStateId,t.advertCounts as advertCounts,t.minAdvertId as minAdvertId,t.id as subId,t.screenCutId as screenCutId,t.orderId as orderId,t.adCorpId as adCorpId,t.tradeId as tradeId,t.blankId as blankId,t.maker as maker,t.makeTime as makeTime,t.advertPolicysId as advertPolicysId," +
                    " t.screenPolicyId as screenPolicyId,t.screenId as screenId, t.playDates as playDates,t.playTimeBegin as playTimeBegin,t.playTimeEnd as playTimeEnd,t.devices as devices,t.playAlone  as playAlone,t.putInKind as putInKind,t.effect as effect,t.effecter as effecter,t.effectTime  as effectTime, " +
                    " t.payMoney as payMoney,t.orderMemo as orderMemo,t.orderFirmTime as orderFirmTime,t.affirmer as affirmer,t.blankName as blankName, t.adcorpName as adcorpName, t.tradeName as tradeName  " +
                    "from ( "+
                   "  select sub.advertCounts as advertCounts,sub.minAdvertId as minAdvertId,sub.id as subId,sub.screenCutId as screenCutId," +
                    "  a.orderId as orderId,a.adCorpId as adCorpId,a.tradeId as tradeId,a.blankId as blankId,a.maker as maker,a.makeTime as makeTime,a.advertPolicysId as advertPolicysId,a.screenPolicyId as screenPolicyId,a.screenId as screenId, " +
                    "  a.playDates as playDates,a.playTimeBegin as playTimeBegin,a.playTimeEnd as playTimeEnd,a.devices as devices,a.playAlone  as playAlone,a.putInKind as putInKind,a.systems as systems,a.effect as effect,a.effecter as effecter,a.effectTime  as effectTime, " +
                    "  a.payMoney as payMoney,a.orderMemo as orderMemo,a.orderFirmTime as orderFirmTime,a.affirmer as affirmer," +
                    " b.blankName as blankName, c.adcorpName as adcorpName, d.tradeName as tradeName,sub.advertCounts as advertCounts, "+
                   "  (case when a.effect=1 and instr(sub.minAdvertId,'or') <![CDATA[ <> ]]> 0  then 1 "+
                   "     when a.effect=1 and instr(sub.minAdvertId,'ad') <![CDATA[ <> ]]>  0 and  substr(a.playDates,0,10)  <![CDATA[ > ]]>  to_char(CONVERT_TZ(CURRENT_DATE(), 'UTC', 'Asia/Shanghai'),'yyyy-MM-dd') then 2 "+
                   "     when a.effect=1 and instr(sub.minAdvertId,'ad') <![CDATA[ <> ]]> 0 and  substr(a.playDates,0,10)   <![CDATA[ <= ]]> to_char(CONVERT_TZ(CURRENT_DATE(), 'UTC', 'Asia/Shanghai'),'yyyy-MM-dd') then 3 "+
                   "     when a.effect=0  then 4 "+
                   "   end) as orderStateId "+
                   " from ad.t_advert_order_policys as a inner join ad.t_blank as b on a.blankId = b.blankId inner join ad.t_advertcorp as c on a.adcorpId=c.adcorpId inner join ad.t_trade as d on a.tradeId = d.tradeId "+
                   " left join  (  select id,orderid,screenCutId,  min(advertId) as minAdvertId,count(*)  as advertCounts   from ad.T_ADVERT_SUB_ORDER_POLICYS where advertid like 'or%' or  advertid like 'ad%'  group by id,orderid,screenCutId  ) as sub on a.orderid=sub.orderid " +
                    "  <where> " +
                    "  <if test='orderId!=\"\" and orderId!=null '> " +
                    "    and a.orderId like '%${orderId}%' " +
                    " </if> " +
                    "  <if test='orderMemo!=\"\" and orderMemo!=null '> " +
                    "    and a.orderMemo like '%${orderMemo}%' " +
                    " </if> " +
                    " <if test='tradeId!=\"\" and tradeId!=null and tradeId!=\"0\"'> " +
                    "    and a.tradeId='${tradeId}'" +
                    " </if> " +
                    "  <if test='blankId!=\"\" and blankId!=null and blankId!=\"0\"'> " +
                    "    and a.blankId='${blankId}'" +
                    " </if> " +
                    "  <if test='orderCorpName!=\"\" and orderCorpName!=null '> " +
                    "    and a.adcorpName  like '%${orderCorpName}%' " +
                    " </if> " +
                    "  <if test='putinBeginTimeStart!=\"\" and putinBeginTimeStart!=null '> " +
                    "     <![CDATA[ and substr(a.playDates,0,10) >='${putinBeginTimeStart}']]> " +
                    " </if>" +
                    "  <if test='putinBeginTimeClose!=\"\" and putinBeginTimeClose!=null '> " +
                    "     <![CDATA[ and substr(a.playDates,0,10) <= '${putinBeginTimeClose} ']]>  " +
                    " </if>" +
                    "  <if test='putinEndTimeStart!=\"\" and putinEndTimeStart!=null '> " +
                    "     <![CDATA[ and substr(a.playDates,-10) >='${putinEndTimeStart}']]> " +
                    " </if> " +
                    "  <if test='putinEndTimeClose!=\"\" and putinEndTimeClose!=null '> " +
                    "     <![CDATA[ and  substr(a.playDates,-10) <='${putinEndTimeClose}']]>  " +
                    " </if> " +
                    "  <if test='inputBeginTime!=\"\" and inputBeginTime!=null '> " +
                    "    <![CDATA[ and  a.makeTime >= to_date('${inputBeginTime} 08','yyyy-MM-dd hh') ]]>  " +
                    " </if> " +
                    "  <if test='inputEndTime!=\"\" and inputEndTime!=null '> " +
                    "    <![CDATA[ and  a.makeTime <= to_date('${inputEndTime} 08','yyyy-MM-dd hh') ]]>   " +
                    "  </if>" +
                    "  <if test='invalidBeginTime!=\"\" and invalidBeginTime!=null '> " +
                    "    <![CDATA[ and  a.effectTime >= to_date('${invalidBeginTime} 08','yyyy-MM-dd hh') ]]>   " +
                    "  </if>" +
                    "  <if test='invalidEndTime!=\"\" and invalidEndTime!=null '> " +
                    "    <![CDATA[ and  a.effectTime <= to_date('${invalidEndTime} 08','yyyy-MM-dd hh') ]]>   " +
                    "  </if>" +
                    "  </where> "+

                "  ) as t "+

                "  <where> " +
                    "  <if test='orderStateId!=\"\" and orderStateId!=null and orderStateId!=\"0\" '> " +
                    "    and t.orderStateId='${orderStateId}'" +
                    " </if> " +
                "  </where> "+
                "  limit ${pageSize} offset ${pageBegin}" +
            " </script>")
    @Results({
            @Result(property = "orderId",column = "orderId",id = true),
            @Result(property = "subOrder",javaType = List.class,column ="orderId",
             many = @Many(select = "com.en.adback.mapper.Adorder.AdorderPolicyMapper.getSubOrder"))})
    public  List<OrderBill> getOrder(Map<String,Object> params);


    @Select("select * from ad.t_advert_sub_order_policys where orderId = '${orderId}'")
    public List<SubOrderBill> getSubOrder(@Param("orderId") String orderId);


    @Select(
            " <script>" +
                    "   select *  from ( " +
                    "   select a.orderId as orderId,a.adCorpId as adCorpId,a.tradeId as tradeId,a.blankId as blankId,a.maker as maker,a.makeTime as makeTime,a.advertPolicysId as advertPolicysId,a.screenPolicyId as screenPolicyId,a.screenId as screenId, " +
                    "   a.playDates as playDates,a.playTimeBegin as playTimeBegin,a.playTimeEnd as playTimeEnd,a.devices as devices,a.playAlone  as playAlone,a.putInKind as putInKind,a.systems as systems,a.effect as effect,a.effecter as effecter,a.effectTime  as effectTime, " +
                    "   a.payMoney as payMoney,a.orderMemo as orderMemo,a.orderFirmTime as orderFirmTime,a.affirmer as affirmer, b.blankName as blankName, c.adcorpName as adcorpName, d.tradeName as tradeName , sub.adtotal advertCounts , '1' orderStateId " +
                    "   from ad.T_ADVERT_ORDER_POLICYS as a inner join ad.T_BLANK as b on a.blankId = b.blankId inner join ad.T_ADVERTCORP as c on a.adcorpId=c.adcorpId inner join ad.T_TRADE as d on a.tradeId = d.tradeId " +
                    "    left join " +
                    "    ( " +
                    "    select orderid, case when count(*) is null then 0 else count(*) end adtotal   from ad.T_ADVERT_sub_ORDER_POLICYS where advertid like 'or%' or  advertid like 'ad%'  group by orderid   " +
                    "   )sub on a.orderid=sub.orderid " +

                    " where a.effect=1 and a.orderid in (select orderid from ad.T_ADVERT_sub_ORDER_POLICYS  where  advertid like 'or%') " +

                    "  union all " +
                    "  select a.orderId as orderId,a.adCorpId as adCorpId,a.tradeId as tradeId,a.blankId as blankId,a.maker as maker,a.makeTime as makeTime,a.advertPolicysId as advertPolicysId,a.screenPolicyId as screenPolicyId,a.screenId as screenId, " +
                    "  a.playDates as playDates,a.playTimeBegin as playTimeBegin,a.playTimeEnd as playTimeEnd,a.devices as devices,a.playAlone  as playAlone,a.putInKind as putInKind,a.systems as systems,a.effect as effect,a.effecter as effecter,a.effectTime  as effectTime, " +
                    "  a.payMoney as payMoney,a.orderMemo as orderMemo,a.orderFirmTime as orderFirmTime, a.affirmer as affirmer, b.blankName as blankName, c.adcorpName as adcorpName, d.tradeName as tradeName , sub.adtotal advertCounts,'2' orderStateId " +
                    "  from ad.T_ADVERT_ORDER_POLICYS as a inner join ad.T_BLANK as b on a.blankId = b.blankId inner join ad.T_ADVERTCORP as c on a.adcorpId=c.adcorpId inner join ad.T_TRADE as d on a.tradeId = d.tradeId " +
                    " left join " +
                    "  ( " +
                    "          select orderid, case when count(*) is null then 0 else count(*) end adtotal   from ad.T_ADVERT_sub_ORDER_POLICYS where advertid like 'or%' or  advertid like 'ad%'  group by orderid   " +
                    "  )sub on a.orderid=sub.orderid " +
                    "  where a.effect=1 and a.orderid in (select orderid from ad.T_ADVERT_sub_ORDER_POLICYS  where  advertid like 'ad%') and  substr(a.playDates,0,10)  <![CDATA[ > ]]> to_char(CONVERT_TZ(CURRENT_DATE(), 'UTC', 'Asia/Shanghai'),'yyyy-MM-dd') " +

                    "  union all " +
                    "  select a.orderId as orderId,a.adCorpId as adCorpId,a.tradeId as tradeId,a.blankId as blankId,a.maker as maker,a.makeTime as makeTime,a.advertPolicysId as advertPolicysId,a.screenPolicyId as screenPolicyId,a.screenId as screenId, " +
                    "  a.playDates as playDates,a.playTimeBegin as playTimeBegin,a.playTimeEnd as playTimeEnd,a.devices as devices,a.playAlone  as playAlone,a.putInKind as putInKind,a.systems as systems,a.effect as effect,a.effecter as effecter,a.effectTime  as effectTime, " +
                    "  a.payMoney as payMoney,a.orderMemo as orderMemo,a.orderFirmTime as orderFirmTime,  a.affirmer as affirmer,b.blankName as blankName, c.adcorpName as adcorpName, d.tradeName as tradeName , sub.adtotal advertCounts,'3' orderStateId " +
                    "  from ad.T_ADVERT_ORDER_POLICYS as a inner join ad.T_BLANK as b on a.blankId = b.blankId inner join ad.T_ADVERTCORP as c on a.adcorpId=c.adcorpId inner join ad.T_TRADE as d on a.tradeId = d.tradeId " +
                    " left join " +
                    "  ( " +
                    "        select orderid, case when count(*) is null then 0 else count(*) end adtotal   from ad.T_ADVERT_sub_ORDER_POLICYS where advertid like 'or%' or  advertid like 'ad%'  group by orderid   " +
                    "  )sub on a.orderid=sub.orderid " +
                    " where a.effect=1 and a.orderid in (select orderid from ad.T_ADVERT_sub_ORDER_POLICYS  where  advertid like 'ad%') and  substr(a.playDates,0,10)  <![CDATA[ <= ]]> to_char(CONVERT_TZ(CURRENT_DATE(), 'UTC', 'Asia/Shanghai'),'yyyy-MM-dd') " +
                    "  union all " +
                    "  select a.orderId as orderId,a.adCorpId as adCorpId,a.tradeId as tradeId,a.blankId as blankId,a.maker as maker,a.makeTime as makeTime,a.advertPolicysId as advertPolicysId,a.screenPolicyId as screenPolicyId,a.screenId as screenId, " +
                    "  a.playDates as playDates,a.playTimeBegin as playTimeBegin,a.playTimeEnd as playTimeEnd,a.devices as devices,a.playAlone  as playAlone,a.putInKind as putInKind,a.systems as systems,a.effect as effect,a.effecter as effecter,a.effectTime  as effectTime, " +
                    "  a.payMoney as payMoney,a.orderMemo as orderMemo,a.orderFirmTime as orderFirmTime,   a.affirmer as affirmer,b.blankName as blankName, c.adcorpName as adcorpName, d.tradeName as tradeName , sub.adtotal advertCounts,'4' orderStateId " +
                    "    from ad.T_ADVERT_ORDER_POLICYS as a inner join ad.T_BLANK as b on a.blankId = b.blankId inner join ad.T_ADVERTCORP as c on a.adcorpId=c.adcorpId inner join ad.T_TRADE as d on a.tradeId = d.tradeId " +
                    "  left join " +
                    " ( " +
                    "        select orderid, case when count(*) is null then 0 else count(*) end adtotal   from ad.T_ADVERT_sub_ORDER_POLICYS where advertid like 'or%' or  advertid like 'ad%'  group by orderid   " +
                    " )sub on a.orderid=sub.orderid " +
                    " where a.effect=0 " +
                    "  ) t  where t.maker='${userId}' " +
                    "<if test='stateId!=null and stateId!=\"\" and stateId!=0 '>" +
                    " and t.orderStateId='${stateId}' " +
                    "</if>" +
                    " order by t.orderStateId asc, t.makeTime desc limit ${pageSize} offset ${pageBegin}" +
                    " </script>")
    public List<OrderBill> getOrderList(Map<String, Object> map);

    @Select(
            " <script>" +
                    "   select count(*) as total  from ( " +
                    "   select a.orderId as orderId,a.adCorpId as adCorpId,a.tradeId as tradeId,a.blankId as blankId,a.maker as maker,a.makeTime as makeTime,a.advertPolicysId as advertPolicysId,a.screenPolicyId as screenPolicyId,a.screenId as screenId, " +
                    "   a.playDates as playDates,a.playTimeBegin as playTimeBegin,a.playTimeEnd as playTimeEnd,a.devices as devices,a.playAlone  as playAlone,a.putInKind as putInKind,a.systems as systems,a.effect as effect,a.effecter as effecter,a.effectTime  as effectTime, " +
                    "   a.payMoney as payMoney,a.orderMemo as orderMemo,a.orderFirmTime as orderFirmTime,a.affirmer as affirmer, b.blankName as blankName, c.adcorpName as adcorpName, d.tradeName as tradeName , sub.adtotal advertCounts , '1' orderStateId " +
                    "   from ad.T_ADVERT_ORDER_POLICYS as a inner join ad.T_BLANK as b on a.blankId = b.blankId inner join ad.T_ADVERTCORP as c on a.adcorpId=c.adcorpId inner join ad.T_TRADE as d on a.tradeId = d.tradeId " +
                    "    left join " +
                    "    ( " +
                    "    select orderid, case when count(*) is null then 0 else count(*) end adtotal   from ad.T_ADVERT_sub_ORDER_POLICYS where advertid like 'or%' or  advertid like 'ad%'  group by orderid   " +
                    "   )sub on a.orderid=sub.orderid " +

                    " where a.effect=1 and a.orderid in (select orderid from ad.T_ADVERT_sub_ORDER_POLICYS  where  advertid like 'or%') " +

                    "  union all " +
                    "  select a.orderId as orderId,a.adCorpId as adCorpId,a.tradeId as tradeId,a.blankId as blankId,a.maker as maker,a.makeTime as makeTime,a.advertPolicysId as advertPolicysId,a.screenPolicyId as screenPolicyId,a.screenId as screenId, " +
                    "  a.playDates as playDates,a.playTimeBegin as playTimeBegin,a.playTimeEnd as playTimeEnd,a.devices as devices,a.playAlone  as playAlone,a.putInKind as putInKind,a.systems as systems,a.effect as effect,a.effecter as effecter,a.effectTime  as effectTime, " +
                    "  a.payMoney as payMoney,a.orderMemo as orderMemo,a.orderFirmTime as orderFirmTime, a.affirmer as affirmer, b.blankName as blankName, c.adcorpName as adcorpName, d.tradeName as tradeName , sub.adtotal advertCounts,'2' orderStateId " +
                    "  from ad.T_ADVERT_ORDER_POLICYS as a inner join ad.T_BLANK as b on a.blankId = b.blankId inner join ad.T_ADVERTCORP as c on a.adcorpId=c.adcorpId inner join ad.T_TRADE as d on a.tradeId = d.tradeId " +
                    " left join " +
                    "  ( " +
                    "          select orderid, case when count(*) is null then 0 else count(*) end adtotal   from ad.T_ADVERT_sub_ORDER_POLICYS where advertid like 'or%' or  advertid like 'ad%'  group by orderid   " +
                    "  )sub on a.orderid=sub.orderid " +
                    "  where a.effect=1 and a.orderid in (select orderid from ad.T_ADVERT_sub_ORDER_POLICYS  where  advertid like 'ad%') and  substr(a.playDates,0,10)  <![CDATA[ > ]]> to_char(CONVERT_TZ(CURRENT_DATE(), 'UTC', 'Asia/Shanghai'),'yyyy-MM-dd') " +

                    "  union all " +
                    "  select a.orderId as orderId,a.adCorpId as adCorpId,a.tradeId as tradeId,a.blankId as blankId,a.maker as maker,a.makeTime as makeTime,a.advertPolicysId as advertPolicysId,a.screenPolicyId as screenPolicyId,a.screenId as screenId, " +
                    "  a.playDates as playDates,a.playTimeBegin as playTimeBegin,a.playTimeEnd as playTimeEnd,a.devices as devices,a.playAlone  as playAlone,a.putInKind as putInKind,a.systems as systems,a.effect as effect,a.effecter as effecter,a.effectTime  as effectTime, " +
                    "  a.payMoney as payMoney,a.orderMemo as orderMemo,a.orderFirmTime as orderFirmTime,  a.affirmer as affirmer,b.blankName as blankName, c.adcorpName as adcorpName, d.tradeName as tradeName , sub.adtotal advertCounts,'3' orderStateId " +
                    "  from ad.T_ADVERT_ORDER_POLICYS as a inner join ad.T_BLANK as b on a.blankId = b.blankId inner join ad.T_ADVERTCORP as c on a.adcorpId=c.adcorpId inner join ad.T_TRADE as d on a.tradeId = d.tradeId " +
                    " left join " +
                    "  ( " +
                    "        select orderid, case when count(*) is null then 0 else count(*) end adtotal   from ad.T_ADVERT_sub_ORDER_POLICYS where advertid like 'or%' or  advertid like 'ad%'  group by orderid   " +
                    "  )sub on a.orderid=sub.orderid " +
                    " where a.effect=1 and a.orderid in (select orderid from ad.T_ADVERT_sub_ORDER_POLICYS  where  advertid like 'ad%') and  substr(a.playDates,0,10)  <![CDATA[ <= ]]> to_char(CONVERT_TZ(CURRENT_DATE(), 'UTC', 'Asia/Shanghai'),'yyyy-MM-dd') " +
                    "  union all " +
                    "  select a.orderId as orderId,a.adCorpId as adCorpId,a.tradeId as tradeId,a.blankId as blankId,a.maker as maker,a.makeTime as makeTime,a.advertPolicysId as advertPolicysId,a.screenPolicyId as screenPolicyId,a.screenId as screenId, " +
                    "  a.playDates as playDates,a.playTimeBegin as playTimeBegin,a.playTimeEnd as playTimeEnd,a.devices as devices,a.playAlone  as playAlone,a.putInKind as putInKind,a.systems as systems,a.effect as effect,a.effecter as effecter,a.effectTime  as effectTime, " +
                    "  a.payMoney as payMoney,a.orderMemo as orderMemo,a.orderFirmTime as orderFirmTime,   a.affirmer as affirmer,b.blankName as blankName, c.adcorpName as adcorpName, d.tradeName as tradeName , sub.adtotal advertCounts,'4' orderStateId " +
                    "    from ad.T_ADVERT_ORDER_POLICYS as a inner join ad.T_BLANK as b on a.blankId = b.blankId inner join ad.T_ADVERTCORP as c on a.adcorpId=c.adcorpId inner join ad.T_TRADE as d on a.tradeId = d.tradeId " +
                    "  left join " +
                    " ( " +
                    "        select orderid, case when count(*) is null then 0 else count(*) end adtotal   from ad.T_ADVERT_sub_ORDER_POLICYS where advertid like 'or%' or  advertid like 'ad%'  group by orderid   " +
                    " )sub on a.orderid=sub.orderid " +
                    " where a.effect=0 " +
                    "  ) t  where t.maker='${userId}' " +
                    "<if test='stateId!=null and stateId!=\"\" and stateId!=0 '>" +
                    " and t.orderStateId='${stateId}' " +
                    "</if>" +
                    " </script>")
    List<Map<String, Object>> getOrderListTotal(Map<String, Object> map);

}
