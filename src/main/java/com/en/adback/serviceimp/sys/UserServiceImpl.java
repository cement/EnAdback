package com.en.adback.serviceimp.sys;

import com.en.adback.entity.sys.GroupRole;
import com.en.adback.entity.sys.User;
import com.en.adback.mapper.sys.UserMapper;
import com.en.adback.service.sys.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 用户管理
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired private UserMapper mapper;

    @Override
    public List<User> validateLogin(User user) {
        return mapper.validateLogin(user);
    }

    @Override
    public List<User> findbyUserId(String userId) {
        return null;
    }

    @Override
    public List<User> findAllUser(Map<String,Object> re) {
        return mapper.findAllUser(re);
    }

    @Override
    public int getGroupRoleTotal(Map<String, String> re) {
        return mapper.getGroupRoleTotal(re);
    }

    @Override
    public List<User> getUserByGroupRoleId(Map<String, Object> re) {
        return mapper.getUserByGroupRoleId(re);
    }

    @Override
    public int getUserByGroupRoleIdTotal(Map<String, String > re) {
        return mapper.getUserByGroupRoleIdTotal(re);
    }

    @Override
    public int insertUser(User user) {
        return mapper.insertUser(user);
    }

    @Override
    public List<User> getByUserId(Map<String, String > re) {
        return mapper.getByUserId(re);
    }

    @Override
    public int  updateUser(User user) {
        return mapper.updateUser(user);
    }

    @Override
    public int deleteUser(Map<String, String > re) {
        return mapper.deleteUser(re);
    }

    @Override
    public List<User> getUserList(Map<String, Object> re) {
        return mapper.getUserList(re);
    }

    @Override
    public int changePassWord(Map<String, Object> re) {
        return mapper.changePassWord(re);
    }

    @Override
    public List<GroupRole> getGroupRoleForInsert() {
        return mapper.getGroupRoleForInsert();
    }

    @Override
    public List<User> getUserExcelByAdmin(Map<String, Object> re) {
        return mapper.getUserExcelByAdmin(re);
    }

    @Override
    public List<User> getUserExcelByGroupRoleId(Map<String, Object> re) {
        return mapper.getUserExcelByGroupRoleId(re);
    }

    @Override
    public int bindWx(Map<String, String> re) {
        return mapper.bindWx(re);
    }

    @Override
    public List<User> appUserLogin(User user) {
        return mapper.appUserLogin(user);
    }


}
