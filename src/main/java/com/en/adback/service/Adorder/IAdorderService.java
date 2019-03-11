package com.en.adback.service.Adorder;

import com.en.adback.entity.Adorder.AdorderPolicy;
import com.en.adback.entity.Adorder.OrderBill;
import com.en.adback.entity.Adorder.OrderQueryList;
import com.en.adback.entity.Adorder.SubOrderBill;

import java.util.List;
import java.util.Map;

public interface IAdorderService {




    // 订单列表
    public List<OrderQueryList> mainOrderBillList(Map<String,Object> params);


    //订单子表查询
    public List<SubOrderBill> subOrderBillList(Map<String,Object> params);


     // 获取最大单号

    public String maxOrderId(Map<String,Object> params);


    // 插入新订单
    public boolean   insertOrder(OrderBill orderBill);

    // 修改订单
    public boolean   updateOrder(OrderBill orderBill);

    //修改订单
    boolean confirmOrder(OrderBill orderBill);

    //设置订单失效
    public boolean setOrderEffect(Map<String,Object> paras);

    //從數據庫讀取訂單
    public AdorderPolicy readAdorderPolicy(String adorderPolicyId);



    // 查询所有到当前为止要失效的订单(下单超7天未确认的，离开始播放还有5天的订单)
    public List<Map<String,Object>> willEffectOrder(Map<String,Object> paras);

    //获取订单主表总数
    public int mainOrderTotalCount(Map<String,Object> params);


    //获取订单主表测试
    public List<OrderBill> getOrder(Map<String, Object> params);

    List<OrderBill> getOrderList(Map<String, Object> map);

    int getOrderListTotal(Map<String, Object> map);
}
