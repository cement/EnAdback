package com.en.adback.mapper;

import com.en.adback.entity.devicemgr.DeviceGroup;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface DeviceMgrMapper {

    // 查询设备分组
     @Select("<script> select devGroupId,devGroupName,addUser,deviceIds,addTime  from ad.t_deviceGroup " +
             " where 1=1 " +
             " <if test ='devGroupName != \"\"'>" +
             "   and  devGroupName like '%${devGroupName}%' " +
             " </if> " +
             " <if test ='devGroupIds !=\"\"'>" +
             "    and devGroupId in (${devGroupIds})" +
             " </if> " +
             "</script>")
     public List<DeviceGroup> deviceGroupList(Map<String,Object> paras);

     // 保存设备分组
    @Insert("upsert into ad.t_deviceGroup(devGroupId,devGroupName,addUser,deviceIds,addTime) " +
            " values('${devGroupId}','${devGroupName}','${loginUserId}','${deviceIds}',CONVERT_TZ(CURRENT_DATE(), 'UTC', 'Asia/Shanghai'))")
     public int saveDeviceGroup(DeviceGroup deviceGroup);

    // 删除设备分组
    @Delete("delete from ad.t_deviceGroup where devGroupId ='${devGroupId}'")
    public int delDeviceGroup(Map<String,Object> paras);

}
