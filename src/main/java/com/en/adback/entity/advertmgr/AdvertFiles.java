package com.en.adback.entity.advertmgr;

import lombok.Data;

/**广告资料文件表
 * Created by Administrator on 2018/12/3.
 */
@Data
public class AdvertFiles {

    private String downLoadFileName;      	//下载时的文件名(唯一)
    private String advertId;  	//广告编号
    private String fileName;  	//文件名称
    private String downLoadUrl;   	//下载地址

    public AdvertFiles() {
    }
}
