package com.en.adback.entity.advertmgr;

import lombok.Data;

/**广告媒体表
 * Created by Administrator on 2018/12/3.
 */
@Data
public class AdvertMedia {
    private String downLoadFileName;   	//下载时的文件名(唯一)
    private String advertId;   	//广告编号
    private String fileName;  	//文件名称
    private String fileSize;   	//文件大小 （单位kb,mb）
    private Double duration; 	//播放时长
    private String downLoadUrl;  	//下载地址

    public AdvertMedia() {
    }
}
