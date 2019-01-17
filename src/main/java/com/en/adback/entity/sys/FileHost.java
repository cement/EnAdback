package com.en.adback.entity.sys;

import lombok.Data;

/**
 * Created by Administrator on 2018/12/5.
 */
@Data
public class FileHost {

    private String cityId;	//市州Id
    private String cityName;//市州名称
    private String ip; 	//ip
    private String url; 	//文件下载路径
    private String appName;  	//项目名称



    public FileHost() {
    }
}
