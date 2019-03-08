package com.en.adback.controller.Adorder;


import com.alibaba.fastjson.JSON;
import com.en.adback.common.Common;
import com.en.adback.common.MessageModel;
import com.en.adback.controller.MakeExcelCtrl;
import com.en.adback.controller.sys.UserLogs;
import com.en.adback.entity.Adorder.*;
import com.en.adback.entity.advertmgr.AdvertStateHis;
import com.en.adback.redisrepo.entity.DeviceCutAdvert;
import com.en.adback.service.Adorder.IAdorderService;
import com.en.adback.service.advertmgr.IAdvertPolicyService;
import com.en.adback.service.advertmgr.IAdvertService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Api(value="广告订单",tags={"广告订单webapi接口"})
@RestController
@CrossOrigin
@RequestMapping(value = "/api/adorder", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json;charset=UTF-8")
public class AdorderCtrl {

//    用于修改已经确定的广告状态
    @Autowired
    private IAdvertService adsvr;

    @Autowired
    private IAdvertPolicyService advertPolicyService;
    @Autowired
    private IAdorderService adorderService;
    @Autowired
    private UserLogs userlogs;


    @Autowired
    private IAdvertPolicyService svr;


    @ApiOperation( value = "获取订单",notes = "获取订单api接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单编号", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "orderMemo", value = "订单备注", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "tradeId", value = "行业ID", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "adCorpName", value = "广告公司名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "blankId", value = "品牌id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "orderStateId", value = "订单状态", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "putinBeginTimeStart", value = "投放开始日期的起始日期", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "putinBeginTimeClose", value = "投放开始日期的起始日期", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "putinEndTimeStart", value = "投放结束日期的 开始日期", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "putinEndTimeClose", value = "投放结束日期的 结束日期", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "inputBeginTime", value = "录入开始时间 字符串", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "inputEndTime", value = "录入截止时间 字符串", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "invalidBeginTime", value = "失效结束时间  字符串", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "invalidEndTime", value = "失效结束时间  字符串", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "当前页", required = false, dataType = "int", paramType = "query"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value="/getAdorderPolicyList")
    public MessageModel getAdorderPolicyList(
            String orderId,  // 订单编号
                                             String orderMemo,  // 订单备注
                                             String tradeId,    // 行业id
                                             String orderCorpName,  // 广告公司名称
                                             String blankId,     // 品牌id
                                             String orderStateId, // 订单状态
                                             String putinBeginTimeStart,  // 投放开始日期的起始日期
                                             String putinBeginTimeClose,  // 投放开始日期的起始日期
                                             String putinEndTimeStart,    // 投放结束日期的 开始日期
                                             String putinEndTimeClose,    // 投放结束日期的 结束日期
                                             String inputBeginTime,  //录入开始时间 字符串
                                             String inputEndTime,   // 录入截止时间 字符串
                                             String invalidBeginTime,  // 失效结束时间  字符串
                                             String invalidEndTime,    //失效结束时间  字符串
                                             String pageSize,
                                             int pageIndex
    ) {
        MessageModel m= new MessageModel();

        Map<String,Object> params = new HashMap<String,Object>(){{
            put("orderId",orderId);
            put("orderMemo",orderMemo);
            put("orderCorpName",orderCorpName);
            put("blankId",blankId);
            put("tradeId",tradeId);
            put("orderStateId",orderStateId);
            put("putinBeginTimeStart",putinBeginTimeStart);
            put("putinBeginTimeClose",putinBeginTimeClose);
            put("putinEndTimeStart",putinEndTimeStart);
            put("putinEndTimeClose",putinEndTimeClose);
            put("inputBeginTime",inputBeginTime);
            put("inputEndTime",inputEndTime);
            put("invalidBeginTime",invalidBeginTime);
            put("invalidEndTime",invalidEndTime);
            put("pageBegin",(pageIndex-1)* Integer.parseInt(pageSize));
            put("pageSize",pageSize);
        }};
        List<OrderQueryList> adorderPolicy = adorderService.mainOrderBillList(params);

        m.setResultCode("1");
        m.setResultMsg("ok");
        m.setData(adorderPolicy);
        return m;
    }


    @ApiOperation( value = "获取订单总数",notes = "获取订单总数api接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单编号", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "orderMemo", value = "订单备注", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "tradeId", value = "行业ID", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "adCorpName", value = "广告公司名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "blankId", value = "品牌id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "orderStateId", value = "订单状态", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "putinBeginTimeStart", value = "投放开始日期的起始日期", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "putinBeginTimeClose", value = "投放开始日期的起始日期", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "putinEndTimeStart", value = "投放结束日期的 开始日期", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "putinEndTimeClose", value = "投放结束日期的 结束日期", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "inputBeginTime", value = "录入开始时间 字符串", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "inputEndTime", value = "录入截止时间 字符串", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "invalidBeginTime", value = "失效结束时间  字符串", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "invalidEndTime", value = "失效结束时间  字符串", required = false, dataType = "String", paramType = "query")
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @RequestMapping(value="/getAdorderTotalCount",method = {RequestMethod.GET,RequestMethod.POST})
    public MessageModel getAdorderTotalCount(
            String orderId,  // 订单编号
            String orderMemo,  // 订单备注
            String tradeId,    // 行业id
            String orderCorpName,  // 广告公司名称
            String blankId,     // 品牌id
            String orderStateId, // 订单状态
            String putinBeginTimeStart,  // 投放开始日期的起始日期
            String putinBeginTimeClose,  // 投放开始日期的起始日期
            String putinEndTimeStart,    // 投放结束日期的 开始日期
            String putinEndTimeClose,    // 投放结束日期的 结束日期
            String inputBeginTime,  //录入开始时间 字符串
            String inputEndTime,   // 录入截止时间 字符串
            String invalidBeginTime,  // 失效结束时间  字符串
            String invalidEndTime   //失效结束时间  字符串
    ) {
        MessageModel m= new MessageModel();

        Map<String,Object> params = new HashMap<String,Object>(){{
            put("orderId",orderId);
            put("orderMemo",orderMemo);
            put("orderCorpName",orderCorpName);
            put("blankId",blankId);
            put("tradeId",tradeId);
            put("orderStateId",orderStateId);
            put("putinBeginTimeStart",putinBeginTimeStart);
            put("putinBeginTimeClose",putinBeginTimeClose);
            put("putinEndTimeStart",putinEndTimeStart);
            put("putinEndTimeClose",putinEndTimeClose);
            put("inputBeginTime",inputBeginTime);
            put("inputEndTime",inputEndTime);
            put("invalidBeginTime",invalidBeginTime);
            put("invalidEndTime",invalidEndTime);
        }};
        int totalCount = adorderService.mainOrderTotalCount(params);

        m.setResultCode("1");
        m.setResultMsg("ok");
        m.setData(totalCount);

        return m;

    }


    @ApiOperation( value = "获取订单子表",notes = "获取订单api接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单编号", required = false, dataType = "String", paramType = "query")
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value="/subOrderBillList")
    public MessageModel  subOrderBillList(String orderId){
        MessageModel m= new MessageModel();
//        /*設置默認參數*/
        Map<String,Object> params = new HashMap<String,Object>(){{
            put("orderId",orderId);
        }};
        List<SubOrderBill> list = adorderService.subOrderBillList(params);
        m.setResultCode("1");
        m.setResultMsg("ok");
        m.setData(list);
        return m;
    }


    int i=1;
    @ApiOperation( value = "插入订单",notes = "插入订单api接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderbill", value = "订单类", required = false, dataType = "OrderBill", paramType = "body"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @PostMapping(value="/insertOrder")
    public MessageModel insertOrder(@RequestBody OrderBill orderbill ){
        MessageModel m= new MessageModel();

        //------------生成订单号及子表广告占位编号------------------//
        Date now=new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String baseDate=dateFormat.format(now);


        String newOrderId ="or" + adorderService.maxOrderId(new HashMap<String,Object>(){{
            put("baseDate",baseDate);
        }});
        orderbill.setOrderId(newOrderId);

        orderbill.getSubOrder().stream().forEach(sub ->{
            int index = orderbill.getSubOrder().indexOf(sub);
            sub.setOrderId(newOrderId);
            sub.setAdvertId(newOrderId +"|" + String.valueOf(index+1));

        });
        //  保存
        adorderService.insertOrder(orderbill);
        m.setResultCode("1");
        m.setResultMsg("ok");
        m.setData(newOrderId);
        return m;
    }


    //更新订单 (修改)
    @ApiOperation( value = "更新订单",notes = "更新订单api接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderbill", value = "订单类", required = false, dataType = "com.en.adback.entity.Adorder.OrderBill", paramType = "body"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @PostMapping(value="/updateOrder")
    public MessageModel updateOrder(@RequestBody OrderBill orderbill ){

        MessageModel m= new MessageModel();
        //  更新操作
        boolean result = adorderService.updateOrder(orderbill);

        m.setResultCode("1");
        m.setResultMsg("ok");
        m.setData(result);

        return m;
    }

    //更新订单
    @ApiOperation( value = "确认订单",notes = "确认订单api接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderbill", value = "订单类", required = false, dataType = "com.en.adback.entity.Adorder.OrderBill", paramType = "body"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @PostMapping(value="/confirmOrder")
    public MessageModel confirmOrder(@RequestBody OrderBill orderbill ){

        MessageModel m= new MessageModel();
        //  更新操作
        boolean result = adorderService.confirmOrder(orderbill);
        // 插入广告历史表
        for (int j = 0; j < orderbill.getSubOrder().size(); j++) {
            AdvertStateHis advertStateHis= new AdvertStateHis();
            advertStateHis.setAdvertId(orderbill.getSubOrder().get(j).getAdvertId());
            advertStateHis.setNowState(6);
            advertStateHis.setMaker(orderbill.getAffirmer());
            adsvr.insertAdvertStateHis(advertStateHis);
            //修改广告表状态
            Map<String,Object> pas = new HashMap<String,Object>();
            pas.put("advertId",orderbill.getSubOrder().get(j).getAdvertId());
            pas.put("nowState","6");
            adsvr.updateAdvertState(pas);
        }
        m.setResultCode("1");
        m.setResultMsg("ok");
        m.setData(result);

        return m;
    }

    //人工设置订单失效
    @ApiOperation( value = "订单失效",notes = "更新订单api接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单ID", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "effecter", value = "操作人", required = false, dataType = "string", paramType = "query"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @RequestMapping(value="/orderEffect",method = {RequestMethod.GET,RequestMethod.POST})
    public MessageModel humanOrderEffect(String orderId,String effecter){

        MessageModel m= new MessageModel();
        HashMap<String, Object> paramsMap = new HashMap<>();

        paramsMap.put("orderId",orderId);
        paramsMap.put("effecter",effecter);
        boolean result = adorderService.setOrderEffect(paramsMap);

        m.setResultCode("1");
        m.setResultMsg("ok");
        m.setData(result);

        return m;
    }

    // 从数据库读取策略
    @ApiOperation( value = "读取订单策略",notes = "读取订单策略api接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单ID", required = false, dataType = "string", paramType = "query"),
    })
    @GetMapping(value="/readAdorderPolicy")
    public MessageModel readAdorderPolicyByOrderId(String orderId) {
        MessageModel m= new MessageModel();
        AdorderPolicy adorderPolicy = adorderService.readAdorderPolicy(orderId);
        m.setData(adorderPolicy);
        m.setResultCode("1");
        m.setResultMsg("ok");
        return m;
    }


    @GetMapping(value = "/downloadExcel")
    public MessageModel downloadExcel(String orderId,  // 订单编号
                                      String orderMemo,  // 订单备注
                                      String tradeId,    // 行业id
                                      String orderCorpName,  // 广告公司名称
                                      String blankId,     // 品牌id
                                      String orderStateId, // 订单状态
                                      String putinBeginTimeStart,  // 投放开始日期的起始日期
                                      String putinBeginTimeClose,  // 投放开始日期的起始日期
                                      String putinEndTimeStart,    // 投放结束日期的 开始日期
                                      String putinEndTimeClose,    // 投放结束日期的 结束日期
                                      String inputBeginTime,  //录入开始时间 字符串
                                      String inputEndTime,   // 录入截止时间 字符串
                                      String invalidBeginTime,  // 失效结束时间  字符串
                                      String invalidEndTime,    //失效结束时间  字符串
                                      int pageSize,
                                      int pageIndex){
        MessageModel model=new MessageModel();

        //把录入日期加长一天
        if (inputEndTime !=null && !inputEndTime.equals("")) {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date d = new Date(f.parse(inputEndTime).getTime() + 24 * 3600 * 1000);
                inputEndTime = f.format(d);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        String finalInputEndTime = inputEndTime;
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("orderId",orderId);
        params.put("orderMemo",orderMemo);
        params.put("orderCorpName",orderCorpName);
        params.put("blankId",blankId);
        params.put("tradeId",tradeId);
        params.put("orderStateId",orderStateId);
        params.put("putinBeginTimeStart",putinBeginTimeStart);
        params.put("putinBeginTimeClose",putinBeginTimeClose);
        params.put("putinEndTimeStart",putinEndTimeStart);
        params.put("putinEndTimeClose",putinEndTimeClose);
        params.put("inputBeginTime",inputBeginTime);
        params.put("inputEndTime", finalInputEndTime);
        params.put("invalidBeginTime",invalidBeginTime);
        params.put("invalidEndTime",invalidEndTime);
        params.put("pageBegin",(pageIndex-1)*pageSize);
        params.put("pageSize",pageSize);
        List<OrderQueryList> list = adorderService.mainOrderBillList(params);

        MakeExcelCtrl mec=new MakeExcelCtrl();
        mec.writeOrderExcel(list);
        model.setResultCode("1");
        model.setData("excels/order.xls");
        return model;
    }


    @PostMapping(value = "/deviceDownloadExcel")
    public MessageModel deviceDownloadExcel(@RequestBody Map<String,Object> data){
        MessageModel model=new MessageModel();
        List<Map<String, Object>> list= JSON.parseObject(data.get("data").toString(),List.class);
        MakeExcelCtrl mec=new MakeExcelCtrl();
        mec.writeDeviceExcel(list);
        model.setResultCode("1");
        model.setResultMsg("success");

        return model;

    }


    /**
     *     微信公众号获取订单列表
     * @param stateId
     * @param userId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/getOrderList")
    public MessageModel getOrderList(int stateId, String userId,int pageIndex,int pageSize){
        MessageModel model=new MessageModel();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("stateId",stateId);
        map.put("userId",userId);
        map.put("pageSize",pageSize);
        map.put("pageBegin",(pageIndex-1)*pageSize);
        List<OrderBill> list=adorderService.getOrderList(map);
        map.clear();
        map.put("list",list);
        model.setData(map);
        model.setResultCode(list.size()>0?"1":"2");
        model.setResultMsg("success");
        return model;
    }


    /*订单表优化测试方法*/
    @GetMapping(value="/getAdorderTest")
    public ResponseEntity<List<OrderBill>> getAdorderTest(
            String orderId,  // 订单编号
            String orderMemo,  // 订单备注
            String tradeId,    // 行业id
            String orderCorpName,  // 广告公司名称
            String blankId,     // 品牌id
            String orderStateId, // 订单状态
            String putinBeginTimeStart,  // 投放开始日期的起始日期
            String putinBeginTimeClose,  // 投放开始日期的起始日期
            String putinEndTimeStart,    // 投放结束日期的 开始日期
            String putinEndTimeClose,    // 投放结束日期的 结束日期
            String inputBeginTime,  //录入开始时间 字符串
            String inputEndTime,   // 录入截止时间 字符串
            String invalidBeginTime,  // 失效结束时间  字符串
            String invalidEndTime,    //失效结束时间  字符串
            int pageSize,
            int pageIndex
    ) {
        MessageModel m= new MessageModel();

        Map<String,Object> params = new HashMap<String,Object>(){{
            put("orderId",orderId);
            put("orderMemo",orderMemo);
            put("orderCorpName",orderCorpName);
            put("blankId",blankId);
            put("tradeId",tradeId);
            put("orderStateId",orderStateId);
            put("putinBeginTimeStart",putinBeginTimeStart);
            put("putinBeginTimeClose",putinBeginTimeClose);
            put("putinEndTimeStart",putinEndTimeStart);
            put("putinEndTimeClose",putinEndTimeClose);
            put("inputBeginTime",inputBeginTime);
            put("inputEndTime",inputEndTime);
            put("invalidBeginTime",invalidBeginTime);
            put("invalidEndTime",invalidEndTime);
            put("pageBegin",(pageIndex-1)* pageSize);
            put("pageSize",pageSize);
        }};
        List<OrderBill> orderList = adorderService.getOrder(params);
        return ResponseEntity.ok(orderList);

    }

}
