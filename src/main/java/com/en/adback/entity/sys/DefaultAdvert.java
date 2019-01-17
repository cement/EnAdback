package com.en.adback.entity.sys;

import lombok.Data;

/**
 * Created by Administrator on 2018/12/8.
 */
@Data
public class DefaultAdvert {

    private String screenCutId; 	//屏幕切分Id
    private String screenCutName; 	//屏幕名称
    private String fileName; 	//文件名称
    private String position; // 位置
    private String screenSize; // 屏幕尺寸
    private String defaultFileName; 	//默认文件名


    public DefaultAdvert() {
    }
}
