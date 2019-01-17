package com.en.adback.entity.calpolicy.screencut;

import lombok.Data;

import java.util.List;

//屏幕类
@Data
public class Screen {
    private String screenId;
    private String screenName;
    private List<ScreenCut> cutScreenForm; // 切分屏组成
    private boolean choosed ;
    public Screen() {
    }

    public Screen(String screenId, String screenName, List<ScreenCut> cutScreenForm, boolean choosed) {
        this.screenId = screenId;
        this.screenName = screenName;
        this.cutScreenForm = cutScreenForm;
        this.choosed = choosed;
    }
}
