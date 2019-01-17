package com.en.adback.service;



import com.en.adback.entity.Menu;
import com.en.adback.entity.sys.RolePower;

import java.util.List;
import java.util.Map;

public interface IMenuService {
    public List<Menu> menuList();

    String getMenus(Map<String,Object > re);

    List<RolePower> rolePowerByRole(Map<String,Object> re);

    String getPareMenuId(Map<String,Object> re);
}
