package com.en.adback.entity.sys;

import com.en.adback.entity.UserAction;
import lombok.Data;

@Data
public class SysSetting extends UserAction {

    private int maxAdvertCounts;
    private int oneAdvertDuration;
}
