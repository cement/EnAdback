package com.en.adback.entity.devicemgr;

import lombok.Data;

/**设备分组副表
 * Created by Administrator on 2018/12/3.
 */
@Data
public class DeviceSubGroup {

    private int id;  	//主键自增id
    private String devGroupId; 	//设备分组id
    private String deviId;	//设备id

    public DeviceSubGroup() {
    }
}
