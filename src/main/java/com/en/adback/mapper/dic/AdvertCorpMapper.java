package com.en.adback.mapper.dic;

import com.en.adback.entity.dic.AdvertCorp;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/9.
 */
@Mapper
public interface AdvertCorpMapper {

    //按条件查询广告公司信息
    @Select("<script>" +
            "select adCorpId,adCorpName,address," +
            " tel,linkMan,TO_CHAR(addTime,'yyyy-MM-dd HH:mm:ss') as addTime,addUser from ad.t_advertcorp ac\n" +
            " limit ${pageSize} offset ${pageBegin} " +
            "</script>")
    List<AdvertCorp> getAdvertCorpList(Map<String,Object> re);

    //查询广告公司总条数
    @Select("<script>" +
            " select count(*) from ad.t_advertcorp ac  " +
            "</script>")
    int getAdvertCorpTotal();

    //插入 广告公司信息
    @Insert("<script>" +
            "upsert into ad.t_advertcorp(adCorpId,adCorpName,address,tradeId,blankId,tel,linkMan,addTime,addUser ) " +
            " values('${adCorpId}','${adCorpName}','${address}', '' , '','${tel}','${linkMan}',CONVERT_TZ(CURRENT_DATE(), 'UTC', 'Asia/Shanghai'),'${addUser}' )" +
            "</script>")
    int insertAdvertCorp(AdvertCorp advertCorp);


    //插入、修改 广告公司信息
    @Insert("<script>" +
            "upsert into ad.t_advertcorp(adCorpId,adCorpName,address,tel,linkMan,addUser ) " +
            " values('${adCorpId}','${adCorpName}','${address}','${tel}','${linkMan}','${addUser}' )" +
            "</script>")
    int updateAdvertCorp(AdvertCorp advertCorp);

    //
    @Select("<script>" +
            " select * from ad.t_advertcorp where adCorpId='${adCorpId}'" +
            "</script>")
    List<AdvertCorp> findByadCorpId(Map<String,String> re);

    //删除
    @Delete("<script>" +
            " delete from ad.t_advertcorp where adCorpId='${adCorpId}'" +
            "</script>")
    int deleteAdCorp(Map<String,String> re);

    //查询公司 -- 广告公司选择用
    @Select("<script>" +
            "  select adCorpId,adCorpName from ad.t_advertcorp " +
            "</script>")
    public List<AdvertCorp> advertCorpChoosedList();
}
