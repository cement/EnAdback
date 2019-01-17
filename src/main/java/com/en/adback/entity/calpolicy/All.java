package com.en.adback.entity.calpolicy;

import lombok.Data;

import java.util.Map;

//全屏广告
@Data
public class All {
    private Map<String,Object> targetScreen; // 各种播放屏幕集合

    public All() {
    }

    public All(Map<String,Object> targetScreen) {
        this.targetScreen = targetScreen;
    }
}
