package com.en.adback.mapper;


import com.en.adback.entity.Menu;
import com.en.adback.entity.sys.RolePower;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface IMenuMapper {

    // 获取所有有效菜单项
    @Select("select menuId,menuName,pareMenuId,url,iused from ad.t_menu where iused=1 order by  menuid, paixu ")
    public List<Menu> menuList();


    @Select("<script>" +
            "select menus from ad.t_grouprole where groupRoleId='${groupRoleId}'" +
            "</script>")
    String getMenus(Map<String,Object > re);

    //获取每个角色的权限
    @Select("select roleId, menuId,powers from ad.t_rolepower where roleId='${roleId}'")
    List<RolePower> rolePowerByRole(Map<String,Object> re);

    //查询子菜单的上级菜单
    @Select("select pareMenuId from ad.t_menu where menuId = '${leafMenuId}'")
    String getPareMenuId(Map<String,Object> re);
}
