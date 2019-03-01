package com.en.adback.mapper.advertmgr;

/**
 * 广告发布
 */

import com.en.adback.entity.advertmgr.AdvertPolicys;
import com.en.adback.entity.advertmgr.AdvertPutIn;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdvertPutinMapper {

    // 广告发布查询
    @Select("<script> " +
            "select ad.adid advertId,ad.advertname advertName,ad.adcorpName adCorpName,ad.tradeName tradeName,ad.blankName blankName,ad.nowstate nowState," +
            "media.filename fileName,media.duration duration,media.filesize fileSize," +
            "policy.devices,policy.pAdPolicyId as advertPolicyId,policy.playAlone playAlone,policy.putInKind putInKind,policy.screenPolicyId screenPolicyId,policy.playDates playDays," +
            "screen.screenCutName screen,screen.position screenPosition," +
            "stathis.maketimer checkDay from\n" +
            " (\n" +
            " select advertid as adid,advertname,nowstate,adcorp.adcorpName,t.tradeName,b.blankName from ad.t_advert \n" +
            " inner join ad.t_advertcorp adcorp on adcorp.adCorpId=ad.t_advert.adCorpId " +
            " left join ad.t_trade t on t.tradeid=ad.t_advert.tradeid\n" +
            " left join ad.t_blank b on b.blankId=ad.t_advert.blankId\n" +
            "where nowstate in (3,5,6) " +
            "<if test='advertId!=\"\" and advertId!=null '>" +
            " <![CDATA[  and ad.t_advert.advertid like '%${advertId}%' ]]> " +
            "</if>" +
            "<if test='advertName!=\"\" and advertName!=null '>" +
            "<![CDATA[  and ad.t_advert.advertname like '%${advertName}%' ]]>" +
            "</if>" +
            "<if test='tradeId!=null and tradeId!=\"\" and tradeId!=\"0\" '>" +
            " and ad.t_advert.tradeid='${tradeId}' " +
            "</if>" +
            "<if test='adCorpName!=null and adCorpName!=\"\" '>" +
            "<![CDATA[ and adcorp.adCorpName like '%${adCorpName}%' ]]>" +
            "</if>" +
            "<if test='blankId!=null and blankId!=\"\" and blankId!=\"0\" '>" +
            " and ad.t_advert.blankid='${blankId}' " +
            "</if>" +
            "<if test='nowState!=null and nowState!=\"\" and nowState!=\"0\"'>" +
            " and ad.t_advert.nowState=${nowState} " +
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
            "where 1=1 " +
            "<if test='checkTimeBegin!=null and checkTimeBegin!=\"\" and checkTimeEnd!=\"\" and checkTimeEnd!=null '>" +
            "<![CDATA[  and maketimer between to_date('${checkTimeBegin} 08','yyyy-MM-dd hh') and to_date('${checkTimeEnd} 08','yyyy-MM-dd hh') ]]>" +
            "</if>" +
            " order by ad.nowstate asc,stathis.maketimer desc limit ${pageSize} offset ${pageBegin} " +
            " </script>")
    public List<AdvertPutIn> advertPutInList(Map<String,Object> paras);

    // 广告发布查询总数
    @Select("<script> " +
            "select count(*) as total from\n" +
            " (\n" +
            " select advertid as adid,advertname,nowstate from ad.t_advert \n" +
            " inner join ad.t_advertcorp adcorp on adcorp.adCorpId=ad.t_advert.adCorpId " +
            "where nowstate in (3,5,6) " +
            "<if test='advertId!=\"\" and advertId!=null '>" +
            " <![CDATA[  and ad.t_advert.advertid like '%${advertId}%' ]]> " +
            "</if>" +
            "<if test='advertName!=\"\" and advertName!=null '>" +
            "<![CDATA[  and ad.t_advert.advertname like '%${advertName}%' ]]>" +
            "</if>" +
            "<if test='tradeId!=null and tradeId!=\"\" and tradeId!=\"0\" '>" +
            " and ad.t_advert.tradeid='${tradeId}' " +
            "</if>" +
            "<if test='adCorpName!=null and adCorpName!=\"\" '>" +
            "<![CDATA[ and adcorp.adCorpName like '%${adCorpName}%' ]]>" +
            "</if>" +
            "<if test='blankId!=null and blankId!=\"\" and blankId!=\"0\" '>" +
            " and ad.t_advert.blankid='${blankId}' " +
            "</if>" +
            "<if test='nowState!=null and nowState!=\"\" and nowState!=\"0\"'>" +
            " and ad.t_advert.nowState=${nowState} " +
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
            "where 1=1 " +
            "<if test='checkTimeBegin!=null and checkTimeBegin!=\"\" and checkTimeEnd!=\"\" and checkTimeEnd!=null '>" +
            "<![CDATA[  and maketimer between to_date('${checkTimeBegin} 08','yyyy-MM-dd hh') and to_date('${checkTimeEnd} 08','yyyy-MM-dd hh') ]]>" +
            "</if>" +
            " </script>")
    public List<Map<String,Object>> advertPutInListTotal(Map<String,Object> paras);

    //广告下发
    @Update("<script>" +
            "upsert into ad.t_advert (advertid,nowstate) values('${advertId}',6)" +
            "</script>")
    public int advertSendDown(Map<String, Object> map);

    //广告状态历史添加记录
    @Insert("<script>" +
            "upsert into ad.t_advert_state_his(id,advertid,nowstate,maker,maketimer)" +
            "values(next value for ad.t_advert_state_his_seq,'${advertId}',6,'${maker}',CONVERT_TZ(CURRENT_DATE(), 'UTC', 'Asia/Shanghai'))" +
            "</script>")
    public int addAdvertHis(Map<String, Object> map);

    @Select("<script> " +
            "select ad.adid advertId,ad.advertname advertName,ad.adcorpName adCorpName,ad.tradeName tradeName,ad.blankName blankName," +
            " case ad.nowstate when 1 then '加入未提交审核' when 2 then '审核中' when 3 then '审核通过' when 4 then '审核不通过' when 5 then '设置策略' when 6 then '待分发'\n" +
            "  when 7 then '已下发到设备' when 8 then '替换' when 9 then '被替换' when 10 then '自动下刊' when 11 then '手动下刊' end state, " +
            "media.filename fileName,media.duration duration,media.filesize fileSize," +
            "policy.devices,policy.pAdPolicyId as advertPolicyId,case policy.playAlone when 1 then '轮播' when 2 then '独播' end playAloneString," +
            " case policy.putInKind when 1 then '购买' when 2 then '赠送' end putInKind,policy.screenPolicyId screenPolicyId,policy.playDates playDays," +
            "screen.screenCutName screen,screen.position screenPosition," +
            "stathis.maketimer checkDay from\n" +
            " (\n" +
            " select advertid as adid,advertname,nowstate,adcorp.adcorpName,t.tradeName,b.blankName from ad.t_advert \n" +
            " inner join ad.t_advertcorp adcorp on adcorp.adCorpId=ad.t_advert.adCorpId " +
            " left join ad.t_trade t on t.tradeid=ad.t_advert.tradeid\n" +
            " left join ad.t_blank b on b.blankId=ad.t_advert.blankId\n" +
            "where nowstate in (3,5,6) " +
            "<if test='advertId!=\"\" and advertId!=null '>" +
            " <![CDATA[  and ad.t_advert.advertid like '%${advertId}%' ]]> " +
            "</if>" +
            "<if test='advertName!=\"\" and advertName!=null '>" +
            "<![CDATA[  and ad.t_advert.advertname like '%${advertName}%' ]]>" +
            "</if>" +
            "<if test='tradeId!=null and tradeId!=\"\" and tradeId!=\"0\" '>" +
            " and ad.t_advert.tradeid='${tradeId}' " +
            "</if>" +
            "<if test='adCorpName!=null and adCorpName!=\"\" '>" +
            "<![CDATA[ and adcorp.adCorpName like '%${adCorpName}%' ]]>" +
            "</if>" +
            "<if test='blankId!=null and blankId!=\"\" and blankId!=\"0\" '>" +
            " and ad.t_advert.blankid='${blankId}' " +
            "</if>" +
            "<if test='nowState!=null and nowState!=\"\" and nowState!=\"0\"'>" +
            " and ad.t_advert.nowState=${nowState} " +
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
            "where 1=1 " +
            "<if test='checkTimeBegin!=null and checkTimeBegin!=\"\" and checkTimeEnd!=\"\" and checkTimeEnd!=null '>" +
            "<![CDATA[  and maketimer between to_date('${checkTimeBegin} 08','yyyy-MM-dd hh') and to_date('${checkTimeEnd} 08','yyyy-MM-dd hh') ]]>" +
            "</if>" +
            " order by ad.nowstate asc,stathis.maketimer desc limit 1000 " +
            " </script>")
    List<AdvertPutIn> getAdvertPutInExcel(Map<String, Object> map);
}
