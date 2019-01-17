package com.en.adback.entity.sys;

import lombok.Data;

/**
 * Created by Administrator on 2018/12/21.
 */
@Data
public class MenuId {

    private String  pareMenuId;//上级菜单Id
    private String menuId;//菜单Id
    private String  subMenuId;//子菜单Id

}
