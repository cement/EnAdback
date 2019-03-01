package com.en.adback.service.sys;

import com.en.adback.entity.sys.GroupRole;
import com.en.adback.entity.sys.User;

import java.util.List;
import java.util.Map;

public interface IUserService {

    //验证登录
    public List<User> validateLogin(User user);

    // 根据 userId 查询
    public List<User> findbyUserId(String userId);

    // 查询所有用户
    List<User> findAllUser(Map<String,Object> re);

    int getGroupRoleTotal(Map<String,String > re);

    //组管理员查询该组的用户
    List<User> getUserByGroupRoleId(Map<String,Object> re);

    int getUserByGroupRoleIdTotal(Map<String,String > re);

    // 插入用户
    int insertUser(User user);

    int updateUser(User user);

    //根据userId查询该用户是否已存在
    List<User> getByUserId(Map<String,String > re);

    // 删除用户
    int deleteUser(Map<String,String> re);

    //修改密码  查询判断原密码是否正确
    List<User> getUserList(Map<String,Object> re);

    //保存新密码
    int changePassWord(Map<String,Object> re);

    List<GroupRole> getGroupRoleForInsert();

    //导出excel 管理员
    List<User> getUserExcelByAdmin(Map<String,Object> re);
    //导出Excel 组管理员
    List<User> getUserExcelByGroupRoleId(Map<String,Object> re);

    //绑定微信
    int bindWx(Map<String,String > re);

    //公众号用户登录
    List<User> appUserLogin(User user);


}
