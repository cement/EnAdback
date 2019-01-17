package com.en.adback.entity.calpolicy;

import lombok.Data;

//所有本天策略
@Data
public class AllDayPolicy {
   private String  advertPolicysId;
   private String screenPolicyId;
   private String screenId;
   private String playTimeBegin;
   private String playTimeEnd;
   private String devices;
    public AllDayPolicy() {
    }
}
