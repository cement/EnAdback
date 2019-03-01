package com.en.adback.mapper;

import com.en.adback.entity.Logs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface playCertifyExcelMapper {

    @Select("<script>" +
            "select year(a.playTime) as nian ,month(a.playTime) as yue ,to_char(a.playTime, 'yyyy-MM-dd HH-mm-ss') as rq ,b.advertid as advertid,h.policyName as policyName,e.screenName as screenName,f.screenCutName as screenCutName ,a.deviceId as deviceId ,count(1) as sl  " +
            "from idcard.t_advert_play_rec a  " +
            "inner join ad.t_advert_media b on a.fileName = b.downLoadFileName  " +
            "inner join ad.t_sub_advert_policys c on b.advertId = c.advertId  " +
            "inner join ad.t_screen_form d on d.screenCutId = c.screenCutId  " +
            "inner join ad.t_screen e on e.screenId = d.screenId  " +
            "inner join ad.t_screen_cut f on f.screenCutId = d.screenCutId  " +
            "inner join ad.t_advert_policys j on j.advertPolicysId = c.advertPolicysId   " +
            "inner join ad.t_play_policy_screen h on h.screenpolicyId = j.screenpolicyId   " +
            "where 1=1 " +
            "<if test='beginDate!=null and beginDate!=\"\" and endDate!=null and endDate!=\"\" '>" +
            "<![CDATA[ and a.playTime between to_date('${beginDate}', 'yyyy-MM-dd') and to_date('${endDate}', 'yyyy-MM-dd') ]]>" +
            "</if>"+
            "<if test='advertId!=null and advertId!=\"\" '>"+
            "and b.advertid = '${advertId}' "+
            "</if>"+
            "group by rq,e.screenName,f.screenCutName,h.policyName,b.advertid,nian,yue,a.deviceId order by rq  "+
            "</script>")
    public List<Map<String, Object>> getPlayCertifyExcel(Map<String, Object> map);

}
