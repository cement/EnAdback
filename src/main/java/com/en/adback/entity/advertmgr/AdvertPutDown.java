package com.en.adback.entity.advertmgr;

import com.en.adback.entity.sys.User;
import lombok.Data;

// 手动下刊类
@Data
public class AdvertPutDown {
    private User opUser;   //  下刊人,
    private String opTime; // 下刊时间
    private String memo ;  // 下刊说明

    public AdvertPutDown() {
    }
}
