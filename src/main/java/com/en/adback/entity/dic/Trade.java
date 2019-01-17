package com.en.adback.entity.dic;

import com.en.adback.entity.UserAction;
import lombok.Data;


/**
 * 行业类
 */

@Data
public class Trade extends UserAction{
    private String tradeId; //行业id
    private String tradeName; //行业名称
    private String memo  ; // 备注

    public Trade() {
    }
}
