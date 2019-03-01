package com.en.adback.entity.calpolicy;

import lombok.Data;

import java.util.List;

//弹屏广告
@Data
public class OpenScreen {
      private List<AdFile> openTip; // 弹屏提示广告
      private List<AdFile> openScreen; // // 弹屏正屏广告

      public OpenScreen() {
      }

      public OpenScreen(List<AdFile> openTip, List<AdFile> openScreen) {
            this.openTip = openTip;
            this.openScreen = openScreen;
      }


}
