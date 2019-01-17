package com.en.adback.entity.sys;

import lombok.Data;

import java.util.List;

/**
 * 菜单类
 */

@Data
public class Menu {

    private String MenuId; // 菜单编号
    private String MenuName; // 菜单名称
    private String url ; // 链接地址
    private String pareMenuId ; // 子菜单
    private boolean isUseing; // 是否启用
}
