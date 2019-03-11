package com.en.adback.serviceimp.dic;

import com.en.adback.entity.devicemgr.Device;
import com.en.adback.entity.dic.*;
import com.en.adback.entity.sys.GroupRole;
import com.en.adback.entity.sys.Role;
import com.en.adback.entity.sys.User;
import com.en.adback.mapper.dic.DicMapper;
import com.en.adback.service.dic.DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/3.
 */
@Service
public class DicServiceImp implements DicService {

    @Autowired
    private DicMapper mapper;

    @Override
    public List<Enterprise> getEnterpriseList(Map<String,Object> map) {
        return mapper.getEnterpriseList(map);
    }

    @Override
    public List<AdvertCorp> getAdvertCorpList() {
        return mapper.getAdvertCorpList();
    }

    @Override
    public List<AdvertState> getAdvertStateList() {
        return mapper.getAdvertStateList();
    }

    @Override
    public List<Blank> getBlankList() {
        return mapper.getBlankList();
    }

    @Override
    public List<DeviceStatus> getDeviceStatusList() {
        return mapper.getDeviceStatusList();
    }


    @Override
    public List<Trade> getTradeList() {
        return mapper.getTradeList();
    }

    @Override
    public List<Area> getOrgList() {
        return mapper.getOrgList();
    }

    @Override
    public List<Area> getAreaList() {
        return mapper.getAreaList();
    }

    @Override
    public List<User> getUserList() {
        return mapper.getUserList();
    }

    @Override
    public List<GroupRole> getGroupRoleList() {
        return mapper.getGroupRoleList();
    }

    @Override
    public List<Role> getRoleList(Map<String, Object> re) {
        return mapper.getRoleList(re);
    }

    @Override
    public List<Device> deviceIdList() {
        return mapper.deviceIdList();
    }
}
