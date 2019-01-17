package com.en.adback.mapper.advertmgr;

import com.en.adback.entity.advertmgr.Advert;
import com.en.adback.entity.advertmgr.AdvertFiles;
import com.en.adback.entity.advertmgr.AdvertMedia;
import com.en.adback.entity.advertmgr.AdvertStateHis;
import com.en.adback.entity.dic.AdvertCorp;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;
import java.util.Map;

@Mapper
public interface AdvertMapper {


    /**
     *  按条件查询广告上传
     * @param map
     * @return
     *
     *
     */
    @Select("<script>" +
            "select c.adCorpid as adCorpid,c.adCorpName as adCorpName , ad.advertid as advertid,ad.advertName as advertName,ad.uploadTime as uploadTime ,ad.nowState as nowState," +
            " tr.tradeId as tradeId,tr.tradeName as tradeName ,bl.blankId as blankId,bl.blankName as blankName,m.fileName as fileName,m.duration as duration,m.fileSize as fileSize,his.maker as maker from " +
            " (select adCorpid,advertid,advertName ,uploadTime ,nowState ,tradeId ,blankId FROM ad.t_advert  where nowstate in (${state})) as ad inner join ad.t_advertcorp c on ad.adcorpid=c.adcorpid " +
            " inner join  ad.t_trade tr on ad.tradeId=tr.tradeId inner join  ad.t_blank bl on ad.blankId=bl.blankId "+
            " inner join ad.t_advert_media m  on ad.advertid=m.advertid inner join ad.t_advert_state_his his on ad.advertid= his.advertid and ad.nowstate=his.nowstate "+
            " where 1=1"+
            "<if test='advertName!=\"\" and advertName!=null '>" +
            "<![CDATA[ and ad.advertName like '%${advertName}%' ]]>" +
            "</if>" +
            "<if test='tradeId != \"0\"  '>" +
            " and tr.tradeId  ='${tradeId}' " +
            "</if>" +
            "<if test='adCorpName!=\"\" and adCorpName!=null '>" +
            " <![CDATA[ and c.adCorpName like '%${adCorpName}%' ]]>" +
            "</if>" +
            "<if test='blankId!=\"0\"  '>" +
            " and bl.blankId='${blankId}' " +
            "</if>" +
            "<if test='uploadTimeBegin!=\"\" and uploadTimeBegin!=null and uploadTimeEnd!=null and uploadTimeEnd!=\"\" '>" +
            "<![CDATA[ and ad.uploadTime between to_date('${uploadTimeBegin} 08','yyyy-MM-dd hh') and to_date('${uploadTimeEnd} 08','yyyy-MM-dd hh') ]]>" +
            "</if>" +
            " order by ad.nowState asc,ad.uploadTime desc limit ${pageSize} offset ${pageBegin}" +
            "</script>")
//    ad.nowState asc
    List<Advert> advertList(Map<String, Object> map);

    /**
     *  按条件查询广告上传 总数
     * @param map
     * @return
     */
    @Select("<script>" +
            "select count(*) as total from " +
            " (select adCorpid,advertid,advertName ,uploadTime ,nowState,tradeId ,blankId FROM ad.t_advert  where nowstate in (${state})) as ad inner join ad.t_advertcorp c on ad.adcorpid=c.adcorpid " +
            " inner join  ad.t_trade tr on ad.tradeId=tr.tradeId inner join  ad.t_blank bl on ad.blankId=bl.blankId "+
            " inner join ad.t_advert_media m  on ad.advertid=m.advertid inner join ad.t_advert_state_his his on ad.advertid= his.advertid and ad.nowstate=his.nowstate "+
            " where 1=1"+
            "<if test='advertName!=\"\" and advertName!=null '>" +
            "<![CDATA[ and ad.advertName like '%${advertName}%' ]]>" +
            "</if>" +
            "<if test='tradeId != \"0\"  '>" +
            " and tr.tradeId  ='${tradeId}' " +
            "</if>" +
            "<if test='adCorpName!=\"\" and adCorpName!=null '>" +
            " <![CDATA[ and c.adCorpName like '%${adCorpName}%' ]]>" +
            "</if>" +
            "<if test='blankId!=\"0\"  '>" +
            " and bl.blankId='${blankId}' " +
            "</if>" +
            "<if test='uploadTimeBegin!=\"\" and uploadTimeBegin!=null and uploadTimeEnd!=null and uploadTimeEnd!=\"\" '>" +
            "<![CDATA[ and ad.uploadTime between to_date('${uploadTimeBegin} 08','yyyy-MM-dd hh') and to_date('${uploadTimeEnd} 08','yyyy-MM-dd hh') ]]>" +
            "</if>" +
            "</script>")
    List<Map<String,Object>> advertCount(Map<String, Object> map);



