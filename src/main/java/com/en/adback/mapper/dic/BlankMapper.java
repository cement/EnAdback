package com.en.adback.mapper.dic;

import com.en.adback.entity.dic.Blank;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/10.
 */
@Mapper
public interface BlankMapper {

    //查询
    @Select("<script>" +
            " select b.blankId as blankId,b.blankName as blankName ,b.pareBlankId as pareBlankId,p.blankName as pareBlankName,b.memo as memo from ad.t_blank b\n" +
            " left join ad.t_blank p on p.blankId = b.pareBlankId " +
            " limit ${pageSize} offset ${pageBegin} " +
            "</script>")
    List<Blank>  getBlankList(Map<String,Object> re);

    @Select("<script>" +
            "select count(*) from ad.t_blank b left join ad.t_blank p on p.blankId = b.pareBlankId " +
            "</script>")
    int getBlankListTotal();

    //查询最大的blankId
    @Select("<script>" +
            "select blankId from ad.t_blank order by blankId desc limit 1" +
            "</script>")
    String getMaxBlankId();

    //插入  修改
    @Insert("<script>" +
            " upsert into ad.t_blank(blankId,blankName,pareBlankId,memo)" +
            "  values('${blankId}','${blankName}','${pareBlankId}','${memo}')" +
            "</script>")
    int upsertBlank(Blank blank);


    @Delete("<script>" +
            " delete from ad.t_blank where blankId='${blankId}'" +
            "</script>")
    int deleteBlank(Map<String,String> re);
}
