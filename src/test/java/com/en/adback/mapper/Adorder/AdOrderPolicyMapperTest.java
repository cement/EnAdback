package com.en.adback.mapper.Adorder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.en.adback.AdbackApplication;
import com.en.adback.entity.Adorder.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdbackApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class AdOrderPolicyMapperTest {

    @Autowired
    private AdorderPolicyMapper orderPolicyMapper;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void adOrderPolicysListTest(){
//        HashMap<String, Object> params = new HashMap<>();
//        params.put("orderId","123");
//        List<TableAdorderPolicy> adOrderAdvertPolicyList = orderPolicyMapper.selectAdorderPolicyList(params);
//        log.info("=== adOrderAdvertPolicyList ===：",adOrderAdvertPolicyList);


    }

    @Test
    public void getAdorderSubAdvertPolicyListTest(){
//        HashMap<String, String> params = new HashMap<>();
//        params.put("orderId","123");
//        List<TableAdorderSubPolicy> adorderSubAdvertPolicyList = orderPolicyMapper.selectAdorderSubAdvertPolicyList(params);
//        log.info("=== adorderSubAdvertPolicyList ===：",adorderSubAdvertPolicyList);
    }


    @Test
    public void  insertAdOrderPolicys(){
        TableAdorderPolicy adorderPolicy = new TableAdorderPolicy();
        adorderPolicy.setOrderId("0123456789");
//        int effectRowCount = orderPolicyMapper.insertAdOrderMainPolicy(adorderPolicy);
//        log.info("=== 测试 插入订单主表 返回影响行数：{}",effectRowCount);

    }


    @Test
    public void insertAdOrderSubAdvertPolicy(){

        TableAdorderSubPolicy adorderSubPolicy = new TableAdorderSubPolicy();

        adorderSubPolicy.setOrderId("111111111");
//        int effectRowCount = orderPolicyMapper.insertAdOrderSubPolicy(adorderSubPolicy);
//        log.info("=== 测试 插入订单子表 订单ID：{}，返回影响行数：{}",adorderSubPolicy.getOrderId(),effectRowCount);
    }

    @Test
    public void deleteAdorderSubPolicy() {

        HashMap<String, Object> params = new HashMap<String, Object>() ;
        params.put("orderId","123456");
//        int effectRowCount = orderPolicyMapper.deleteAdorderSubPolicyById(params);
//        log.info("=== 测试 删除订单子表 订单ID：{}，返回影响行数：{}",params.get("orderId"),effectRowCount);

    }

    @Test
    public void deleteAdorderMainPolicy() {

        HashMap<String, Object> params = new HashMap<String, Object>() ;
        params.put("orderId","123456789");
//        int effectRowCount = orderPolicyMapper.deleteAdorderMainPolicyById(params);
//        log.info("=== 测试 删除订单子表 订单ID：{},返回影响行数：{}",params.get("orderId"),effectRowCount);

    }

/*撤销订单 */
    @Test
    public void revokeAdorderPolicy(){
        HashMap paramsMap = new HashMap() {{
            put("orderId","111111111");
            put("effect",0);
            put("effecter","user001");
        }};

//        int effectRowCount = orderPolicyMapper.revokeAdorderPolicy(paramsMap);
//        log.info("=== 测试 撤销订单表 订单ID：{},返回影响行数：{}",paramsMap.get("orderId"),effectRowCount);
    }

    @Test
    public void saveSubOrderBill(){

        SubOrderBill subOrderBill = new SubOrderBill();
        subOrderBill.setId(44);
        subOrderBill.setScreenCutId("00");
        orderPolicyMapper.saveSubOrderBill(subOrderBill);

        log.info("=== 测试 更新订单子表 订单ID：{},返回影响行数：{}");
    }

    @Test
    public void getOrderTest1(){
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("pageSize",10);
        paramsMap.put("pageBegin",0);
        long start = System.currentTimeMillis();
        List<OrderBill> orderList = orderPolicyMapper.getOrder(paramsMap);
        long stop = System.currentTimeMillis();
        log.info("=== Test1 查询耗时:===  {}",stop-start);
        log.info("=== 测试 getOrderTest:,返回：{}", JSON.toJSONString(orderList, SerializerFeature.WriteMapNullValue,SerializerFeature.PrettyFormat));
    }
    @Test
    public void getOrderTest2(){
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("pageSize",10);
        paramsMap.put("pageBegin",0);
        long start = System.currentTimeMillis();
        List<OrderQueryList> orderList = orderPolicyMapper.mainOrderBillList(paramsMap);
        long stop = System.currentTimeMillis();
        log.info("=== Test2 查询耗时:===  {}",stop-start);
        log.info("=== 测试 getOrderTest:,返回：{}", JSON.toJSONString(orderList, SerializerFeature.WriteMapNullValue,SerializerFeature.PrettyFormat));
    }

    @Test
    public void  confirmOrderBill(){
        OrderBill orderBill = new OrderBill();
        orderBill.setOrderId("123456789");
        orderBill.setSystems("123456");
        int resultCount = orderPolicyMapper.confirmOrderBill(orderBill);
        log.info("=== 测试 confirmOrderBill:=== count： {}",resultCount);
    }

}