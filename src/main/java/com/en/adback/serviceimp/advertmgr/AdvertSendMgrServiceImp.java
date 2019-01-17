package com.en.adback.serviceimp.advertmgr;

import com.alibaba.fastjson.JSON;
import com.en.adback.entity.advertmgr.AdvertDayPolicyRole;
import com.en.adback.entity.advertmgr.AdvertPutIn;
import com.en.adback.entity.calpolicy.DownDayPolicy;
import com.en.adback.mapper.advertmgr.AdvertSendMgrMapper;
import com.en.adback.service.advertmgr.IAdvertSendMgrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdvertSendMgrServiceImp implements IAdvertSendMgrService {

    @Autowired private AdvertSendMgrMapper mapper;

    @Override
    public List<AdvertPutIn> getAdvertSendList(Map<String, Object> map) {
        return mapper.getAdvertSendList(map);
    }

    @Override
    public List<Map<String,Object>> getAdvertSendListTotal(Map<String, Object> map) {
        return mapper.getAdvertSendListTotal(map);
    }

    @Override
    public DownDayPolicy dayPolicyRole(Map<String, Object> map) {
        List<AdvertDayPolicyRole> list=mapper.getAdvertDayPolicyRole(map);
        DownDayPolicy downDayPolicy=null;
        if(list.size()>0){
        String strRole=list.get(0).getRoleContent();
             downDayPolicy= JSON.parseObject(strRole,DownDayPolicy.class);
            //标记设备已读播放策略
             mapper.updateBrowseTimeToAdvertDayPolicyRole(new HashMap<String,Object>(){{
                 put("id",String.valueOf( list.get(0).getId()));
             }});
        }
        return downDayPolicy;
    }

    @Override
    public List<AdvertDayPolicyRole> getDeviceDaysPolicyRole(Map<String, Object> map) {
        return mapper.getDeviceDaysPolicyRole(map);
    }

    // 查询播放策略（播放日志报表用）
    @Override
    public List<AdvertDayPolicyRole> getDevicePolicyRoles(Map<String, Object> map) {
        List<AdvertDayPolicyRole> list=mapper.getDevicePolicyRoles(map);

        return null;
    }


    @Override
    public List<AdvertPutIn> getAdvertSendDetail(Map<String, Object> map) {
        return mapper.getAdvertSendDetail(map);
    }

}
