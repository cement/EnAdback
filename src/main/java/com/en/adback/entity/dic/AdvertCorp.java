package com.en.adback.entity.dic;

import com.en.adback.entity.UserAction;
import lombok.Data;


/**
 *  广告公司类
 */
@Data
public class AdvertCorp extends UserAction{
    private String adCorpId;  //公司编号
    private String adCorpName;  //公司名称
    private String address ; // 地址
    private String  tradeId; // 所属行业
    private String  tradeName; // 行业名称
    private String blankId; // 品牌
    private String blankName; // 品牌名称
    private String tel ; // 电话
    private String linkMan ; // 联系人
    private String addTime; // 加入时间
    private String addUser ; // 添加人
    private String pinyin; //拼音

    public AdvertCorp() {
    }
}




















