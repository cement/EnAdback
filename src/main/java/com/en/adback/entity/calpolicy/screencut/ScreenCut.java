package com.en.adback.entity.calpolicy.screencut;

import lombok.Data;

//切分屏类
@Data
public class ScreenCut {
    private String screenCutId;
    private String screenCutName;
    private String position;
    private String screenSize;
    private String advertId;
    public ScreenCut() {
    }

    public ScreenCut(String screenCutId, String screenCutName, String position, String screenSize, String advertId) {
        this.screenCutId = screenCutId;
        this.screenCutName = screenCutName;
        this.position = position;
        this.screenSize = screenSize;
        this.advertId = advertId;
    }
}
