package com.en.adback.entity.advertmgr;

import lombok.Data;

/**廣告狀態歷史表
 * Created by Administrator on 2018/12/3.
 */
@Data
public class AdvertStateHis {

    private int id; 	//id
    private String advertId; 	//广告Id
    private int nowState; 	//当前状态 (1. 加入未提交审核，2.审核中，3.审核通过，4.审核不通过,5.设置策略,6.待分发， 7.已下发到设备,8.替换9.被替换，10.自动下刊,11.手动下刊)
    private String maker;   	//狀態改變人
    private String makeTimer;   	//狀態改變時間
    private String memo;      	//備注

    public AdvertStateHis() {
    }

    public AdvertStateHis(String advertId, int nowState, String maker, String makeTimer, String memo) {
        this.advertId = advertId;
        this.nowState = nowState;
        this.maker = maker;
        this.makeTimer = makeTimer;
        this.memo = memo;
    }
}
