package com.en.adback.entity.sys;

import com.en.adback.entity.UserAction;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 角色类
 */
@Data
public class Role extends UserAction {
    private String roleId;//角色id
    private String roleName;//角色名称
    private String groupRoleId;//分组id
    private String groupRoleName;//分组名称
    private String memo;//备注
    private String state;//操作状态（新增、更新）

    public Role() {
    }
}


