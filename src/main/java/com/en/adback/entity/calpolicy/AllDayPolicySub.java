package com.en.adback.entity.calpolicy;

import lombok.Data;

//本天策略字表
@Data
public class AllDayPolicySub {

  private String  advertPolicysId;
  private String position;
  private String downloadfilename;
  private float duration;
  private String devices ;
  private String advertId;
    public AllDayPolicySub() {
    }

  public AllDayPolicySub(String advertPolicysId, String position, String downloadfilename, float duration, String devices) {
    this.advertPolicysId = advertPolicysId;
    this.position = position;
    this.downloadfilename = downloadfilename;
    this.duration = duration;
    this.devices = devices;
  }
}
