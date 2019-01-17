package com.en.adback.entity.dic;

import lombok.Data;

@Data
public class Area {
    private String nid ; //编号
    private String  nationId ; //区域编号
    private String  nationName ; //区域名称
    private String  spell ; //拼音
    private String  aspell ; //简拼
    private String  longitude ;// 经度
    private String  latitude ; //纬度
    private int areaAttr ; // 1. 市州，2.区。3.县

    public Area() {
    }
}
