package com.en.adback.mapper.sys;

import com.en.adback.entity.sys.GroupRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/17.
 */
@Mapper
public interface GroupRoleMapper {

    //查询
    @Select("<script>" +
            "select groupRoleId,groupRoleName,memo,menus,appFunction from ad.t_grouprole" +
            " limit ${pageSize} offset ${pageBegin} " +
            "</script>")
    List<GroupRole> getGroupRoleList(Map<String,Object> re);

    @Select("<script>" +
            " select count(*) from ad.t_grouprole" +
            "</script>")
    int getGroupRoleListTotal();

    //新增、修改
    @Insert("<script>" +
            "upsert into ad.t_grouprole(groupRoleId,groupRoleName,memo,menus,appFunction) " +
            " values('${groupRoleId}','${groupRoleName}','${memo}','${menus}',${appFunction}) " +
            "</script>")
    int upsertGroupRole(GroupRole groupRole);

    //查询该GroupRoleId是否已存在
    @Select("<script>" +
            " select * from ad.t_grouprole where groupRoleId='${groupRoleId}'" +
            "</script>")
    List<GroupRole> getGroupRoleById(Map<String,String > re);

    //删除
    @Delete("<script>" +
            "delete from ad.t_grouprole where groupRoleId='${groupRoleId}'" +
            "</script>")
    int deleteGroupRole(Map<String,String> re);


    //检查具有公众号权限的分组是否已存在
    @Select("<script>" +
            " select * from ad.t_grouprole where appFunction = true" +
            "</script>")
    List<GroupRole> checkAppGroupRole();
}
