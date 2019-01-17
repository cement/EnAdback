package com.en.adback.mapper;

import com.en.adback.entity.devicemgr.Device;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface DeviceMapper {

    //设备查询 选择
    @Select("<script>" +
            "select d.deviceId deviceId,ep.enterpriseId enterpriseId,ep.enterpriseName enterpriseName,ep.address address from idcard.t_device d inner join \n" +
            "(\n" +
            "select dsf.deviceId,dsf.enterpriseid,dsf.stateid from idcard.t_devicestate_flow dsf inner join \n" +
            "(\n" +
            "select maxd.deviceId as maxdeviceid,max(stateid) as maxstateid from idcard.t_devicestate_flow maxd where stateid in ('2','3','4','6') group by maxdeviceid\n" +
            ")maxf on maxf.maxdeviceid=dsf.deviceId and dsf.stateid=maxf.maxstateid\n" +
            ") df on df.deviceId=d.deviceid inner join idcard.t_enterprise ep on ep.enterpriseId=df.enterpriseId where 1=1\n" +
            "<if test='deviceId!=null and deviceId!=\"\"'>" +
            "<![CDATA[ and d.deviceId like '%${deviceId}%' ]]>\n" +
            "</if>" +
            "<if test='enterpriseName!=null and enterpriseName!=\"\"'>" +
            "<![CDATA[ and ep.enterpriseName like '%${enterpriseName}%' ]]>\n" +
            "</if>" +
            "<if test='address!=null and address!=\"\"'>" +
            "<![CDATA[ and ep.address like '%${address}%' ]]>\n" +
            "</if>" +
            "<if test='cityId!=null and cityId!=\"\" and cityId!=\"0\"'>" +
            " and substr(ep.orgid,1,4)=substr('${cityId}',1,4)\n" +
            "</if>" +
            "<if test='areaId!=null and areaId!=\"\" and areaId!=\"0\" and cityId!=\"0\"'>" +
            " and substr(ep.orgid,1,6)=substr('${areaId}',1,6)\n" +
            "</if>" +
            "<if test='minBeds!=null and minBeds!=\"\" '>" +
            "<![CDATA[ and ep.beds>=${minBeds} ]]>\n" +
            "</if>" +
            "<if test='maxBeds!=null and maxBeds!=\"\"'>" +
            "<![CDATA[  and ep.beds<=${maxBeds} ]]>" +
            "</if>" +
            "</script>")
    public List<Device> getDeviceAllList(Map<String, Object> map);

}
