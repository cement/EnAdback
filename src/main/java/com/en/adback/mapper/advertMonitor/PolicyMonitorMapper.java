package com.en.adback.mapper.advertMonitor;

import com.en.adback.entity.advertmgr.AdvertDayPolicyRole;
import com.en.adback.service.advertMonitor.PolicyMonitorService;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Mapper
public interface PolicyMonitorMapper {

    @Select("<script>" +
            "<![CDATA[select theday,deviceId,roleContent,deviceRecievedTime,browseTime,mediaFiles from ad.t_advert_day_policy_role " +
            "where theday='${putinBeginTime}']]>" +
            "</script>")
    public List<AdvertDayPolicyRole> getPolicyMonList(Map<String, Object> map);
}
