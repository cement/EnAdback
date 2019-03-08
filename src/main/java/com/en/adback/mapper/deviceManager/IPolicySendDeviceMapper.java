package com.en.adback.mapper.deviceManager;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface IPolicySendDeviceMapper {

    //查询已分发策略的设备
    @Select("select deviceId,count from ad.t_haveSendPolicy_device where count > 0")
    public List<String> selectHaveSendPolicyDeviceList();



    //插入已发策略表(订单) 自增++
    @Insert("<script>" +
            " upsert into ad.t_haveSendPolicy_device(deviceId, count,orderId) " +
            " values " +
            " (#{deviceId},1,#{orderId}) on duplicate key update count = count+1,orderId=#{orderId} " +
            "</script>")
    public  int upsertWithOrderIdIncr(@Param("deviceId") String deviceId,@Param("orderId")String orderId);
    //插入已发策略表(订单) 自减--
    @Insert("<script>" +
            " upsert into ad.t_haveSendPolicy_device(deviceId, count,orderId) " +
            " values " +
            " (#{deviceId},1,#{orderId}) on duplicate key update count = count-1,orderId=#{orderId} " +
            "</script>")
    public  int upsertWithOrderIdDecr(@Param("deviceId") String deviceId,@Param("orderId")String orderId);

    //插入已发策略表 (广告策略)
    @Insert("<script>" +
            " upsert into ad.t_haveSendPolicy_device(deviceId, count,advertPolicysId) " +
            " values " +
            " (#{deviceId},1,#{advertPolicysId}) on duplicate key update count = count+1,advertPolicysId=#{advertPolicysId} " +
            "</script>")
    public  int upsertWithAdvertPolicyId(@Param("deviceId") String deviceId,@Param("advertPolicysId") String advertPolicysId);

    //查询订单表 最新插入的数据(以orderId为比较条件)
    @Select(
            "<script>" +
            " select orderId as \"orderId\",devices as \"devices\" from ad.t_advert_order_policys as a " +
            "  where a.orderId > (select  case when  max(orderId) is null then '0' else max(orderId) end as maxOrderId  from ad.t_havesendpolicy_device)" +
            "  order by orderId asc  " +
            "</script>")
    public  List<Map<String,String>> selectNewerOrderPolicyDevices();

    //查询广告策略表 最新插入的数据(以advertPolicysId为比较条件)
    @Select(
            "<script>" +
            " select advertPolicysId as \"advertPolicysId\",devices as  \"devices\" from ad.t_advert_policys as a "+
            " where a.advertPolicysId  > (select  case when  max(advertPolicysId) is null then '0' else max(advertPolicysId) end as maxAdvertPolicysId  from ad.t_havesendpolicy_device)" +
            " order by advertPolicysId asc"+
            "</script>")
    public  List<Map<String,String>> selectNewerAdvertPolicyDevices();
}
