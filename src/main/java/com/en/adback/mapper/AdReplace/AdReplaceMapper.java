package com.en.adback.mapper.AdReplace;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


import java.util.List;
import java.util.Map;

@Mapper
public interface AdReplaceMapper {

    @Insert(
            " UPSERT INTO ad.t_advert_policys_backup " +
            " (id," +
            " advertPolicysId,  " +
            " screenPolicyId,    " +
            " screenId, " +
            " playDates, " +
            " playTimeBegin, " +
            " playTimeEnd, " +
            " devices, " +
            " playAlone, " +
            " systems," +
            " putInKind," +
            " sourceId)  " +
            " SELECT next value for ad.t_advert_policys_backup_seq as id, " +
            " advertPolicysId,  " +
            " screenPolicyId,    " +
            " screenId,   " +
            " playDates,  " +
            " playTimeBegin, " +
            " playTimeEnd,  " +
            " devices, " +
            " playAlone,  " +
            " systems, " +
            " putInKind,  " +
            " sourceId " +
            " FROM ad.t_advert_policys WHERE advertPolicysId = #{advertPolicysId} ")
    public int advertPolicysBackup(@Param("advertPolicysId") String advertPolicysId);


    @Select(
            " SELECT " +
                " advertPolicysId as \"advertPolicysId\",  " +
                " screenPolicyId as  \"screenPolicyId\", " +
                " screenId  as  \"screenId\",  " +
                " playDates  as  \"playDates\",  " +
                " playTimeBegin  as  \"playTimeBegin\", " +
                " playTimeEnd  as  \"playTimeEnd\", " +
                " devices  as  \"devices\", " +
                " playAlone  as  \"playAlone\", " +
                " systems  as  \"systems\", " +
                " putInKind  as  \"putInKind\", " +
                " sourceId  as  \"sourceId\"  " +
            " FROM ad.t_advert_policys " +
            " WHERE advertPolicysId = #{advertPolicysId} ")
    public Map<String,String> getAdvertPolicysById(@Param("advertPolicysId") String advertPolicysId);


    //保存单广告策略主表
    @Insert(
            " UPSERT INTO ad.t_advert_policys " +
                    " (" +
                        " advertPolicysId,  " +
                        " screenPolicyId,    " +
                        " screenId, " +
                        " playDates, " +
                        " playTimeBegin, " +
                        " playTimeEnd, " +
                        " devices, " +
                        " playAlone, " +
                        " systems," +
                        " putInKind," +
                        " sourceId)  " +
                    " VALUES  " +
                    " ( " +
                        " '${advertPolicysId}',  " +
                        " '${screenPolicyId}',    " +
                        " '${screenId}',   " +
                        " '${playDates}',  " +
                        " '${playTimeBegin}', " +
                        " '${playTimeEnd}',  " +
                        " '${devices}', " +
                        " TO_NUMBER('${playAlone}'),  " +
                        " '${systems}', " +
                        " TO_NUMBER('${putInKind}'),  " +
                        " '${sourceId}' " +
                    " ) "
                    )
    public int upsertAdvertPolicys(Map<String,Object> params);



    //保存单广告播放策略子表
    @Insert(
            " UPSERT INTO ad.t_sub_advert_policys " +
            " ( " +
            " id, " +
            " advertPolicysId, " +
            " screenCutId, " +
            " advertId" +
            " ) " +
            "values(" +
            " next value for ad.t_sub_advert_policys_seq, " +
            " #{advertPolicysId} ," +
            " #{screenCutId}," +
            " #{advertId})")
    public int insertSubAdvertPolicys(Map<String,Object> paras);


    //更新 广告状态表
    @Insert("UPSERT into ad.t_advert(advertId,nowState) SELECT advertId,TO_NUMBER('${nowState}') as nowState from ad.t_advert where advertId='${advertId}' ")
    public int updateAdvertState(Map<String,Object> paramMap);

