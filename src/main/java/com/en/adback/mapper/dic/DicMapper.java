package com.en.adback.mapper.dic;

import com.en.adback.entity.devicemgr.Device;
import com.en.adback.entity.dic.*;
import com.en.adback.entity.sys.GroupRole;
import com.en.adback.entity.sys.Role;
import com.en.adback.entity.sys.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/3.
 */
@Mapper
public interface DicMapper {

    @Select("<script>" +
            "select  enterpriseId, enterpriseName, beds, systemId from ad.t_enterprise where 1=1" +
            "<if test='systemId != null and systemId != \"\" '>" +
            " and systemid='${systemId}' " +
            "</if>" +
            "</script>")
    List<Enterprise> getEnterpriseList(Map<String,Object> map);

    @Select("<script>" +
            "select  adCorpId, adCorpName, address, tradeId, blankId,tel ,linkMan ,addTime, addUser from ad.t_advertcorp" +
            "</script>")
    List<AdvertCorp> getAdvertCorpList();

    @Select("<script>" +
            "select  state, stateName, stateTime from ad.t_advertState" +
            "</script>")
    List<AdvertState> getAdvertStateList();

    @Select("<script>" +
            "select blankId, blankName, pareBlankId , memo  from ad.t_blank" +
            "</script>")
    List<Blank> getBlankList();


    @Select("<script>" +
            "select    devStatusId,devStatusName from ad.t_deviceStatus" +
            "</script>")
    List<DeviceStatus> getDeviceStatusList();

    @Select("<script>" +
            "select tradeId,tradeName, memo from ad.t_trade" +
            "</script>")
    List<Trade> getTradeList();

    @Select("<script>" +
            " select nationId as nid,nationId,nationName,spell,aspell,longitude,latitude,areaAttr from pub.t_dic_nation" +
            "</script>")
    List<Area> getOrgList();

    @Select("<script>" +
            " select nationId as nid,nationId,nationName,spell,aspell,longitude,latitude,areaAttr from pub.t_dic_nation where nationid like '43%'" +
            "</script>")
    List<Area> getAreaList();

    @Select("<script>" +
            " select userid,username from ad.t_user " +
            "</script>")
    List<User> getUserList();

    @Select("<script>" +
            " select groupRoleId,groupRoleName ,memo,menus from ad.t_groupRole " +
            "</script>")
    List<GroupRole> getGroupRoleList();

    @Select("<script>" +
            "  select roleId,roleName from ad.t_role where groupRoleId = '${groupRoleId}'" +
            "</script>")
    List<Role> getRoleList(Map<String,Object> re);

    // 查询原有已发布策略设备编号
    @Select("<script>" +
            " select deviceId from ad.t_haveSendPolicy_device  where count > 0" +
            "</script>")
    List<Device> deviceIdList();
}
