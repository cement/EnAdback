package com.en.adback.entity.calpolicy;

import lombok.Data;

import java.util.List;

//开屏广告
@Data
public class OpenScreen {
      private List<AdFile> openTip; // 开屏提示广告
      private List<AdFile> openScreen; // // 开屏正屏广告

      public OpenScreen() {
      }

      public OpenScreen(List<AdFile> openTip, List<AdFile> openScreen) {
            this.openTip = openTip;
            this.openScreen = openScreen;
      }


}
