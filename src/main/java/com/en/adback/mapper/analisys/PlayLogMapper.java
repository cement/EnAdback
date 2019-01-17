package com.en.adback.mapper.analisys;

import com.en.adback.entity.PlayLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface PlayLogMapper {

    @Select("<script>" +
            "select to_char(rec.playTime,'yyyy-MM-dd HH-mm-ss') as playTime, media.duration as duration, screen.policyName as policyName,cut.screenCutName as screen,cut.position as screenPosition, advert.advertId as advertId,advert.advertName as advertName  " +
            "from idcard.t_advert_play_rec rec " +
            "inner join ad.t_advert_media media on rec.fileName = media.downLoadFileName " +
            "inner join ad.t_advert advert on advert.advertId = media.advertId " +
            "inner join ad.t_sub_advert_policys subPolicys on subPolicys.advertId = advert.advertId " +
            "inner join ad.t_advert_policys policys on policys.advertPolicysId = subPolicys.advertPolicysId " +
            "inner join ad.t_play_policy_screen screen on screen.screenPolicyId = policys.screenPolicyId " +
            "inner join ad.t_screen_cut cut on cut.screenCutId = subPolicys.screenCutId " +
            "where 1=1 " +
            "<if test='beginDate!=null and beginDate!=\"\" and endDate!=\"\" and endDate!=null '>" +
            "<![CDATA[  and playTime between to_date('${beginDate} 08','yyyy-MM-dd hh') and to_date('${endDate} 08','yyyy-MM-dd hh') ]]>" +
            "</if>" +
            " order by rec.playTime asc" +
            "</script>")
    public List<PlayLog> getPlayLlogList(Map<String,Object> map);
}
