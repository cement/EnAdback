package com.en.adback.entity.Adorder;

import com.en.adback.entity.advertmgr.TableSubAdvertPolicys;
import lombok.Data;

// 数据库表的广告策略字表类
@Data
public class TableAdorderSubPolicy extends TableSubAdvertPolicys {
    /*继承的属性及说明*/
//   private String advertPolicysId;//策略id
//   private String screenCutId;  //切屏id
//   private String advertId ;       //广告id (预定，未加入广告时，本字段用 订单号-1 ，-2 ，-3  标识， 确认后， 填入  )

    private String id; 	//自增id
    private String orderId; 	//订单号
    private String orderFirmTime; 	//确认订单日期


    public TableAdorderSubPolicy() {
    }
}
