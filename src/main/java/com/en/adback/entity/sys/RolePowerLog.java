package com.en.adback.entity.sys;

import com.en.adback.entity.UserAction;
import lombok.Data;

/**
 * Created by Administrator on 2019/1/7.
 */
@Data
public class RolePowerLog extends UserAction{

    private RolePower[] rolePowerArray;
}
