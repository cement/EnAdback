package com.en.adback.entity.advertmgr;

import lombok.Data;

// 数据库表的广告策略类
@Data
public class TableAdvertPolicys {
   private String  advertPolicysId ;
   private String screenPolicyId;
   private String screenId;
   private String playDates;
   private String playTimeBegin;
   private String playTimeEnd;
   private String  devices;
   private int playAlone;
   private int putInKind;


    public TableAdvertPolicys() {
    }
}
