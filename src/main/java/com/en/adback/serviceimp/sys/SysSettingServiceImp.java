package com.en.adback.serviceimp.sys;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.en.adback.entity.sys.SysSetting;
import com.en.adback.mapper.sys.SysSettingMapper;
import com.en.adback.service.sys.SysSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysSettingServiceImp implements SysSettingService {

    @Autowired private SysSettingMapper mapper;

    @Override
    public SysSetting getSettingParams() {
        List<Map<String,Object>> list=mapper.getSettingParams();
        String settings=list.get(0).get("OPSTR").toString();
        SysSetting sysSetting= JSON.parseObject(settings, SysSetting.class);
        return sysSetting;
    }

    @Override
    public int settingParams(SysSetting sysSetting) {
        Map<String,Object> map=new HashMap<String, Object>();
        String settings=JSON.toJSONString(sysSetting);
        map.put("settings",settings);
        int i=mapper.settingParams(map);
        return i;
    }
}
