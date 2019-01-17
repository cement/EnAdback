package com.en.adback.mapper.sys;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysSettingMapper {

    @Select("select opstr from ad.t_systemparams where pid='as001'")
    public List<Map<String,Object>> getSettingParams();

    @Update("upsert into ad.t_systemparams(pid,opstr) values('as001','${settings}')")
    public int settingParams(Map<String,Object> map);
}
