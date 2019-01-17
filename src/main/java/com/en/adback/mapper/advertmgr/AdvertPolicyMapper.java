package com.en.adback.mapper.advertmgr;

import com.en.adback.entity.advertmgr.AdvertPolicys;
import com.en.adback.entity.advertmgr.TableAdvertPolicys;
import com.en.adback.entity.advertmgr.TableSubAdvertPolicys;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

// 广告策略
@Mapper
public interface AdvertPolicyMapper {

    // 读取单广告策略表
    @Select("select advertPolicysId ,screenPolicyId,screenId,playDates,playTimeBegin," +
            " playTimeEnd,devices,playAlone,putInKind from ad.t_advert_policys where advertPolicysId='${advertPolicysId}'")
    public List<TableAdvertPolicys> advertPolicysList(Map<String,Object> paras);
    // 读取单广告策略字表
    @Select("select advertPolicysId ,screenCutId,advertId from ad.t_sub_advert_policys where advertPolicysId='${advertPolicysId}' ")
    public List<TableSubAdvertPolicys> subAdvertPolicysList(Map<String,Object> paras);

   //保存单广告策略表
    @Insert("upsert into ad.t_advert_policys(advertPolicysId ,screenPolicyId,screenId,playDates,playTimeBegin," +
            "  playTimeEnd,devices,playAlone,putInKind) values(#{advertPolicysId} ,#{screenPolicyId},#{screenId},#{playDates},#{playTimeBegin}," +
            " #{playTimeEnd},#{devices},#{playAlone},#{putInKind})")
    public int insertAdvertPolicys(TableAdvertPolicys  paras);
    //保存单广告播放策略子表
    @Insert("upsert into ad.t_sub_advert_policys( id, advertPolicysId ,screenCutId,advertId) " +
            "values(next value for ad.t_sub_advert_policys_seq, #{advertPolicysId} ,#{screenCutId},#{advertId})")
    public int insertSubAdvertPolicys(TableSubAdvertPolicys paras);

    // 删除字表
    @Delete("delete from ad.t_sub_advert_policys where advertPolicysId='${advertPolicysId}'")
    public int deleteSubAdvertPolicys(Map<String,Object> paras);





    //查询该时间段非本广告该策略是否有独播广告或插播广告(设备发送到前台比对)
    @Select("<script>" +
           " select 1 from ( " +
            "       select screenCutId,advertpolicysid from ad.t_sub_advert_policys where advertId !='' and screenCutId=''\n" +
            "    ) s " +
            "     inner join ad.t_advert_policys m on s.advertpolicysid=m.advertpolicysid\n" +
            "     where m.playAlone=1 and m.playDates like '%${theDate}%'  " +
            "</script>")
    public List<Map<String,Object>> existPlayAloneAdvert(Map<String,Object> paras);


   // 检查播放冲突（投放独播，有轮播存在，轮播有独播存在）
    @Select("<script> select m.devices as devices,m.playdates as playdates from  ad.t_advert_policys m inner join ad.t_sub_advert_policys s on m.advertpolicysid=s.advertpolicysid " +
            "where s.advertid !='${advertId}' and s.screencutid='${screenCutId}' " +
            "<![CDATA[ and substr(playdates,length(playdates)-9,10)>= '${beginDate}' " +
            " and substr(playdates,0,10)<= '${endDate}' ]]>" +
            " <if test='playalone !=null'> " +
            " and m.playalone=${playalone} " +
            " </if> " +
            "</script>")
    public List<Map<String,Object>> judgeScreenConflict(Map<String,Object> paras);


    // 插播广告冲突检查
    @Select("<script> select m.devices as devices,m.playdates as playdates from  ad.t_advert_policys m inner join ad.t_sub_advert_policys s on m.advertpolicysid=s.advertpolicysid \n" +
            "           where (m.playtimebegin ='${playTimebegin}' or m.playtimeEnd !='00:00') s.advertid !='${advertId}' and s.screencutid='${screenCutId}' " +
            "<![CDATA[ and substr(playdates,length(playdates)-9,10)>= '${beginDate}' " +
            " and substr(playdates,0,10)<= '${endDate}' ]]>" +
            " <if test='playalone !=null'> " +
            " and m.playalone=${playalone} " +
            " </if> " +
            "</script>")
    public List<Map<String,Object>> judgeInsertScreenConflict(Map<String,Object> paras);

}
