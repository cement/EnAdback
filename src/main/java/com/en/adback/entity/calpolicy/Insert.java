package com.en.adback.entity.calpolicy;

import lombok.Data;

import java.util.Map;

//插播广告
@Data
public class Insert {
    private Map<String,Object> targetScreen; // 各种播放屏幕集合

    public Insert() {
    }

    public Insert(Map<String,Object> targetScreen) {
            this.targetScreen = targetScreen;
        }
}
