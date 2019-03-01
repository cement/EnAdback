package com.en.adback.service.advertmgr;

import com.en.adback.entity.Logs;
import com.en.adback.entity.advertmgr.AdvertDayPolicyRole;
import com.en.adback.entity.advertmgr.AdvertPutIn;
import com.en.adback.entity.calpolicy.DownDayPolicy;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface IAdvertSendMgrService {
    //查询广告信息
    public List<AdvertPutIn> getAdvertSendList(Map<String, Object> map);

    //查询广告信息总数
    public List<Map<String,Object>> getAdvertSendListTotal(Map<String, Object> map);

    public DownDayPolicy dayPolicyRole(Map<String, Object> map);

    // 按时间段，设备查询播放策略, 投放点位查询
    public List<AdvertDayPolicyRole> getDeviceDaysPolicyRole(Map<String, Object> map);

    // 查询播放策略（播放日志报表用）
    public List<AdvertDayPolicyRole> getDevicePolicyRoles(Map<String, Object> map);

    //查询广告对应的策略、设备、关联屏幕详情
    public List<AdvertPutIn> getAdvertSendDetail(Map<String, Object> map);

    //查询广告excel导出信息
    List<AdvertPutIn> getExcelAdvertSendMgr(Map<String, Object> map);

    //查看修改历史
    List<Logs> getChangeHistory(Map<String,Object> re);
}
