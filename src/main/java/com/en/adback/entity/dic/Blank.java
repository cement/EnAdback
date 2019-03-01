package com.en.adback.entity.dic;

import com.en.adback.entity.UserAction;
import lombok.Data;


import java.util.List;

@Data
public class Blank extends UserAction{

    private String blankId; //品牌id
    private String blankName; //品牌名称
    private String pareBlankId  ; // 上级品牌id（0，无上级）
    private String pareBlankName;//上级品牌名称
    private String memo  ; // 备注
    private String pinyin; //拼音
    public Blank() {
    }
}
