package com.en.adback.serviceimp.Adorder;

import com.en.adback.entity.Adorder.AdorderPolicy;
import com.en.adback.entity.Adorder.OrderBill;
import com.en.adback.entity.Adorder.OrderQueryList;
import com.en.adback.entity.Adorder.SubOrderBill;
import com.en.adback.entity.advertmgr.PlayPolicyScreen;
import com.en.adback.entity.advertmgr.TableAdvertPolicys;
import com.en.adback.entity.advertmgr.TableSubAdvertPolicys;
import com.en.adback.mapper.Adorder.AdorderPolicyMapper;
import com.en.adback.mapper.advertmgr.AdvertPolicyMapper;
import com.en.adback.redisrepo.DeviceRedis;
import com.en.adback.redisrepo.entity.DeviceCutAdvert;
import com.en.adback.service.Adorder.IAdorderService;
import com.en.adback.service.advertmgr.IAdvertPolicyService;
import com.en.adback.service.deviceManager.IPolicySendDeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;

@Slf4j
@Service
public class AdorderServiceImpl implements IAdorderService {

    @Autowired
    private IAdvertPolicyService advertPolicyService;

    @Autowired
    private AdorderPolicyMapper adorderMapper;

    @Autowired
    private DeviceRedis deviceRedis;

    @Autowired
    private AdvertPolicyMapper advertMapper;

    @Autowired
    private IPolicySendDeviceService deviceService;

    @Autowired
    @Qualifier("ThreadExecutor")
    private ExecutorService threadExecutor;

    // 赋值选中的屏幕策略到策略集合(从数据库读取)
    @Override
    public AdorderPolicy readAdorderPolicy(String orderId) {
        AdorderPolicy adorderPolicy= new AdorderPolicy();
        List<PlayPolicyScreen> playPolicyScreenList = advertPolicyService.allPlayPolicyScreen();
        List<OrderQueryList> orderList = adorderMapper.mainOrderBillList(new HashMap<String, Object>() {{
            put("orderId", orderId);
            put("pageSize",10);
            put("pageBegin",0);
        }});

        OrderQueryList adorder=orderList.size()==0 ? null : orderList.get(0);

        List<SubOrderBill> orderSubList = adorderMapper.subOrderBillList(new HashMap<String, Object>() {{put("orderId", orderId);}});

        if (adorder != null){
            PlayPolicyScreen screenPolicy=  playPolicyScreenList.stream()
                    .filter(playPolicyScreen->playPolicyScreen.getScreenpolicyId().equals(adorder.getScreenPolicyId()))
                    .findFirst().get();
            screenPolicy.setChoosed(true);
            //  currentP.setScreenpolicyId(mainp.getScreenPolicyId());
            screenPolicy.getScreens().stream()
                    .filter(screen->screen.getScreenId().equals(adorder.getScreenId()))
                    .forEach(screen-> {
                        screen.getCutScreenForm().stream().forEach(screenCut ->{
                            for (SubOrderBill sub : orderSubList){
                                if (screenCut.getScreenCutId().equals(sub.getScreenCutId())){
                                    screenCut.setAdvertId(String.valueOf(sub.getAdvertId()));
                                    screen.setChoosed(true);
                                }
                            }
                        });
                    }
            );
        }
        adorderPolicy.setOrder(adorder);
        adorderPolicy.setSubOrder(orderSubList);
        adorderPolicy.setScreenpolicys(playPolicyScreenList);
        return adorderPolicy;
    }




    // 订单列表查询
    @Override
    public List<OrderQueryList> mainOrderBillList(Map<String, Object> params) {
        List<OrderQueryList> list= adorderMapper.mainOrderBillList(params);
        return list;
    }

    // 订单子表查询
    @Override
    public List<SubOrderBill> subOrderBillList(Map<String, Object> params) {
        List<SubOrderBill> list =adorderMapper.subOrderBillList(params);
        return list;
    }