    /**
     *  条件查询，广告审核
     * @param map
     * @return
     */
    @Select("<script>" +
            " select  c.adCorpid as adCorpid,c.adCorpName as adCorpName , ad.advertid as advertid,ad.advertName as advertName,ad.uploadTime as uploadTime ,ad.nowState as nowState, " +
            "  tr.tradeId as tradeId,tr.tradeName as tradeName ,bl.blankId as blankId,bl.blankName as blankName,m.fileName as fileName,m.duration as duration,m.fileSize as fileSize," +
            "  his.maker as maker from (select adCorpid,advertid,advertName ,uploadTime ,nowState,tradeId,blankId FROM ad.t_advert where nowstate in (2,3,4)  )  ad inner join ad.t_advertcorp c on ad.adcorpid=c.adcorpid inner join ad.t_trade tr on ad.tradeId=tr.tradeId " +
            "  inner join ad.t_blank bl on ad.blankId=bl.blankId inner join ad.t_advert_media m on ad.advertid=m.advertid inner join ad.t_advert_state_his his on ad.advertid= his.advertid and ad.nowstate=his.nowstate "+
            " where 1=1 "+
            "<if test='advertName!=\"\" and advertName!=null '>" +
            "<![CDATA[ and ad.advertName like '%${advertName}%' ]]> " +
            "</if>" +
            "<if test='tradeId != \"0\"  '>" +
            " and tr.tradeId  ='${tradeId}' " +
            "</if>" +
            "<if test='adCorpName!=\"\" and adCorpName!=null '>" +
            " <![CDATA[ and c.adCorpName like '%${adCorpName}%' ]]>" +
            "</if>" +
            "<if test='blankId!=\"0\"  '>" +
            " and bl.blankId='${blankId}' " +
            "</if>" +
            "<if test='uploadTimeBegin!=\"\" and uploadTimeBegin!=null and uploadTimeEnd!=null and uploadTimeEnd!=\"\" '>" +
            "   <![CDATA[ and  ad.uploadTime  between to_date('${uploadTimeBegin} 08','yyyy-MM-dd hh') and to_date('${uploadTimeEnd} 08','yyyy-MM-dd hh') ]]>" +
            "</if>" +
            " order by ad.nowState asc,ad.uploadTime desc limit ${pageSize} offset ${pageBegin}" +
            "</script>")
//    ad.nowState
    List<Advert> advertAuditList(Map<String, Object> map);

    /**
     *  条件查询，广告审核总数
     * @param map
     * @return
     */
    @Select("<script>" +
            "  select  count(*) as total  from (select adCorpid,advertid,advertName ,uploadTime ,nowState,tradeId,blankId FROM ad.t_advert where nowstate in (2,3,4)  )  ad inner join ad.t_advertcorp c on ad.adcorpid=c.adcorpid inner join ad.t_trade tr on ad.tradeId=tr.tradeId " +
            "  inner join ad.t_blank bl on ad.blankId=bl.blankId inner join ad.t_advert_media m on ad.advertid=m.advertid inner join ad.t_advert_state_his his on ad.advertid= his.advertid and ad.nowstate=his.nowstate "+
            " where 1=1 "+
            "<if test='advertName!=\"\" and advertName!=null '>" +
            "<![CDATA[ and ad.advertName like '%${advertName}%' ]]> " +
            "</if>" +
            "<if test='tradeId != \"0\"  '>" +
            " and tr.tradeId  ='${tradeId}' " +
            "</if>" +
            "<if test='adCorpName!=\"\" and adCorpName!=null '>" +
            " <![CDATA[ and c.adCorpName like '%${adCorpName}%' ]]>" +
            "</if>" +
            "<if test='blankId!=\"0\"  '>" +
            " and bl.blankId='${blankId}' " +
            "</if>" +
            "<if test='uploadTimeBegin!=\"\" and uploadTimeBegin!=null and uploadTimeEnd!=null and uploadTimeEnd!=\"\" '>" +
            "   <![CDATA[ and  ad.uploadTime  between to_date('${uploadTimeBegin} 08','yyyy-MM-dd hh') and to_date('${uploadTimeEnd} 08','yyyy-MM-dd hh') ]]>" +
            "</if>" +
            "</script>")
   public List<Map<String,Object>> advertAuditCount(Map<String, Object> map);


