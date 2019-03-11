package com.en.adback.service.dic;

import com.en.adback.entity.devicemgr.Device;
import com.en.adback.entity.dic.*;
import com.en.adback.entity.sys.GroupRole;
import com.en.adback.entity.sys.Role;
import com.en.adback.entity.sys.User;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/3.
 */
public interface DicService {

    List<Enterprise> getEnterpriseList(Map<String,Object> map);

    List<AdvertCorp> getAdvertCorpList();

    List<AdvertState> getAdvertStateList();

    List<Blank> getBlankList();

    List<DeviceStatus> getDeviceStatusList();

    List<Trade> getTradeList();

    public List<Area> getOrgList();

    List<Area> getAreaList();

    List<User> getUserList();

    List<GroupRole> getGroupRoleList();

    List<Role> getRoleList(Map<String,Object> re);

    //查询所有已发布策略的设备编号
    List<Device> deviceIdList();

}
