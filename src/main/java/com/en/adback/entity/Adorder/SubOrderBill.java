package com.en.adback.entity.Adorder;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubOrderBill {
    private long id;          //订单字表id
    private String  orderId; // 订单号
    private String   screenCutId; // 切屏id
    private String  advertId; // 广告id (预定，未加入广告时，本字段用 订单号-1 ，-2 ，-3  标识， 确认后， 填入)
    private String advertName; // 广告名称
}