    //插入
    //upsert into ad.t_advert_state_his(id,advertid,nowState,maker,makeTimer)
    // values(next value for ad.t_advert_state_his_seq,'ad1551236127958',TO_NUMBER('8'),'admin',CONVERT_TZ(CURRENT_DATE(), 'UTC', 'Asia/Shanghai'))
    @Insert("<script>" +
            "upsert into ad.t_advert_state_his(id,advertid,nowState,maker,makeTimer)" +
            "values(next value for ad.t_advert_state_his_seq,'${advertId}',TO_NUMBER('${nowState}') ,'${maker}',CONVERT_TZ(CURRENT_DATE(), 'UTC', 'Asia/Shanghai'))" +
            "</script>")
    public int insertAdvertHisState(Map<String, Object> map);

    //更新 广告状态历史记录

    @Insert(
            " <script>" +
            " upsert into ad.t_advert_state_his(id,advertid,nowState,maker,makeTimer) "+
            " select id,advertid, TO_NUMBER('${nowState}') as nowState, '${maker}' as maker,CONVERT_TZ(CURRENT_DATE(), 'UTC', 'Asia/Shanghai') " +
            " from ad.t_advert_state_his where advertid= '${advertId}' "+
            " </script> ")
    public int updateAdvertHisState(Map<String, Object> map);


    // 读取单广告策略子表
    @Select(
            " select advertPolicysId as \"advertPolicysId\",screenCutId as \"screenCutId\",advertId as \"advertId\" from ad.t_sub_advert_policys " +
            " where advertPolicysId=#{advertPolicysId} and screenCutId  in ${screenCutIds}")
    public List<Map<String,Object>> getSubAdvertPolicysByAdvertPolicysId(@Param("advertPolicysId") String advertPolicysId,@Param("screenCutIds") String screenCutIds );


    // 读取单条广告策略子表
    @Select(
            " select a.advertPolicysId as \"advertPolicysId\",a.screenCutId as \"screenCutId\",a.advertId as \"advertId\",b.downLoadFilename as \"downLoadFilename\" " +
            " from ad.t_sub_advert_policys as a left join ad.t_advert_files as b on a.advertId=b.advertId  " +
            "  where advertPolicysId=#{advertPolicysId} and screenCutId  = #{screenCutId}  ")
    public Map<String,Object> getSubAdvertPolicysByAdvertPolicysIdAndScreenCutId(@Param("advertPolicysId") String advertPolicysId,@Param("screenCutId") String screenCutId );


    //根据广告Id 查询文件名
    @Select(
            "SELECT downLoadFileName, " +
            " advertId ,  " +
            " fileName,  " +
            " downLoadUrl   " +
            " FROM ad.t_advert_media " +
            " WHERE advertId=#{advertId}"
    )
    public String getFileNameByAdvertId(@Param("advertId") String advertId);


//    @Select("select cityId as \"cityId\",ip as  \"ip\",url as  \"url\",appName as  \"appName\"  from ad.t_fileHost where 1=1")
    @Select("select distinct a.cityId as \"cityId\",a.ip as  \"ip\",a.url as  \"url\",a.appName as  \"appName\"  " +
            " from ad.t_fileHost as a  inner join idcard.t_device as b on a.cityId = b.planCityId and b.deviceId in ('${devices}')")
    public List<Map<String,String>> getFilehostAddress(@Param("devices") String devices);


    @Insert(
            " UPSERT into ad.t_advert_day_policy_role(" +
            " id," +
            " theDay,  " +
            " deviceId,  " +
            " browseTime, " +
            " mediaFiles," +
            " roleContent" +
            " )" +
            " SELECT " +
            " id," +
            " theDay,  " +
            " deviceId,  " +
            " browseTime, " +
            " mediaFiles," +
            " regexp_replace(roleContent, '${oldFileName}', '${newFileName}') as roleContent " +
            " FROM ad.t_advert_day_policy_role " +
            " WHERE " +
            " theDay >= TO_CHAR(CONVERT_TZ(CURRENT_DATE(), 'UTC', 'Asia/Shanghai'),'yyyy-MM-dd') " +
            " and deviceId in('${replaceDevices}') "
    )
    public int updateDayPolicysRole(Map<String,Object> paramMap);




}
