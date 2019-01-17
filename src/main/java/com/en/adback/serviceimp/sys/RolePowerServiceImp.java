package com.en.adback.serviceimp.sys;

import com.en.adback.entity.sys.MenuId;
import com.en.adback.entity.sys.Role;
import com.en.adback.entity.sys.RolePower;
import com.en.adback.mapper.sys.RolePowerMapper;
import com.en.adback.service.sys.IRolePowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/21.
 */
@Service
public class RolePowerServiceImp implements IRolePowerService{

    @Autowired
    private RolePowerMapper mapper;

    @Override
    public List<Role> getRoleList(Map<String, Object> re) {
        return mapper.getRoleList(re);
    }

    @Override
    public String getMenus(Map<String, Object> re) {
        return mapper.getMenus(re);
    }

    @Override
    public List<MenuId> getMenuIdList(Map<String, Object> re) {
        return mapper.getMenuIdList(re);
    }

    @Override
    public List<RolePower> getRolePowerList(Map<String, Object> re) {
        return mapper.getRolePowerList(re);
    }

    @Override
    public List<RolePower> getMaxMenuRolePower(Map<String, Object> re) {
        return mapper.getMaxMenuRolePower(re);
    }

    @Override
    public List<RolePower> getAllRolePowerList(Map<String, Object> re) {
        return mapper.getAllRolePowerList(re);
    }

    @Override
    public List<RolePower> getMaxMenu(Map<String, Object> re) {
        return mapper.getMaxMenu(re);
    }

    @Override
    public List<Role> getByRoleId(Map<String, String> re) {
        return mapper.getByRoleId(re);
    }

    @Override
    public int upsertRole(Role role) {
        return mapper.upsertRole(role);
    }

    @Override
    public int deleteRole(Map<String, String> re) {
        return mapper.deleteRole(re);
    }

    @Override
    public int deleteRolePower(Map<String, String > re) {
        return mapper.deleteRolePower(re);
    }

    @Override
    public int insertRolePower(RolePower rolePower) {
        return mapper.insertRolePower(rolePower);
    }

    @Override
    public List<Role> getAllRoleByAdminList() {
        return mapper.getAllRoleByAdminList();
    }

    @Override
    public String getGroupRoleName(Map<String, String> re) {
        return mapper.getGroupRoleName(re);
    }
}
