package com.en.adback.mapper.analisys;

import com.en.adback.entity.devicemgr.DeviceGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface PutinPointCountMapper {
    @Select("<script>" +
            "select " +
            "sum(case month(a.playTime) when 1 then 1 else 0 end) as InJanuary , " +
            "sum(case month(a.playTime) when 2 then 1 else 0 end) as InFebruary, " +
            "sum(case month(a.playTime) when 3 then 1 else 0 end) as InMarch, " +
            "sum(case month(a.playTime) when 4 then 1 else 0 end) as April, " +
            "sum(case month(a.playTime) when 5 then 1 else 0 end) as InMay, "+
            "sum(case month(a.playTime) when 6 then 1 else 0 end) as InJune, "+
            "sum(case month(a.playTime) when 7 then 1 else 0 end) as July, "+
            "sum(case month(a.playTime) when 8 then 1 else 0 end) as August, "+
            "sum(case month(a.playTime) when 9 then 1 else 0 end) as September, "+
            "sum(case month(a.playTime) when 10 then 1 else 0 end) as October, "+
            "sum(case month(a.playTime) when 11 then 1 else 0 end) as November, "+
            "sum(case month(a.playTime) when 12 then 1 else 0 end) as December, "+
            "e.policyName as policyName "+
            "from idcard.t_advert_play_rec a inner join ad.t_advert_media b on a.fileName = b.downLoadFileName "+
            "inner join ad.t_sub_advert_policys c on b.advertId = c.advertId  "+
            "inner join ad.t_advert_policys d on c.advertPolicysId = d.advertPolicysId "+
            "inner join ad.t_play_policy_screen e on e.screenpolicyId = d.screenpolicyId "+
            "where year(a.playTime) = ${year} " +
            "group by e.policyName " +
            "</script>")
    public List<Map<String,Object>> PutinPointCount(Map<String, Object> map);

    @Select("<script>" +
            "select policyName from ad.t_play_policy_screen " +
            "</script>")
    public List<Map<String,Object>> getTPlayPolicyScreen();

    @Select("<script>" +
            "select b.screenName as detail,c.screenCutName as screen " +
            "from ad.t_screen_form a " +
            "inner join ad.t_screen b on a.screenId = b.screenId " +
            "inner join ad.t_screen_cut c on c.screenCutId  = a.screenCutId" +
            "</script>")
    public List<Map<String,Object>> TScreenCount();


    // 获取 各点位 统计 排名
    @Select("<script>" +
            "select h.screenName as screenName  ,g.screenCutName as screenCutName ,j.policyName as policyName   ,count(*) as strategyCount " +
            "from idcard.t_advert_play_rec a   " +
            "inner join ad.t_advert_media b on a.fileName = b.downLoadFileName " +
            "inner join ad.t_sub_advert_policys c on b.advertId = c.advertId  " +
            "inner join ad.t_screen_form f on f.screenCutId  = c.screenCutId  "+
            "inner join ad.t_screen_cut g on g.screenCutId = f.screenCutId  "+
            "inner join ad.t_screen h on h.screenId = f.screenId  "+
            "inner join ad.t_advert_policys i on i.advertPolicysId = c.advertPolicysId "+
            "inner join ad.t_play_policy_screen j on j.screenPolicyId = i.screenPolicyId "+
            "where year(a.playTime) =${year} group by h.screenName ,g.screenCutName,j.policyName order by strategyCount desc  "+
            "</script>")
    public List<Map<String,Object>> getPointStatistics(Map<String, Object> map);




    // 获取 广告投放总数
    @Select("<script>" +
            "select e.policyName as policyName, count(*) as strategyCount  " +
            "from ad.t_advert a  " +
            "inner join ad.t_sub_advert_policys b on b.advertId = a.advertId " +
            "inner join ad.t_advert_policys d on d.advertPolicysId = b.advertPolicysId  " +
            "inner join ad.t_play_policy_screen e on e.screenpolicyId = d.screenpolicyId  "+
            "where year(a.uploadTime) = ${year} group by e.policyName order by strategyCount desc "+
            "</script>")
    public List<Map<String,Object>> getOrientationOnTheArray(Map<String, Object> map);



    @Select("<script>" +
            " select e.screenName as screenName,f.screenCutName as screenCutName ,h.policyName as policyName, count(*) as strategyCount   " +
            "from ad.t_advert a  " +
            "inner join ad.t_sub_advert_policys b on b.advertId = a.advertId " +
            "inner join ad.t_screen_form d on d.screenCutId = b.screenCutId  " +
            "inner join ad.t_screen e on e.screenId = d.screenId  "+
            "inner join ad.t_screen_cut f on f.screenCutId = d.screenCutId  "+
            "inner join ad.t_advert_policys g on g.advertPolicysId = b.advertPolicysId  "+
            "inner join ad.t_play_policy_screen h on h.screenPolicyId  = g.screenPolicyId  "+
            "where year(a.uploadTime) = ${year}  group by e.screenName,f.screenCutName,h.policyName order by strategyCount desc  " +
            "</script>")
    public List<Map<String,Object>> getTheTailArray(Map<String, Object> map);


    // 获取 播放频次 统计
    @Select("<script>" +
            "select " +
            "sum(case month(a.playTime) when 1 then 1 else 0 end) as InJanuary , " +
            "sum(case month(a.playTime) when 2 then 1 else 0 end) as InFebruary, " +
            "sum(case month(a.playTime) when 3 then 1 else 0 end) as InMarch, " +
            "sum(case month(a.playTime) when 4 then 1 else 0 end) as April, " +
            "sum(case month(a.playTime) when 5 then 1 else 0 end) as InMay, "+
            "sum(case month(a.playTime) when 6 then 1 else 0 end) as InJune, "+
            "sum(case month(a.playTime) when 7 then 1 else 0 end) as July, "+
            "sum(case month(a.playTime) when 8 then 1 else 0 end) as August, "+
            "sum(case month(a.playTime) when 9 then 1 else 0 end) as September, "+
            "sum(case month(a.playTime) when 10 then 1 else 0 end) as October, "+
            "sum(case month(a.playTime) when 11 then 1 else 0 end) as November, "+
            "sum(case month(a.playTime) when 12 then 1 else 0 end) as December, "+
            "e.screenName as screenName, "+
            "f.screenCutName as screenCutName, "+
            "h.policyName as policyName " +
            "from idcard.t_advert_play_rec a inner join ad.t_advert_media b on a.fileName = b.downLoadFileName "+
            "inner join ad.t_sub_advert_policys c on b.advertId = c.advertId   "+
            "inner join ad.t_screen_form d on d.screenCutId = c.screenCutId  "+
            "inner join ad.t_screen e on e.screenId = d.screenId "+
            "inner join ad.t_screen_cut f on f.screenCutId = d.screenCutId "+
            "inner join ad.t_advert_policys j on j.advertPolicysId = c.advertPolicysId "+
            "inner join ad.t_play_policy_screen h on h.screenpolicyId = j.screenpolicyId " +
            "where year(a.playTime) = ${year} " +
            "group by e.screenName,f.screenCutName ,h.policyName " +
            "</script>")
    public List<Map<String,Object>> getBroadcastFrequency(Map<String, Object> map);








}
