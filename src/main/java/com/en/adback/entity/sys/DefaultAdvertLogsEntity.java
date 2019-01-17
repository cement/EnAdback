package com.en.adback.entity.sys;

import com.en.adback.entity.UserAction;
import lombok.Data;

/**
 * Created by Administrator on 2019/1/8.
 */
@Data
public class DefaultAdvertLogsEntity extends UserAction {

    private DefaultAdvert[] defaultAdvertList;
}
