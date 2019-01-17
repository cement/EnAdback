package com.en.adback.entity.sys;

import com.en.adback.entity.UserAction;
import lombok.Data;


/**
 * 系统用户类
 */

@Data
public class User extends UserAction {

    private String userId ; //账号
    private String userName; // 用户名称
    private String tel ; // 联系电话
    private String groupRoleId; // 所属分组
    private String  roleId; // 拥有角色
    private String passWord ; // 登录密码
    private boolean isManager; // 是否为管理员
    private String groupRoleName;//所属分组名称
    private String roleName;//角色名称
    private boolean ifGroupManager;//是否组管理员

    public boolean isIfGroupManager() {
        return ifGroupManager;
    }

    public void setIfGroupManager(boolean ifGroupManager) {
        this.ifGroupManager = ifGroupManager;
    }

    public User() {
    }

}
