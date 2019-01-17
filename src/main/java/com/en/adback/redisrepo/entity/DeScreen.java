package com.en.adback.redisrepo.entity;

import lombok.Data;

import java.util.List;

@Data
public class DeScreen {
  private String   screenId;
  private String screenName;
  private List<DeScreenCut> screenCuts;
    public DeScreen() {
    }

  public DeScreen(String screenId, String screenName, List<DeScreenCut> screenCuts) {
    this.screenId = screenId;
    this.screenName = screenName;
    this.screenCuts = screenCuts;
  }
}