    // String format = String.format("%04d", nos);
    // 获取最大单号
    @Override
    public String maxOrderId(Map<String, Object> params) {
        List<Map<String, Object>> list =adorderMapper.maxOrderId(params);
        if (list.size()==0 || Objects.isNull(list.get(0))){
            return params.get("baseDate") + "-0001";
        }else {
            String maxId= list.get(0).get("MAXORDERID").toString();

            int nos = Integer.parseInt(maxId.substring(maxId.indexOf("-")+1));
//            int nos = Integer.parseInt( maxId.replace("or","").replace("-","").replace(params.get("baseDate").toString(),""));
            if (nos+1>=1000){
               return   params.get("baseDate")+"-" +  String.valueOf(++nos);
            }else if (nos+ 1<1000 && nos+1>=100){
                return   params.get("baseDate")+"-0" +  String.valueOf(++nos);
            }else if (nos+ 1<100 && nos+1>=10){
                return   params.get("baseDate")+"-00" +  String.valueOf(++nos);
            }else {
                return   params.get("baseDate")+"-000" +  String.valueOf(++nos);
            }
        }
    }


    // 插入订单
    @Override
    public boolean insertOrder(OrderBill orderBill) {
        adorderMapper.saveMainOrderBill(orderBill);
        orderBill.getSubOrder().stream().forEach(sub ->{
            adorderMapper.saveSubOrderBill(sub);
        });
        // 写入redis
        orderBill.getSubOrder().stream().forEach(sub ->{
            Arrays.stream(orderBill.getDevices().split(",")).forEach(
                    device ->{
                        Arrays.stream(orderBill.getPlayDates().split(",")).forEach(
                            playdate ->{
                                DeviceCutAdvert dc = new DeviceCutAdvert(device, playdate, orderBill.getScreenId(), sub.getScreenCutId(), String.valueOf( orderBill.getPlayAlone()),
                                        orderBill.getPlayTimeBegin(), orderBill.getPlayTimeEnd(),sub.getAdvertId());
                                        deviceRedis.addDeviceState(dc);
                            }
                        );

                    }
            );
        });

        // TODO 插入已分配策略设备记录表(标识最大orderId)
        threadExecutor.execute(()->{
           deviceService.upsertWithOrderIdIncr(orderBill.getDevices(),orderBill.getOrderId());
        });
        return true;
    }

    //修改订单
    @Override
    public boolean updateOrder(OrderBill orderBill) {

        /*  TODO 更新 已分配策略设备记录表 (orderId) */
        //第一步  根据id查出原来的 devices
        //第二步  根据查询出的devicesId 更新记录表count-1
        //第三步  根据当前订单类,根据orderId 更新记录表 count+1
        String devideIds = deviceService.getDevidesByOrderId(orderBill.getOrderId());
        threadExecutor.execute(()->{
            deviceService.upsertWithOrderIdDecr(devideIds,orderBill.getOrderId());
            deviceService.upsertWithOrderIdIncr(orderBill.getDevices(),orderBill.getOrderId());
        });

        //根据id查询出原来的广告Id,用于删除Redis
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("orderId",orderBill.getOrderId());
        List<SubOrderBill> subOrderBills = adorderMapper.subOrderBillList(paramsMap);
        //删除redis中的记录
        subOrderBills.stream().forEach(sub->{
            deviceRedis.deleteMsgByAdvertId(sub.getAdvertId());
        });

        //删除订单子表
        int count = adorderMapper.deleteSubOrder(orderBill.getOrderId());


        //更新订单主表
        adorderMapper.saveMainOrderBill(orderBill);
        //更新订单子表
        List<SubOrderBill> subOrder = orderBill.getSubOrder();
        for (int i = 0; i < subOrder.size(); i++) {
             subOrder.get(i).setOrderId(orderBill.getOrderId());
             subOrder.get(i).setAdvertId(orderBill.getOrderId() +"|" + String.valueOf(i+1));
             adorderMapper.saveSubOrderBill(subOrder.get(i));
        }


        // 重新写入redis
        orderBill.getSubOrder().stream().forEach(sub ->{
            Arrays.stream(orderBill.getDevices().split(",")).forEach(
                    device ->{
                        Arrays.stream(orderBill.getPlayDates().split(",")).forEach(
                                playdate ->{
                                    DeviceCutAdvert dc = new DeviceCutAdvert(device, playdate, orderBill.getScreenId(), sub.getScreenCutId(), String.valueOf( orderBill.getPlayAlone()),
                                            orderBill.getPlayTimeBegin(), orderBill.getPlayTimeEnd(),sub.getAdvertId());
                                           deviceRedis.addDeviceState(dc);
                                }
                        );

                    }
            );
        });

        return true;
    }


