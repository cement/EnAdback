package com.en.adback.mapper.sys;

import com.en.adback.entity.sys.DefaultAdvert;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by Administrator on 2018/12/8.
 */
@Mapper
public interface DefaultAdvertMapper {

    //查询全部
    @Select("<script>" +
            " select c.screenCutId as screenCutId,c.screenCutName as screenCutName,c.position as position,c.screensize as screenSize , d.fileName as fileName,d.defaultFileName as defaultFileName   from ad.t_screen_cut c left outer join ad.t_defaultAdvert d on c.screencutId =d.screencutid " +
            "</script>")
    List<DefaultAdvert> getDefaultAdvertList();

    // 保存
    @Insert("<script>" +
            " upsert into ad.t_defaultAdvert(screenCutId,fileName,defaultFileName)" +
            " values('${screenCutId}','${fileName}','${defaultFileName}')" +
            "</script>")
    int insertDefaultAdvert(DefaultAdvert defaultAdvert);


}
