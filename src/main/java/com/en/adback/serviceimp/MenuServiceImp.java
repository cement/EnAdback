package com.en.adback.serviceimp;


import com.en.adback.entity.Menu;
import com.en.adback.entity.sys.RolePower;
import com.en.adback.mapper.IMenuMapper;
import com.en.adback.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImp implements IMenuService {
    @Autowired
    private IMenuMapper mapper;
    @Override
    public List<Menu> menuList() {
        List<Menu> list=mapper.menuList();
        return list;
    }

    @Override
    public String getMenus(Map<String, Object> re) {
        return mapper.getMenus(re);
    }

    @Override
    public List<RolePower> rolePowerByRole(Map<String, Object> re) {
        return mapper.rolePowerByRole(re);
    }

    @Override
    public String getPareMenuId(Map<String, Object> re) {
        return mapper.getPareMenuId(re);
    }
}
