package com.en.adback.service.sys;

import com.en.adback.entity.sys.MenuId;
import com.en.adback.entity.sys.Role;
import com.en.adback.entity.sys.RolePower;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/21.
 */
public interface IRolePowerService {

    //获取角色列表
    List<Role> getRoleList(Map<String,Object> re);

    //根据分组Id获得这一组的所有权限
    String getMenus(Map<String,Object > re);

    //判断是否还有子页面
    List<MenuId> getMenuIdList(Map<String,Object> re);

    //对于每个页面的最终权限
    List<RolePower> getRolePowerList(Map<String,Object> re);

    //获得属于一级菜单的权限
    List<RolePower> getMaxMenuRolePower(Map<String,Object> re);

    //当拥有所有权限时
    List<RolePower> getAllRolePowerList(Map<String,Object> re);

    //获取一级菜单
    List<RolePower> getMaxMenu(Map<String,Object> re);

    List<Role> getByRoleId(Map<String,String > re);

    int upsertRole(Role role);

    int deleteRole(Map<String,String > re);

    //删除权限
    int deleteRolePower(Map<String,String > re);

    //插入权限
    int insertRolePower(RolePower rolePower);

    //管理员查询所有角色信息
    List<Role> getAllRoleByAdminList();

    //角色分组名称
    String getGroupRoleName(Map<String,String > re);
}
