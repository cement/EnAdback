package com.en.adback.entity.dic;

import lombok.Data;

/**设备状态
 * Created by Administrator on 2018/12/3.
 */
@Data
public class DeviceStatus {
    private int devStatusId;	//设备状态编号 1.在运行，2.报损待处理，3.维修，4.更换-淘汰
    private String devStatusName;   	//设备状态名称

    public DeviceStatus() {
    }
}
