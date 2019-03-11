package com.en.adback.serviceimp.sys;

import com.en.adback.entity.sys.GroupRole;
import com.en.adback.mapper.sys.GroupRoleMapper;
import com.en.adback.service.sys.IGroupRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/17.
 */
@Service
public class GroupRoleServiceImp implements IGroupRoleService{

@Autowired
private GroupRoleMapper mapper;

    @Override
    public List<GroupRole> getGroupRoleList(Map<String, Object> re) {
        return mapper.getGroupRoleList(re);
    }

    @Override
    public int getGroupRoleListTotal() {
        return mapper.getGroupRoleListTotal();
    }

    @Override
    public int upsertGroupRole(GroupRole groupRole) {
        return mapper.upsertGroupRole(groupRole);
    }

    @Override
    public List<GroupRole> getGroupRoleById(Map<String, String> re) {
        return mapper.getGroupRoleById(re);
    }

    @Override
    public int deleteGroupRole(Map<String, String> re) {
        return mapper.deleteGroupRole(re);
    }

    @Override
    public List<GroupRole> checkAppGroupRole() {
        return mapper.checkAppGroupRole();
    }
}
