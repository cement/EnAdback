package com.en.adback.mapper.sys;

import com.en.adback.entity.sys.MenuId;
import com.en.adback.entity.sys.Role;
import com.en.adback.entity.sys.RolePower;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/21.
 */
@Mapper
public interface RolePowerMapper {

    @Select("<script>" +
            " select roleId,roleName ,memo from ad.t_role where groupRoleId  = '${groupRoleId}' " +
            "</script>")
    List<Role> getRoleList(Map<String,Object> re);

    @Select("<script>" +
            "select menus from ad.t_grouprole where groupRoleId='${groupRoleId}'" +
            "</script>")
    String getMenus(Map<String,Object > re);

    //组权限菜单的上级菜单和下级菜单
    @Select("<script>" +
            " select m.pareMenuId as pareMenuId ,m.menuId as menuId ,sub.menuId as subMenuId from ad.t_menu m\n" +
            " left join ad.t_menu sub on m.menuId = sub.pareMenuId\n" +
            " where m.menuId='${menuId}'" +
            "</script>")
    List<MenuId> getMenuIdList(Map<String,Object> re);

    //获取权限列表
    @Select("<script>" +
            " select sa.roleId as roleId,sa.menuId as menuId,sa.pareMenuId as pareMenuId,sa.menuName as menuName,case when rp.powers is null then defaultPower else rp.powers end powers from \n" +
            " (select '${roleId}' as roleId, defaultPower,menuId,pareMenuId,menuName from ad.t_menu where menuId='${leafMenuId}') sa\n" +
            " left join (select roleId,menuId,powers from ad.t_rolepower where menuId='${leafMenuId}') rp on  rp.roleId = sa.roleId " +
            "</script>")
    List<RolePower> getRolePowerList(Map<String,Object> re);

    //当权限为一级菜单时 获取权限
    @Select("<script>" +
            " select '${roleId}' as roleId,m.menuId as menuId ,m.pareMenuId as pareMenuId ,m.menuName as menuName,case when rp.powers is null then m.defaultpower else rp.powers end powers from ad.t_menu m\n" +
            " left join (\n" +
            " select roleId,me.menuId as menuId,powers from ad.t_rolepower me inner join ad.t_menu menu on menu.menuId = me.menuId where menu.pareMenuId = '${pareMenuId}' and roleId = '${roleId}' \n" +
            " ) rp on rp.menuId = m.menuId where m.pareMenuId='${pareMenuId}' and m.iused=1 " +
            "</script>")
    List<RolePower> getMaxMenuRolePower(Map<String,Object> re);

    //当权限为0时（即所有权限）
    @Select("<script>" +
            " select '${roleId}' as roleId,m.menuId as menuId ,m.pareMenuId as pareMenuId ,m.menuName as menuName,case when rp.powers is null then m.defaultpower else rp.powers end powers from ad.t_menu m \n" +
            " left join (select ad.roleId,menuId ,powers from ad.t_rolePower ad  where ad.roleId='${roleId}') rp on rp.menuId = m.menuId" +
            " where m.iused =1" +
            "</script>")
    List<RolePower> getAllRolePowerList(Map<String,Object> re);

    //获取一级菜单
    @Select("<script>" +
            "select  '' as roleId,'' as powers, m.menuId as menuId ,m.pareMenuId as pareMenuId ,m.menuName as menuName from ad.t_menu m\n" +
            " left join ad.t_menu p on m.pareMenuId = p.menuId\n" +
            " where m.menuId = '${maxMenu}'" +
            "</script>")
    List<RolePower> getMaxMenu(Map<String,Object> re);

    //查询该roleId是否已存在
    @Select("<script>" +
            " select * from ad.t_role where roleId = '${roleId}'" +
            "</script>")
    List<Role> getByRoleId(Map<String,String > re);

    @Insert("<script>" +
            "upsert into ad.t_role(roleId,roleName,groupRoleId,memo) values('${roleId}','${roleName}','${groupRoleId}','${memo}')" +
            "</script>")
    int upsertRole(Role role);

    @Delete("<script>" +
            " delete from ad.t_role where roleId='${roleId}'" +
            "</script>")
    int deleteRole(Map<String,String > re);

    //删除角色权限
    @Delete("delete from ad.t_rolePower where roleId = '${roleId}'")
    int deleteRolePower(Map<String,String > re);

    // 添加角色权限
    @Insert("upsert into ad.t_rolePower(Id ,roleId,menuId,powers) values" +
            "( next value for ad.t_rolePower_seq, '${roleId}','${menuId}','${powers}')")
    int insertRolePower(RolePower rolePower);

    //管理员登录  查询所有角色信息
    @Select("<script>" +
            " select r.groupRoleId as groupRoleId,gr.groupRoleName as groupRoleName ,roleId,roleName,r.memo as memo from ad.t_role r\n" +
            " inner join ad.t_groupRole gr on gr.groupRoleId = r.groupRoleId" +
            "</script>")
    List<Role> getAllRoleByAdminList();

    //查询角色分组名称
    @Select("<script>" +
            " select groupRoleName from ad.t_groupRole where groupRoleId='${groupRoleId}'" +
            "</script>")
    String getGroupRoleName(Map<String,String > re);

}
