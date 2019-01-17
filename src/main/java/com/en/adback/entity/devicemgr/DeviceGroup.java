package com.en.adback.entity.devicemgr;

import lombok.Data;

/**设备分组主表
 * Created by Administrator on 2018/12/3.
 */
@Data
public class DeviceGroup {

    private String devGroupId;   	//设备分组id
    private String devGroupName;  	//设备分组名称
    private String addUser;  	//分组添加人(登录用户id)
    private String addTime;  	//添加时间
    private String deviceIds; // 设备
    public DeviceGroup() {
    }
}
