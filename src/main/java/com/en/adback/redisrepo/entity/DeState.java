package com.en.adback.redisrepo.entity;

import lombok.Data;

import java.util.List;

@Data
public class DeState {
     private String  theDate ; // 日期
     private List<DeScreen> screens; // 屏幕
    public DeState() {
    }

    public DeState(String theDate, List<DeScreen> screens) {
        this.theDate = theDate;
        this.screens = screens;
    }
}
