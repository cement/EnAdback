package com.en.adback.mapper.sys;

import com.en.adback.entity.sys.GroupRole;
import com.en.adback.entity.sys.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {



    @Select("<script>" +
            "select userid,username,tel,groupRoleId,roleId,ifGroupManager from ad.t_user where userid='${userId}' and password='${passWord}'" +
            "</script>")
    public List<User> validateLogin(User user);


    //根据用户名以及填入的原密码查询用户
    @Select("<script>" +
            "select *  from ad.t_user where userId='${userId}'and password='${passWord}'" +
            "</script>")
    List<User> getUserList(Map<String,Object> re);


    //插入新的密码
    @Insert("<script>" +
            " upsert into ad.t_user(userId,passWord) values('${userId}','${newPassWord}')" +
            "</script>")
    int changePassWord(Map<String,Object> re);

    //管理员查询所有用户
    @Select("<script>" +
            "select userId,userName,tel,u.roleId as roleId,isManager,password,u.groupRoleId as groupRoleId,gr.groupRoleName as groupRoleName,ifGroupManager,roleName from ad.t_user u\n" +
            " left join ad.t_grouprole gr on u.groupRoleId = gr.groupRoleId" +
            " left join ad.t_role r on r.roleId = u.roleId " +
            " where <![CDATA[ userId <> 'admin' ]]>  " +
            "<if test='userId!=\"\" and userId!=null '>" +
            " and userId = '${userId}'" +
            "</if>" +
            "<if test='userName!=\"\" and userName!=null '>" +
            " and userName like '%${userName}%'" +
            "</if>" +
            "<if test='tel!=\"\" and tel!=null '>" +
            " and tel like '%${tel}%'" +
            "</if>" +
            "<if test='groupRoleId!=\"\" and groupRoleId!=null and groupRoleId!=\"0\"'>" +
            " and u.groupRoleId = '${groupRoleId}'" +
            "</if>" +
            "<if test='roleId !=\"\" and roleId !=null and roleId !=\"0\"'>" +
            " and u.roleId = '${roleId}'" +
            "</if>" +
            " limit ${pageSize} offset ${pageBegin} " +
            "</script>")
     List<User> findAllUser(Map<String,Object> re);

    @Select("<script>" +
            " select count(*) from ad.t_user u " +
            " left join ad.t_grouprole gr on u.groupRoleId = gr.groupRoleId" +
            " left join ad.t_role r on r.roleId = u.roleId " +
            " where <![CDATA[ userId <> 'admin' ]]>" +
            "<if test='userId!=\"\" and userId!=null '>" +
            " and userId = '${userId}'" +
            "</if>" +
            "<if test='userName!=\"\" and userName!=null '>" +
            " and userName like '%${userName}%'" +
            "</if>" +
            "<if test='tel!=\"\" and tel!=null '>" +
            " and tel like '%${tel}%'" +
            "</if>" +
            "<if test='groupRoleId!=\"\" and groupRoleId!=null and groupRoleId!=\"0\"'>" +
            " and u.groupRoleId = '${groupRoleId}'" +
            "</if>" +
            "<if test='roleId !=\"\" and roleId !=null and roleId !=\"0\"'>" +
            " and u.roleId = '${roleId}'" +
            "</if>" +
            "</script>")
    int getGroupRoleTotal(Map<String,String > re);

    //组管理员查询该组用户信息
    @Select("<script>" +
            "select userId,userName,tel,u.roleId as roleId,isManager,password,u.groupRoleId as groupRoleId,gr.groupRoleName as groupRoleName,ifGroupManager,roleName from ad.t_user u\n" +
            "             left join ad.t_grouprole gr on u.groupRoleId = gr.groupRoleId\n" +
            "             left join ad.t_role r on r.roleId = u.roleId \n" +
            "             where 1=1 and u.groupRoleId = '${groupRoleId}'" +
            "<if test='userId!=\"\" and userId!=null '>" +
            " and userId = '${userId}'" +
            "</if>" +
            "<if test='userName!=\"\" and userName!=null '>" +
            " and userName like '%${userName}%'" +
            "</if>" +
            "<if test='tel!=\"\" and tel!=null '>" +
            " and tel like '%${tel}%'" +
            "</if>" +
            "<if test='roleId !=\"\" and roleId !=null and roleId !=\"0\"'>" +
            " and u.roleId = '${roleId}'" +
            "</if>" +
            " limit ${pageSize} offset ${pageBegin} " +
            "</script>")
   List<User> getUserByGroupRoleId(Map<String,Object> re);

    //组管理员的查询总数
    @Select("<script>" +
            " select count(*) from ad.t_user u " +
            " left join ad.t_grouprole gr on u.groupRoleId = gr.groupRoleId \n" +
            " left join ad.t_role r on r.roleId = u.roleId \n" +
            " where 1=1 and u.groupRoleId = '${groupRoleId}'" +
            "<if test='userId!=\"\" and userId!=null '>" +
            " and userId = '${userId}'" +
            "</if>" +
            "<if test='userName!=\"\" and userName!=null '>" +
            " and userName like '%${userName}%'" +
            "</if>" +
            "<if test='tel!=\"\" and tel!=null '>" +
            " and tel like '%${tel}%'" +
            "</if>" +
            "<if test='roleId !=\"\" and roleId !=null and roleId !=\"0\"'>" +
            " and u.roleId = '${roleId}'" +
            "</if>" +
            "</script>")
    int getUserByGroupRoleIdTotal(Map<String,String > re);

    //插入用户
    @Insert("<script>" +
            "upsert into ad.t_user(userId,userName,tel,roleId,isManager,password, groupRoleId,ifGroupManager) " +
            " values('${userId}','${userName}','${tel}','${roleId}',${isManager},'${passWord}', '${groupRoleId}',${ifGroupManager})" +
            "</script>")
     int insertUser(User user);

    @Insert("<script>" +
            "upsert into ad.t_user(userId,userName,tel,roleId,isManager, groupRoleId,ifGroupManager) " +
            " values('${userId}','${userName}','${tel}','${roleId}',${isManager},'${groupRoleId}',${ifGroupManager})" +
            "</script>")
    int updateUser(User user);


    //根据用户Id查询该用户ID是否已存在
    @Select("<script>" +
            "select * from ad.t_user where userId = '${userId}'" +
            "</script>")
    List<User> getByUserId(Map<String,String > re);

    @Delete("<script>" +
            " delete from ad.t_user where userId = '${userId}'" +
            "</script>")
    int deleteUser(Map<String,String > re);

    //查询没有确认组管理员的角色组
    @Select("<script>" +
            "  select gr.groupRoleId as groupRoleId,groupRoleName ,gr.memo as memo from ad.t_groupRole gr \n" +
            " where gr.groupRoleId not in (select groupRoleId from ad.t_user where ifgroupmanager = true)" +
            "</script>")
    List<GroupRole> getGroupRoleForInsert();
}
