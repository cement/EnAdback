package com.en.adback.entity.calpolicy;

import lombok.Data;

//播放策略里的 文件
@Data
public class AdFile {
   private  String  name; //文件唯一名称
   private  float time;    // 播放时长
   private  int Link ;  // 关联广告
   private  int beginTime ;// 开始播放时间（只对插播广告, 时间戳标识,非插播广告，用 -1 标识）
   private  int endTime;
    public AdFile() {
    }

    public AdFile(String name, float time, int link, int beginTime, int endTime) {
        this.name = name;
        this.time = time;
        Link = link;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }
}
