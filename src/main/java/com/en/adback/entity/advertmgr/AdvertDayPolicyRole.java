package com.en.adback.entity.advertmgr;

import lombok.Data;

/**每日播放策略文件表(每天計算大後天的供設備下載的播放策略)
 * Created by Administrator on 2018/12/3.
 */
@Data
public class AdvertDayPolicyRole {

    private int id ;  	//id
    private String theDay;  	//日期
    private String deviceId;  	//設備ID
    private String roleContent; 	//播放策略 （json 格式)
    private String mediaFiles;    // 设备需要下载的文件
    private String browseTime;    // 设备请求时间

    public AdvertDayPolicyRole() {
    }
}
