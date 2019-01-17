package com.en.adback.entity.sys;

import com.en.adback.entity.Menu;
import lombok.Data;

import java.util.List;

/**
 * 权限分组
 */

@Data
public class PowerGroup {


    private String pGroupId ; // 分组编号
    private String pGroupName; // 分组名称
    private boolean isSupper ; // 超级权限组
    private List<Menu> manageMenu ; // 该组管理的菜单
    private List<Role> roles ; // 该组所辖角色组
}
