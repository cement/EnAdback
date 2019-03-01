package com.en.adback.entity.Adorder;

import com.en.adback.entity.advertmgr.TableAdvertPolicys;
import lombok.Data;

import java.util.Date;

// 数据库表的广告策略类
@Data
public class TableAdorderPolicy extends TableAdvertPolicys {
    /*继承的属性以及说明*/
//   private String  advertPolicysId ; //策略id
//   private String  screenPolicyId;  // 屏幕策略id
//   private String  screenId;           //屏幕id
//   private String  playDates;         //播放日期
//   private String  playTimeBegin;  //开始播放时间(插播广告用)
//   private String  playTimeEnd;    //结束播放时间
//   private String  devices;           //投放的设备集合
//   private int playAlone;             //	(1,轮播，2.独播)
//   private int putInKind;            //投放类型(1.购买，2.赠送）






    private String orderId;    //订单id
    private String adCorpId;        //预定广告公司id

    private String tradeId;        //行业
    private String blankId;    //品牌id
    private String maker;    //业务员id
    private String systems;    //投放的系统（001 ， '旅业系统',002.'娱服系统'）
    private String effect;   //有效标志(0,失效，1.有效)
    private String effecter;  //撤销人(标记为 "auto" 为到了预定要播放的时间前5天，仍未确认的订单，系统将自动将其设为失效)
    private String effectTime;  // 撤销时间
    private String payMoney;     //付款金额
    private String orderMemo;    //订单备注
    private String makeTime;    //下单日期


    public TableAdorderPolicy() {
    }
}


