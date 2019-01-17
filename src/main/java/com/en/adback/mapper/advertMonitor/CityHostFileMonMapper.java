package com.en.adback.mapper.advertMonitor;

import com.en.adback.entity.advertmgr.FilehostDownloadRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface CityHostFileMonMapper {

    @Select("<script><![CDATA[" +
            "select fileName,fdr.cityId cityId,beginPlayTime,arrivedCityHostTime,ip hostUrl,url downloadUrl from ad.t_filehost_download_role fdr\n" +
            "inner join ad.t_fileHost fh on fh.cityId=fdr.cityId " +
            " where fdr.beginPlayTime='${putinBeginTime}']]></script>")
    public List<FilehostDownloadRole> cityHostFileList(Map<String, Object> map);
}
