package com.en.adback.entity.devicemgr;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllDevice {
    private String   deviceId  ;//  	设备编号
    private String   receiveTime ;//  	入库时间
    private String   receiveMan ;//  	入库人编号
    private String   useState  ;//    	运行状态 0, 正常,1.异常
    private String   alertTime  ;// 	异常报警时间
    private String   enterpriseId ;//              所在企业id
    private String   enterpriseName ;//       所在企业名称
    private String   cityId      ;//                  企业所属市州id
    private String  cityName     ;//             企业所属市州Name
    private String   quxianId    ;//                企业所属区县id
    private String   quxianName   ;//          企业所属区县Name
    private String   paichusuoId    ;//         企业所属派出所id
    private String   paichusuoName  ;//          企业所属派出所Name
    private String   systemId    ;//               系统类别id
    private String   stateId      ;//                最后状态id
    private String   stateTime      ;//           最后状态改变时间
    private int      beds  ;   // 床位数
    private String   address ; //  地址
    private int playCounts;//播放频次

}
