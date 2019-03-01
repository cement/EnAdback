package com.en.adback.entity.Adorder;

import com.en.adback.entity.advertmgr.AdvertPolicys;
import com.en.adback.entity.advertmgr.PlayPolicyScreen;
import lombok.Data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 单广告播放策略表
 * Created by Administrator on 2018/12/3.
 */
@Data
public class AdorderPolicy {



    private OrderQueryList order;
    private List<SubOrderBill> subOrder = new LinkedList<>();
    private List<PlayPolicyScreen>  screenpolicys;
    /*继承的属性以及说明*/
//   private String  advertPolicysId ; // 廣告策略id
//   private List<PlayPolicyScreen>  screenpolicys; 	// 屏幕策略(选中的屏幕策略包含其中)
//   private String  playDates; 	// 播放日期 （,号组成的日期串）
//   private String  playTimeBegin; 	// 开始播放时间(默认零点)
//   private String  playTimeEnd;  	// 结束播放时间(默认24点 ,如果为插播广告，设置具体时间)
//   private String  devices  ;   	// 投放的设备集合
//   private int  playAlone;	// (0,轮播，1.独播)
//   private int  putInKind ; // 投放类型(1.购买，2.赠送）


    public AdorderPolicy() {

    }

}

