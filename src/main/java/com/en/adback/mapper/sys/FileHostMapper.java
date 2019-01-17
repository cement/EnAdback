package com.en.adback.mapper.sys;

import com.en.adback.entity.sys.FileHost;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Administrator on 2018/12/5.
 */
@Mapper
public interface FileHostMapper {

    //查询文件服务器的所有信息
    @Select("<script>" +
            "select city.nationId as cityId,city.nationName as cityName,f.ip as ip,f.url as url,f.appName as appName from ad.t_fileHost f\n" +
            "RIGHT  JOIN  pub.t_dic_nation city on  substr(f.cityId,0,4)||'00' = city.nationid and city.areaattr=1 " +
            "</script>")
    List<FileHost> getFileHostList();


    //保存文件服务器信息
    @Insert("<script>" +
            "upsert into ad.t_fileHost (cityId,ip,url,appName) " +
            " values('${cityId}','${ip}','${url}','${appName}')" +
            "</script>")
    int insertFileHost(FileHost fileHost);
}
