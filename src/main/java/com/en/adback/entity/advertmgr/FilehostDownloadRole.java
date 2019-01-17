package com.en.adback.entity.advertmgr;

import lombok.Data;

/**文件服务器下发策略表
 * Created by Administrator on 2018/12/3.
 */
@Data
public class FilehostDownloadRole {

    private int id;   	//id
    private String fileName; 	//要下发的文件名
    private String hostUrl;  	//目标服务器
    private String beginPlayTime;  	//在设备上开始播放的日期
    private boolean sended;  	//是否成功下发
    private String cityId;     //  市州id
    private String downloadUrl;  // 下载路径
    private String arrivedCityHostTime; // 到达服务器时间

    public FilehostDownloadRole() {
    }
}
