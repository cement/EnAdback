package com.en.adback.mapper.analisys;

import com.en.adback.entity.AdvertCount;
import com.en.adback.entity.advertmgr.AdvertPutIn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdvertCountMapper {


    @Select("<script><![CDATA[" +
            "select ym.c_year as year,ym.c_month as month,case when cb.adcorpNums is null then 0 else cb.adcorpNums end as adcorpNums," +
            "case when cb.blankNums is null then 0 else cb.blankNums end as blankNums from \n" +
            "(\n" +
            "select c_year,c_month from pub.t_year_month \n" +
            "where to_number(c_year)=${year}\n" +
            ") ym left join \n" +
            "(\n" +
            "select count( distinct adcorpid) as adcorpNums,count(distinct blankid) as blankNums,year(uploadTime) as year," +
            "month(uploadTime) as month from ad.t_advert inner join ad.t_sub_advert_policys sap on sap.advertid=ad.t_advert.advertid \n" +
            "where year(uploadTime)=${year}\n" +
            "group by year(uploadTime),month(uploadTime)\n" +
            ") cb on to_number(ym.c_year)=cb.year and to_number(ym.c_month)=cb.month order by ym.c_year,c_month" +
            "]]></script>")
    public List<AdvertCount> getAdcorpAndBlankCount(Map<String, Object> map);


    @Select("<script><![CDATA["+
            "select t.tradeId tradeId,t.tradeName tradeName,case when tc.total is null then 0 else tc.total end as tradeNums from ad.t_trade t left join \n" +
            "(\n" +
            "select count(*) as total,tradeid from ad.t_advert inner join ad.t_sub_advert_policys sap on sap.advertid=ad.t_advert.advertid where\n" +
            " uploadTime>=to_date('${beginTime} 08','yyyy-MM-dd hh') and uploadTime<=to_date('${endTime} 08','yyyy-MM-dd hh') " +
            " group by tradeid\n" +
            ") tc on tc.tradeid=t.tradeid order by tradeNums desc" +
            "]]></script>")
    List<AdvertCount> getTradeCount(Map<String, Object> map);

    @Select("<script><![CDATA[" +
            "select b.blankId blankId,b.blankName blankName,case when bc.total is null then 0 else bc.total end as blankNums from ad.t_blank b left join \n" +
            "(\n" +
            "select count(*) as total,blankid from ad.t_advert inner join ad.t_sub_advert_policys sap on sap.advertid=ad.t_advert.advertid where" +
            " uploadTime>=to_date('${beginTime} 08','yyyy-MM-dd hh') and uploadTime<=to_date('${endTime} 08','yyyy-MM-dd hh') group by blankid \n" +
            ") bc on bc.blankid=b.blankid order by blankNums desc" +
            " ]]></script>")
    List<AdvertCount> getBlankCount(Map<String, Object> map);

    //统计每年各策略广告数量
    @Select("<script><![CDATA[" +
            "select ym.c_year year,ym.c_month month,pc.screenpolicyId screenPolicyId,\n" +
            "case when pc.total is null then 0 else pc.total end as screenPolicyNums from \n" +
            "(\n" +
            "select c_year,c_month from pub.t_year_month where to_number(c_year)=${year} order by c_year,c_month \n" +
            ") ym left join \n" +
            "(\n" +
            "select pps.screenpolicyId,count(*) as total,year(ad.uploadTime) as ayear,month(ad.uploadTime) as amonth " +
            "from ad.t_sub_advert_policys sbp \n" +
            "inner join ad.t_advert ad on ad.advertid=sbp.advertId \n" +
            "inner join ad.t_advert_policys ap on sbp.advertpolicysid=ap.advertPolicysId \n" +
            "inner join ad.t_play_policy_screen pps on pps.screenpolicyId=ap.screenPolicyId \n" +
            "where year(ad.uploadTime)=${year} group by pps.screenPolicyId,ayear,amonth \n" +
            ")\n" +
            "pc on pc.ayear=to_number(ym.c_year) and pc.amonth=to_number(ym.c_month)" +
            "]]></script>")
    List<AdvertCount> getPolicyCount(Map<String, Object> map);

    //获取各个策略数据
//    @Select("<script><![CDATA[" +
//            "select ym.c_year year,ym.c_month month,ps.screenpolicyId screenPolicyId,ps.policyName screenPolicyName,case when pc.total is null then 0 else pc.total end as screenPolicyNums from \n" +
//            "(\n" +
//            "select c_year,c_month from pub.t_year_month \n" +
//            "where to_number(c_year)=${year} order by c_year,c_month\n" +
//            ") ym left join \n" +
//            "(\n" +
//            "select screenpolicyId,policyName from ad.t_play_policy_screen\n" +
//            ") ps on 1=1\n" +
//            "left join \n" +
//            "(\n" +
//            "select pps.screenpolicyId,count(*) as total,year(ad.uploadTime) as ayear,month(ad.uploadTime) as amonth from ad.t_sub_advert_policys sbp" +
//            " inner join ad.t_advert ad on ad.advertid=sbp.advertId\n" +
//            "inner join ad.t_advert_policys ap on sbp.advertpolicysid=ap.advertPolicysId\n" +
//            "inner join ad.t_play_policy_screen pps on pps.screenpolicyId=ap.screenPolicyId " +
//            "where year(ad.uploadTime)=${year}" +
//            " group by pps.screenPolicyId,ayear,amonth\n" +
//            ") pc on pc.screenpolicyId=ps.screenpolicyId and pc.ayear=to_number(ym.c_year) and pc.amonth=to_number(ym.c_month)" +
//            "]]></script>")
//    List<AdvertCount> getPolicyCount(Map<String, Object> map);


    @Select("<script><![CDATA[" +
            "select sap.advertId advertId,playDates,devices from ad.t_sub_advert_policys sap " +
            "inner join ad.t_advert_policys ap on ap.advertPolicysId=sap.advertPolicysId \n" +
            "inner join ad.t_advert ad on ad.advertid=sap.advertid \n" +
            "where ad.uploadtime>=to_date('${beginTime} 08','yyyy-MM-dd hh') and ad.uploadtime<=to_date('${endTime} 08','yyyy-MM-dd hh')" +
            "]]></script>")
    List<AdvertCount> getQuxianCount(Map<String, Object> map);

    @Select("<script>" +
            "select ad.advertId advertId,ad.advertName advertName,t.tradeName tradeName,b.blankName blankName,ac.adcorpName adCorpName,am.filename fileName," +
            "am.downloadFileName downLoadFileName,am.duration duration,am.fileSize fileSize,ad.nowstate nowState,his.maker maker,his.maketimer checkDay from\n" +
            "(\n" +
            "select advertId,advertName,tradeId,blankId,adcorpId,nowstate,uploadTime from ad.t_advert\n" +
            ") ad inner join ad.t_advertcorp ac on ac.adcorpId=ad.adcorpId \n" +
            "inner join ad.t_trade t on t.tradeId=ad.tradeId \n" +
            "inner join ad.t_blank b on b.blankId=ad.blankId \n" +
            "inner join ad.t_advert_media am on am.advertId=ad.advertId \n" +
            "inner join (\n" +
            "select ash.advertid,maker,maketimer,ash.nowstate from ad.t_advert_state_his ash inner join \n" +
            "(\n" +
            "select advertid,max(nowstate),max(id) id from ad.t_advert_state_his group by advertid \n" +
            ")\n" +
            "max on max.advertid=ash.advertid where ash.id=max.id and ash.nowstate>=6\n" +
            ") his on his.advertId=ad.advertId \n" +
            "where 1=1 \n" +
            "<if test='advertId!=null and advertId!=\"\"  '>" +
            "<![CDATA[ and ad.advertId like '%${advertId}%' ]]> " +
            "</if>" +
            "<if test='advertName!=\"\" and advertName!=null '>" +
            "<![CDATA[ and ad.advertName like '%${advertName}%' ]]> " +
            "</if>" +
            "<if test='tradeId!=null and tradeId!=\"\" and tradeId!=\"0\" '>" +
            "and ad.tradeId='${tradeId}' " +
            "</if>" +
            "<if test='adCorpName!=null and adCorpName!=\"\" '>" +
            " <![CDATA[ and ac.adcorpName like '%${adCorpName}%' ]]>" +
            "</if>" +
            "<if test='blankId!=null and blankId!=\"\" and blankId!=\"0\" '>" +
            "and ad.blankId='${blankId}' " +
            "</if>" +
            "<if test='beginTime!=null and beginTime!=\"\" and endTime!=null and endTime!=\"\" '>" +
            " <![CDATA[ and ad.uploadtime between to_date('${beginTime} 08','yyyy-MM-dd hh') and to_date('${endTime} 08','yyyy-MM-dd hh') ]]> " +
            "</if>" +
            " order by ad.uploadtime desc limit ${pageSize} offset ${pageBegin} " +
            "</script>")
    List<AdvertPutIn> getAdvertCountList(Map<String, Object> map);

    @Select("<script>" +
            "select count(*) as total from\n" +
            "(\n" +
            "select advertId,advertName,tradeId,blankId,adcorpId,nowstate,uploadTime from ad.t_advert\n" +
            ") ad inner join ad.t_advertcorp ac on ac.adcorpId=ad.adcorpId \n" +
            "inner join ad.t_trade t on t.tradeId=ad.tradeId \n" +
            "inner join ad.t_blank b on b.blankId=ad.blankId \n" +
            "inner join ad.t_advert_media am on am.advertId=ad.advertId \n" +
            "inner join (\n" +
            "select ash.advertid,maker,maketimer,ash.nowstate from ad.t_advert_state_his ash inner join \n" +
            "(\n" +
            "select advertid,max(nowstate),max(id) id from ad.t_advert_state_his group by advertid \n" +
            ")\n" +
            "max on max.advertid=ash.advertid where ash.id=max.id and ash.nowstate>=6\n" +
            ") his on his.advertId=ad.advertId \n" +
            "where 1=1 \n" +
            "<if test='advertId!=null and advertId!=\"\"  '>" +
            "<![CDATA[ and ad.advertId like '%${advertId}%' ]]> " +
            "</if>" +
            "<if test='advertName!=\"\" and advertName!=null '>" +
            "<![CDATA[ and ad.advertName like '%${advertName}%' ]]> " +
            "</if>" +
            "<if test='tradeId!=null and tradeId!=\"\" and tradeId!=\"0\" '>" +
            "and ad.tradeId='${tradeId}' " +
            "</if>" +
            "<if test='adCorpName!=null and adCorpName!=\"\" '>" +
            " <![CDATA[ and ac.adcorpName like '%${adCorpName}%' ]]>" +
            "</if>" +
            "<if test='blankId!=null and blankId!=\"\" and blankId!=\"0\" '>" +
            "and ad.blankId='${blankId}' " +
            "</if>" +
            "<if test='beginTime!=null and beginTime!=\"\" and endTime!=null and endTime!=\"\" '>" +
            " <![CDATA[ and ad.uploadtime between to_date('${beginTime} 08','yyyy-MM-dd hh') and to_date('${endTime} 08','yyyy-MM-dd hh') ]]> " +
            "</if>" +
            "</script>")
    List<Map<String,Object>> getAdvertCountListTotal(Map<String, Object> map);
}
