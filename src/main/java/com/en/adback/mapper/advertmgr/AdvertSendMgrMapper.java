package com.en.adback.mapper.advertmgr;

import com.en.adback.entity.advertmgr.AdvertDayPolicyRole;
import com.en.adback.entity.advertmgr.AdvertPutIn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdvertSendMgrMapper {
    //查询广告信息
    @Select("<script> " +
            "select ad.adid advertId,ad.advertname advertName,ad.adcorpName adCorpName,ad.tradeName tradeName,ad.blankName blankName,media.filename fileName,media.downloadfilename downLoadFileName,media.duration duration,media.filesize fileSize," +
            "policy.pAdPolicyId as advertPolicyId, policy.devices,policy.playAlone playAlone,policy.putInKind putInKind,policyscreen.policyName policyName," +
            "screen.screenCutName screen,screen.position screenPosition,policy.playDates playDays,ad.nowstate nowState," +
            "stathis.maketimer checkDay from\n" +
            " (\n" +
            " select advertid as adid,advertname,nowstate,adcorp.adcorpName,t.tradeName,b.blankName from ad.t_advert \n" +
            " inner join ad.t_advertcorp adcorp on adcorp.adCorpId=ad.t_advert.adCorpId " +
            "left join ad.t_trade t on t.tradeid=ad.t_advert.tradeid \n" +
            "left join ad.t_blank b on b.blankId=ad.t_advert.blankId \n" +
            "where nowstate in (6,7,8,9,10,11) " +
            "<if test='advertId!=\"\" and advertId!=null '>" +
            " <![CDATA[  and ad.t_advert.advertid like '%${advertId}%' ]]> " +
            "</if>" +
            "<if test='advertName!=\"\" and advertName!=null '>" +
            "<![CDATA[  and ad.t_advert.advertname like '%${advertName}%' ]]>" +
            "</if>" +
            "<if test='tradeId!=null and tradeId!=\"\" and tradeId!=\"0\" '>" +
            " and ad.t_advert.tradeid='${tradeId}' " +
            "</if>" +
            "<if test='blankId!=null and blankId!=\"\" and blankId!=\"0\" '>" +
            " and ad.t_advert.blankId='${blankId}' " +
            "</if>" +
            "<if test='adCorpId!=null and adCorpId!=\"\" and adCorpId!=\"0\" '>" +
            "<![CDATA[ and adcorp.adCorpId = '${adCorpId}' ]]>" +
            "</if>" +
            "<if test='nowState!=null and nowState!=\"\" and nowState!=0 '>" +
            "<![CDATA[ and ad.t_advert.nowstate = ${nowState} ]]>" +
            "</if>" +
            ") ad inner join ad.t_advert_media media on media.advertid=ad.adid\n" +
            "left join " +
            "(\n" +
            "select advertId subpadId,advertPolicysId,screenCutId from ad.t_sub_advert_policys \n" +
            ") subpolicy on subpolicy.subpadId=ad.adid\n" +
            "left join " +
            "(\n" +
            "select advertId as hisAdId,max(maketimer) as maketimer from ad.t_advert_state_his where nowstate =3 group by advertid\n" +
            ")stathis on stathis.hisAdId=ad.adid\n" +
            "left join " +
            "(\n" +
            "select advertPolicysId as pAdPolicyId,screenPolicyId,devices,playAlone,putInKind,playdates from ad.t_advert_policys \n" +
            ")policy on subpolicy.advertPolicysId =policy.pAdPolicyId\n" +
            "left join " +
            "(\n" +
            "select screenCutId as sCutId,screenCutName ,position from ad.t_screen_cut \n" +
            ")screen on screen.sCutId = subpolicy.screenCutId\n" +
            "left join " +
            "(\n" +
            "select screenpolicyId as scPolicyId,policyName from ad.t_play_policy_screen \n" +
            ")policyscreen on policyscreen.scPolicyId =policy.screenPolicyId\n" +
            "where 1=1 " +
            "<if test='checkTime!=null and checkTime!=\"\" and endCheckTime!=null and endCheckTime!=\"\" '>" +
            "<![CDATA[ and maketimer>=to_date('${checkTime} 08','yyyy-MM-dd hh') and maketimer <=to_date('${endCheckTime} 08','yyyy-MM-dd hh') ]]>" +
            "</if>" +
            " order by ad.nowstate asc,stathis.maketimer desc limit ${pageSize} offset ${pageBegin} " +
            " </script>")
    public List<AdvertPutIn> getAdvertSendList(Map<String, Object> map);

    //查询广告信息总数
    @Select("<script> " +
            "select count(*) total from\n" +
            " (\n" +
            " select advertid as adid,advertname,nowstate from ad.t_advert \n" +
            " inner join ad.t_advertcorp adcorp on adcorp.adCorpId=ad.t_advert.adCorpId " +
            "where nowstate in (6,7,8,9,10,11) " +
            "<if test='advertId!=\"\" and advertId!=null '>" +
            " <![CDATA[  and ad.t_advert.advertid like '%${advertId}%' ]]> " +
            "</if>" +
            "<if test='advertName!=\"\" and advertName!=null '>" +
            "<![CDATA[  and ad.t_advert.advertname like '%${advertName}%' ]]>" +
            "</if>" +
            "<if test='tradeId!=null and tradeId!=\"\" and tradeId!=\"0\" '>" +
            " and ad.t_advert.tradeid='${tradeId}' " +
            "</if>" +
            "<if test='blankId!=null and blankId!=\"\" and blankId!=\"0\" '>" +
            " and ad.t_advert.blankId='${blankId}' " +
            "</if>" +
            "<if test='adCorpId!=null and adCorpId!=\"\" and adCorpId!=\"0\" '>" +
            "<![CDATA[ and adcorp.adCorpId = '${adCorpId}' ]]>" +
            "</if>" +
            "<if test='nowState!=null and nowState!=\"\" and nowState!=\"0\" '>" +
            "<![CDATA[ and ad.t_advert.nowState = ${nowState} ]]>" +
            "</if>" +
            ") ad inner join ad.t_advert_media media on media.advertid=ad.adid\n" +
            "left join " +
            "(\n" +
            "select advertId subpadId,advertPolicysId,screenCutId from ad.t_sub_advert_policys \n" +
            ") subpolicy on subpolicy.subpadId=ad.adid\n" +
            "left join " +
            "(\n" +
            "select advertId as hisAdId,max(maketimer) as maketimer from ad.t_advert_state_his where nowstate =3 group by advertid\n" +
            ")stathis on stathis.hisAdId=ad.adid\n" +
            "left join " +
            "(\n" +
            "select advertPolicysId as pAdPolicyId,screenPolicyId,devices,playAlone,putInKind,playdates from ad.t_advert_policys \n" +
            ")policy on subpolicy.advertPolicysId =policy.pAdPolicyId\n" +
            "left join " +
            "(\n" +
            "select screenCutId as sCutId,screenCutName ,position from ad.t_screen_cut \n" +
            ")screen on screen.sCutId = subpolicy.screenCutId\n" +
            "left join " +
            "(\n" +
            "select screenpolicyId as scPolicyId,policyName from ad.t_play_policy_screen \n" +
            ")policyscreen on policyscreen.scPolicyId =policy.screenPolicyId\n" +
            "where 1=1 " +
            "<if test='checkTime!=null and checkTime!=\"\" '>" +
            "<![CDATA[ and maketimer>=to_date('${checkTime} 08','yyyy-MM-dd hh') and maketimer <=to_date('${endCheckTime} 08','yyyy-MM-dd hh') ]]>" +
            "</if>" +
            " </script>")
    public List<Map<String,Object>> getAdvertSendListTotal(Map<String, Object> map);

    //查询可供下载的播放策略
    @Select("<script>" +
            " select id, rolecontent from ad.t_advert_day_policy_role where theday ='${theDay}' and deviceid ='${deviceId}' " +
            "</script>")
    public List<AdvertDayPolicyRole> getAdvertDayPolicyRole(Map<String, Object> map);


    // 按时间段，设备查询播放策略, 投放点位查询
    @Select("<script>" +
            " select id,deviceId,theDay, rolecontent from ad.t_advert_day_policy_role where " +
            " <![CDATA[ theday >='${beginDate}' and theday<='${endDate}' and deviceid  in (${deviceIds}) ]]>" +
            "</script>")
    public List<AdvertDayPolicyRole> getDeviceDaysPolicyRole(Map<String, Object> map);





    //标记设备已读播放策略
    @Update(" upsert into ad.t_advert_day_policy_role(id,browsetime)  values(" +
            " ${id}, CONVERT_TZ(CURRENT_DATE(), 'UTC', 'Asia/Shanghai') )")
    public  int updateBrowseTimeToAdvertDayPolicyRole(Map<String,Object> paras);


    // 查询播放策略（播放日志报表用）
    @Select("<script>" +
            " select rolecontent from ad.t_advert_day_policy_role where " +
            "<![CDATA[ theday >='${beginDate}' and theday <='${endDate}' ]]>" +
            " and deviceid ='${deviceId}' " +
            "</script>")
    public List<AdvertDayPolicyRole> getDevicePolicyRoles(Map<String, Object> map);

    //根据广告id查询 广告策略、设备、关联屏幕详情
    @Select("select ad.advertid advertId,ad.advertName advertName,fileName,downLoadFileName,am.duration duration,sap.advertpolicysid advertPolicyId,ap.playAlone playAlone," +
            "ap.putInKind putInKind,ap.playDates playDays,ap.devices devices,pc.policyName policyName,sc.screenCutName screen,sc.position screenPosition," +
            "case when ad.advertid='${advertId}' then 1 else 2 end as screenType from ad.t_advert ad\n" +
            "inner join ad.t_advert_media am on am.advertId=ad.advertId\n" +
            "inner join  ad.t_sub_advert_policys sap on sap.advertid=ad.advertid \n" +
            "inner join \n" +
            "(\n" +
            "select ad.advertid,ad.advertName,sap.advertpolicysid from ad.t_advert ad " +
            "inner join ad.t_sub_advert_policys sap on sap.advertid=ad.advertid where ad.advertid='${advertId}'\n" +
            ") p on p.advertpolicysid=sap.advertpolicysid\n" +
            "inner join ad.t_advert_policys ap on ap.advertPolicysId=sap.advertPolicysId\n" +
            "inner join ad.t_play_policy_screen pc on pc.screenPolicyId=ap.screenPolicyId\n" +
            "inner join ad.t_screen_cut sc on sc.screenCutId=sap.screenCutId")
    public List<AdvertPutIn> getAdvertSendDetail(Map<String, Object> map);
}
