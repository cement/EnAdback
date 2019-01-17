package com.en.adback.entity.devicemgr;

import lombok.Data;


import java.util.List;
import java.util.Map;

/**
 * 设备类
 */
@Data
public class Device {
    private String deviceId; // 设备编号
    private String deviStatesId; // 设备状态 (1.在运行，2.报损待处理，3.维修，4.更换-淘汰)
    private String enterpriseId;	//企业id
    private String enterpriseName;
    private String systemId;//对应企业的所属行业（娱服、旅馆）
    private int beds;
    private String address;
    private String installPeopleId;
    private String installPeopleName;
    private String installDate ; // 安装日期
    private String remarks;

    public Device() {
    }
}
