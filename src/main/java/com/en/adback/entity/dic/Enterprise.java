package com.en.adback.entity.dic;

import com.en.adback.entity.devicemgr.Device;
import lombok.Data;


import java.util.List;

/**
 * 企业类
 */
@Data
public class Enterprise {
    private String enterpriseId; // 企业编号
    private String enterpriseName; // 企业名称
    private int beds ;// 床位数
    private String systemId; // 行业系统id(001,旅业系统)
    private String address ; // 企业地址
    private String orgId ; // 所属区域
    private String star;
    public Enterprise() {
    }
}
