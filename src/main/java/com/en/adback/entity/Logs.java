package com.en.adback.entity;

import lombok.Data;

@Data
public class Logs {

    private String userId;
    private String groupRoleId;
    private String groupRoleName;
    private String roleId;
    private String roleName;
    private String ip;
    private String logContent;
    private String actionTime;
    private String userName;

    public Logs() {
    }
}
