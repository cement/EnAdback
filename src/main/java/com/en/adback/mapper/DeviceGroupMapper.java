package com.en.adback.mapper;

import com.en.adback.entity.devicemgr.Device;
import com.en.adback.entity.devicemgr.DeviceGroup;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface DeviceGroupMapper {

    //根据条件查询设备分组信息
    @Select("<script>" +
            "select dga.devGroupId devGroupId,dga.devGroupName devGroupName,dga.policeNo addUser,dga.policeName userName," +
            "dga.addTime addTime,case when c.counts is null then 0 else c.counts end as counts from \n" +
            "(\n" +
            "select dg.devGroupId,dg.devGroupName,u.policeNo,u.policeName,dg.addTime from \n" +
            "ad.t_deviceGroup dg \n" +
            "inner join pub.t_policeUser u on u.policeNo=dg.addUser\n" +
            ") dga left join (\n" +
            "select dg.devGroupId, count(*) as counts from ad.t_deviceGroup dg \n" +
            "inner join ad.t_deviceSubGroup dsg on dsg.devGroupId=dg.devGroupId \n" +
            " group by dg.devGroupId \n" +
            " ) c on c.devGroupId=dga.devGroupId where 1=1 " +
            "<if test='equGroupName!=null and equGroupName!=\"\" '>" +
            "<![CDATA[ and dg.devGroupName like '%${equGroupName}%' ]]>" +
            "</if>" +
            " order by dga.addTime desc" +
            "</script>")
    public List<DeviceGroup> getDeviGroupList(Map<String, Object> map);

    //添加设备分组信息
    @Insert("<script>upsert into ad.t_deviceGroup(devGroupId,devGroupName,addUser,addTime) " +
            "values('${devGroupId}','${devGroupName}','${addUser}',CONVERT_TZ(CURRENT_DATE(), 'UTC', 'Asia/Shanghai'))</script>")
    public int addDeviceGroup(DeviceGroup deviceGroup);


    @Insert("<script>" +
            "upsert into ad.t_devicesubgroup(id,devgroupid,deviid) values(next value for ad.t_devicesubgroup_seq,'${deviceGroupId}','${deviceId}')" +
            "</script>")
    public int addDeviceSubGroup(Map<String, Object> map);

    @Update("<script>upsert into ad.t_deviceGroup(devGroupId,devGroupName) " +
            "values('${devGroupId}','${devGroupName}')</script>")
    public int updateDeviceGroup(DeviceGroup deviceGroup);

    @Delete("<script>" +
            "delete from ad.t_devicegroup where devgroupid='${deviceGroupId}' " +
            "</script>")
    public void deleteDeviceGroup(Map<String, Object> map);

    @Delete("<script>" +
            "delete from ad.t_devicesubgroup where devgroupid='${deviceGroupId}' " +
            "</script>")
    public void deleteDeviceForGroup(Map<String, Object> map);

    @Select("<script>" +
            "select d.deviceId as deviceId,ep.enterpriseId as enterpriseId,ep.enterpriseName as enterpriseName,ep.address as address from idcard.t_device d inner join \n" +
            "(\n" +
            "select dsf.deviceId,dsf.enterpriseid,dsf.stateid from idcard.t_devicestate_flow dsf inner join \n" +
            "(\n" +
            "select maxd.deviceId as maxdeviceid,max(stateid) as maxstateid from idcard.t_devicestate_flow maxd where stateid in ('2','3','4','6') group by maxdeviceid\n" +
            ")maxf on maxf.maxdeviceid=dsf.deviceId and dsf.stateid=maxf.maxstateid\n" +
            ") df on df.deviceId=d.deviceid inner join idcard.t_enterprise ep on ep.enterpriseId=df.enterpriseId\n" +
            "inner join ad.t_devicesubgroup dsg on dsg.deviid=d.deviceid\n" +
            "inner join ad.t_devicegroup dg on dg.devgroupid=dsg.devgroupid where 1=1 \n" +
            "<if test='devGroupId!=null and devGroupId!=\"\" '>" +
            " and dg.devgroupId ='${devGroupId}' " +
            "</if>" +
            "<if test='deviceId!=null and deviceId!=\"\" '>" +
            "<![CDATA[ and d.deviceId like '%${deviceId}%' ]]>" +
            "</if>" +
            "<if test='enterpriseName!=null and enterpriseName!=\"\" '>" +
            "<![CDATA[ and ep.enterpriseName like '%${enterpriseName}%' ]]>" +
            "</if>" +
            "<if test='address!=null and address!=\"\" '>" +
            "<![CDATA[ and ep.address like '%${address}%' ]]>" +
            "</if>" +
            "<if test=' cityId!=\"\" and cityId!=null and cityId!=\"0\" '>" +
            " and substr(ep.orgid,1,4)=substr('${cityId}',1,4) " +
            "</if>" +
            "<if test=' areaId!=\"\" and areaId!=null and areaId!=\"0\" and areaId!=\"0\" '>" +
            " and substr(ep.orgid,1,6)=substr('${areaId}',1,6)" +
            "</if>" +
            "<if test='bedsDown!=null and bedsDown!=\"\" and bedsDown!=\"0\"'>" +
            " <![CDATA[ and ep.beds >= ${bedsDown} ]]>"+
            "</if>" +
            "<if test=' bedsUp!=null and bedsUp!=\"\" and bedsUp!=\"0\" '>" +
            "<![CDATA[ and ep.beds <= ${bedsUp} ]]>"+
            "</if>" +
            "</script>")
    public List<Device> deviceListByGroup(Map<String, Object> map);

    @Select("select max(to_number(devgroupid)) as id from ad.t_devicegroup")
    List<Map<String, Object>> getNewGroupId();


}
