package com.en.adback.entity.advertmgr;

import com.en.adback.entity.sys.User;
import lombok.Data;

// 广告替换类
@Data
public class AdvertReplace {

    private String repId ; // 替换过来的广告_id
    private User opUser;   //  替换人,
    private String opTime; // 替换时间
    private String memo ;  // 替换说明

    public AdvertReplace() {
    }
}
