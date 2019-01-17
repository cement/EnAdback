package com.en.adback.entity.sys;

import com.en.adback.entity.UserAction;
import lombok.Data;

/**
 * Created by Administrator on 2018/12/21.
 */
@Data
public class RolePower extends UserAction{

   private String roleId;//角色Id
    private String menuId ;//菜单id
    private String  pareMenuId ;//上级菜单编号
    private String  menuName;//菜单名称
    private String powers;//权限


}