    /**
     * 查询该广告媒体文件
     * @return
     */
    @Select("select downLoadFileName,advertId ,fileName,fileSize,duration,downLoadUrl from ad.t_advert_media where advertId='${advertId}' ")
    public List<AdvertMedia> advertMediaList(Map<String,Object> paras);

    /**
     * 查询该广告资料文件
     * @return
     */
    @Select("select downLoadFileName,advertId ,fileName,downLoadUrl from ad.t_advert_files where advertId='${advertId}' ")
    public List<AdvertFiles> advertFilesList(Map<String,Object> paras);

    // 插入广告状态历史表
    @Insert("upsert into ad.t_advert_state_his(id,advertId,nowState,maker,makeTimer,memo)" +
            " values(next value for ad.t_advert_state_his_seq,#{advertId},#{nowState},#{maker}," +
            "CONVERT_TZ(CURRENT_DATE(), 'UTC', 'Asia/Shanghai'),#{memo})")
    public int insertAdvertStateHis(AdvertStateHis advertStateHis);

    //修改广告表 状态字段
    @Update("upsert into ad.t_advert(advertId,nowState)" +
            " values('${advertId}',${nowState})")
    public int updateAdvertState(Map<String,Object> paras);



    /**
     * 添加广告
     */
    @Insert("upsert into ad.t_advert(advertId,advertName,adCorpId,uploadTime,nowState,tradeId,blankId) " +
            "values ('${advertId}','${advertName}','${adCorpId}',CONVERT_TZ(CURRENT_DATE(), 'UTC', 'Asia/Shanghai'),1, '${tradeId}','${blankId}')")
    int saveAdvert (Advert advert);

    /**
     * 修改广告
     */
    @Insert("upsert into ad.t_advert(advertId,advertName,adCorpId,tradeId,blankId) " +
            " values ('${advertId}','${advertName}','${adCorpId}','${tradeId}','${blankId}')")
    int updateAdvert (Advert advert);



    // 插入广告媒体表
    @Insert("upsert into ad.t_advert_media(downLoadFileName,advertId,fileName,fileSize,duration,downLoadUrl) values(" +
            " #{downLoadFileName},#{advertId},#{fileName},#{fileSize},#{duration},#{downLoadUrl} )")
    public int insertAdvertMedia(AdvertMedia advertMedia);
    // 插入广告资料表
    @Insert("upsert into ad.t_advert_files(downLoadFileName,advertId ,fileName ,downLoadUrl ) values(" +
            " #{downLoadFileName},#{advertId},#{fileName},#{downLoadUrl} )")
    public int insertAdvertFile(AdvertFiles advertFiles);



    @Delete("delete from ad.t_advert where advertId = '${advertId}'")
    int deleteAdvert(Map<String,Object> re);


    // 查询各状态广告
    @Select ("<script> select advertId,advertName,adCorpId ,uploadTime ,blankId,tradeId,nowState   from ad.t_advert where 1=1" +
            " <if test='nowState != null and nowState !=0'>" +
            " and nowState=${nowState}" +
            "</if> </script>")
    public List<Advert> advertListByNowState(Map<String,Object> paras);

    // 查询历史表状态广告
    @Select("<script>select his.advertid as advertId,his.nowstate as nowState,his.maker as maker,his.maketimer as makeTimer," +
            "his.memo as memo from ad.t_advert_state_his his \n" +
           " where his.advertid='${advertId}'</script>")
    public List<AdvertStateHis> getAllStateHisByAdvertId(Map<String, Object> map);

    @Select("<script>select his.advertid as advertId,his.nowstate as nowState,his.maker as maker,his.maketimer as makeTimer," +
            "his.memo as memo from ad.t_advert_state_his his inner join \n" +
            "(\n" +
            "select advertid,max(nowstate) as state from ad.t_advert_state_his where nowstate in (1,2,3,4) group by advertid\n" +
            ") maxhis on maxhis.advertid=his.advertid and his.nowstate=maxhis.state where his.advertid='${advertId}'</script>")
    public List<AdvertStateHis> getStateHisByAdvertId(Map<String, Object> map);
}
