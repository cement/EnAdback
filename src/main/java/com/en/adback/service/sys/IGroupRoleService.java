package com.en.adback.service.sys;

import com.en.adback.entity.sys.GroupRole;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/17.
 */
public interface IGroupRoleService {

    List<GroupRole> getGroupRoleList(Map<String,Object> re);

    int getGroupRoleListTotal();

    int upsertGroupRole(GroupRole groupRole);

    List<GroupRole> getGroupRoleById(Map<String,String > re);

    int deleteGroupRole(Map<String,String> re);

}
