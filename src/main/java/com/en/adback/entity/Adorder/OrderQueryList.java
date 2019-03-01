package com.en.adback.entity.Adorder;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderQueryList {

   private String  orderId; // 订单id
    private String   adCorpId; // 预定广告公司id
    private String   tradeId; // 行业
    private String  blankId; // 品牌id
    private String  maker; // 业务员id
    private String  makeTime; // 下单日期
    private String  advertPolicysId; // 策略id
    private String  screenPolicyId; // 屏幕策略id
    private String  screenPolicyName; // 屏幕策略名称
    private String  screenId; // 屏幕id
    private String  playDates; // 播放日期
    private String  playTimeBegin; // 开始播放时间(插播广告用)
    private String  playTimeEnd; // 结束播放时间
    private String  devices; // 投放的设备集合
    private int  playAlone; // (1:轮播，2.独播)
    private int  putInKind ; // 投放类型(1.购买，2.赠送）
    private String  systems; // 投放的系统（001 ， '旅业系统':002.'娱服系统'）
    private int  payMoney; // 付款金额
    private String  orderMemo; // 订单备注
    private String tradeName;  // 行业名称
    private String  blankName;   // 品牌名称
    private String  adCorpName;   // 公司名称
    private int  advertCounts;  // 广告数量
    private String makerName;  // 业务员名称
    private String  effectTime;  // 失效时间
    private String orderStateId;  // 订单状态
    // 1. 待确认：为还未确认的订单；
    // 2. 待分发：设置了广告，还没到投放日期的订单；
    // 3. 已完成：设置了广告，已经到了投放日期；
    // 4. 已失效：撤销了的订单，或已经到了失效日期的订单。
    private String affirmer; //确认人
    private String  orderFirmTime ; //订单确认时间

}
