package com.en.adback.entity;

import lombok.Data;

@Data
public class Menu {
    private String  menuId ; // 菜单编号
    private String menuName ;//菜单名称
    private String pareMenuId ;//上级菜单编号
    private String url ; // url
    private boolean iused ;//是否有效

    public Menu() {
    }
}
