package com.en.adback.mapper.analisys;

import com.en.adback.entity.advertmgr.TableAdvertPolicys;
import com.en.adback.entity.advertmgr.TableSubAdvertPolicys;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

//播放点位查询
@Mapper
public interface PutinPointQueryMapper {

    // 获取所有覆盖该时间段的策略

    @Select("<script>" +
            "select advertPolicysId ,screenPolicyId,screenId,playDates,playTimeBegin," +
            "   playTimeEnd,devices,playAlone,putInKind from ad.t_advert_policys where" +
            " <![CDATA[ " +
            "           substr(playdates,length(playdates)-9,10)>= '${beginDate}'     " +
            "           and substr(playdates,0,10)<= '${beginDate}' " +
            "         or  substr(playdates,length(playdates)-9,10)>= '${endDate}'     " +
            "           and substr(playdates,0,10)<= '${endDate}' " +
            "         or  substr(playdates,length(playdates)-9,10)>= '${beginDate}'     " +
            "           and  substr(playdates,length(playdates)-9,10)<= '${endDate}' " +
            "         or  substr(playdates,0,10)>= '${beginDate}'     " +
            "           and  substr(playdates,0,10)<= '${endDate}' " +
            " ]]> " +
            "</script>")
    public List<TableAdvertPolicys> allAdvertPolicysList(Map<String,Object> paras);

    // 读取所有广告策略字表
    @Select("select advertPolicysId ,screenCutId,advertId from ad.t_sub_advert_policys where advertPolicysId in (${advertPolicysIds}) ")
    public List<TableSubAdvertPolicys> subAdvertPolicysList(Map<String,Object> paras);



}
