package com.en.adback.entity.advertmgr;

import lombok.Data;

// 数据库表的广告策略字表类
@Data
public class TableSubAdvertPolicys {
   private String advertPolicysId;
   private String screenCutId;
   private String advertId ;

    public TableSubAdvertPolicys() {
    }
}
