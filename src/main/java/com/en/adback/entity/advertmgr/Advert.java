package com.en.adback.entity.advertmgr;

import com.en.adback.controller.sys.UserLogs;
import com.en.adback.entity.UserAction;
import lombok.Data;

import java.util.List;

/**
 * 广告类
 */

@Data
public class Advert extends UserAction{
    private String advertId ; 	// 广告编号
    private String advertName;  	// 广告名称
    private String adCorpId ;   	// 所属企业id
    private String adCorpName;  // 所属企业名称
    private String blankId; // 品牌id
    private String blankName; // 品牌名称
    private String tradeId; // 行业编号
    private String tradeName; // 行业名称
    private String fileName; // 媒体文件名称
    private double duration; // 播放时长
    private String fileSize; // 文件大小
    private String maker; // 账号
    private String uploadTime ;  	// 上传时间
    private int nowState;   	//当前状态 (1. 加入未提交审核，2.审核中，3.审核通过，4.审核不通过,5.设置策略,6.待分发， 7.已下发到设备,8.替换9.被替换，10.自动下刊,11.手动下刊)
    private AdvertMedia media;
    private List<AdvertFiles> files;
    private String state;//当前状态 Excel用到

    public Advert() {
    }
}
