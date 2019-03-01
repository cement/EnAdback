package com.en.adback.entity.sys;

import com.en.adback.entity.UserAction;
import lombok.Data;

/**
 * Created by Administrator on 2018/12/17.
 */
@Data
public class GroupRole extends UserAction {

    private String groupRoleId; // 组id
    private String groupRoleName ;  // 组名称
    private String memo ; // 备注
    private String menus  ;// 可操作的菜单项 逗号分割 菜单id
    private String state;
    private boolean appFunction;//是否拥有公众号权限

    public GroupRole() {
    }
}