    //确认订单
    @Override
    public boolean confirmOrder(OrderBill orderBill) {
        //根据orderid查询出原来的advertId,用于删除Redis
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("orderId",orderBill.getOrderId());
        List<SubOrderBill> subOrderBills = adorderMapper.subOrderBillList(paramsMap);
        //删除redis中的记录
        subOrderBills.stream().forEach(sub->{
            deviceRedis.deleteMsgByAdvertId(sub.getAdvertId());
        });
        //写入订单主表
        adorderMapper.confirmOrderBill(orderBill);


        //写入广告策略主表
        TableAdvertPolicys mainp= new TableAdvertPolicys();
        mainp.setAdvertPolicysId(orderBill.getAdvertPolicysId());
        mainp.setScreenPolicyId(orderBill.getScreenPolicyId());
        mainp.setScreenId(orderBill.getScreenId());
        mainp.setPlayDates(orderBill.getPlayDates());
        mainp.setPlayTimeBegin(orderBill.getPlayTimeBegin());
        mainp.setPlayTimeEnd(orderBill.getPlayTimeEnd());
        mainp.setDevices(orderBill.getDevices());
        mainp.setPlayAlone(orderBill.getPlayAlone());
        mainp.setPutInKind(orderBill.getPutInKind());

        advertMapper.insertAdvertPolicys(mainp);


        //写入订单子表 以及广告策略子表
        orderBill.getSubOrder().stream().forEach(sub ->{
            adorderMapper.updateSubOrderBill(sub);

            TableSubAdvertPolicys subp= new TableSubAdvertPolicys();
            subp.setAdvertPolicysId(orderBill.getAdvertPolicysId());
            subp.setScreenCutId(sub.getScreenCutId());
            subp.setAdvertId(sub.getAdvertId());
            advertMapper.insertSubAdvertPolicys(subp);
        });

        // 重新写入redis
        orderBill.getSubOrder().stream().forEach(sub ->{
            Arrays.stream(orderBill.getDevices().split(",")).forEach(
                    device ->{
                        Arrays.stream(orderBill.getPlayDates().split(",")).forEach(
                                playdate ->{
                                    DeviceCutAdvert dc = new DeviceCutAdvert(device, playdate, orderBill.getScreenId(), sub.getScreenCutId(), String.valueOf( orderBill.getPlayAlone()),
                                            orderBill.getPlayTimeBegin(), orderBill.getPlayTimeEnd(),sub.getAdvertId());
                                            deviceRedis.addDeviceState(dc);
                                }
                        );

                    }
            );
        });


        //TODO 插入已分配策略设备记录表(标识AdvertPolicysId count--)
        threadExecutor.execute(()->{
           deviceService.upsertWithAdvertPolicyIdIncr(orderBill.getDevices(),orderBill.getAdvertPolicysId());
        });
        return true;
    }



    // 设置订单失效
    @Override
    public boolean setOrderEffect(Map<String, Object> params) {
        //设置订单失效  effect->0
        adorderMapper.setOrderEffect(params);

        //根据orderid查询出原来的advertId,用于删除Redis
        List<SubOrderBill> subOrderBills = adorderMapper.subOrderBillList(params);
        //删除redis中的记录
        subOrderBills.stream().forEach(sub->{
            deviceRedis.deleteMsgByAdvertId(sub.getAdvertId());
        });


        /*TODO  更新 已分配策略设备记录表 (orderId) */
        //第一步  根据id查出原来的 devices
        //第二步  根据查询出的devicesId 更新记录表count-1

        threadExecutor.execute(()->{
           String devideIds = deviceService.getDevidesByOrderId(String.valueOf(params.get("orderId")));
           deviceService.upsertWithOrderIdDecr(devideIds,String.valueOf(params.get("orderId")));
        });

        return true;
    }


    // 查询所有到当前为止要失效的订单(下单超7天未确认的，离开始播放还有5天的订单)
    @Override
    public List<Map<String,Object>> willEffectOrder(Map<String,Object> paras) {
        List<Map<String,Object>> list = adorderMapper.willEffectOrder(paras);
        return list;
    }

    //获取订单主表总数
    public int mainOrderTotalCount(Map<String,Object> params){
        return  adorderMapper.mainOrderTotalCount(params);
    }

    @Override
    public List<OrderBill> getOrder(Map<String, Object> params) {
        return adorderMapper.getOrder(params);
    }

    //查询订单列表
    @Override
    public List<OrderBill> getOrderList(Map<String, Object> map) {
        return adorderMapper.getOrderList(map);
    }


}
