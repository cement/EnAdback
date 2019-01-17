package com.en.adback.entity.dic;

import lombok.Data;

/**广告状态
 * Created by Administrator on 2018/12/3.
 */
@Data
public class AdvertState {

    private int state;   	//状态标志
    private String stateName;  	//状态名称
    private String stateTime;   	//填写时间

    public AdvertState() {
    }
}
