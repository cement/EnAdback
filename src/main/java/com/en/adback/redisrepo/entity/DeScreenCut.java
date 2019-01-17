package com.en.adback.redisrepo.entity;

import lombok.Data;

import java.util.List;

//切屏状态类
@Data
public class DeScreenCut {
  private String   screenCutId ;
  private int playAlone ;// 1,轮播，2.独播
  private String playTimeBegin ;
  private String playTimeEnd ;
  private List<String> advertIds;

    public DeScreenCut() {
    }

  public DeScreenCut(String screenCutId, int playAlone, String playTimeBegin, String playTimeEnd, List<String> advertIds) {
    this.screenCutId = screenCutId;
    this.playAlone = playAlone;
    this.playTimeBegin = playTimeBegin;
    this.playTimeEnd = playTimeEnd;
    this.advertIds = advertIds;
  }
}
