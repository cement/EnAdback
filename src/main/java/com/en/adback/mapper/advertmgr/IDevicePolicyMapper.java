package com.en.adback.mapper.advertmgr;

/**
 * 策略运算
 * 每日晚 23 点开始计算
 * 1.广告文件下发策略
 * 2.第4天凌晨开始播放的各个设备的广告
 */

import com.en.adback.entity.advertmgr.AdvertDayPolicyRole;
import com.en.adback.entity.calpolicy.AllDayPolicy;
import com.en.adback.entity.calpolicy.AllDayPolicySub;
import com.en.adback.entity.calpolicy.AllDevices;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface IDevicePolicyMapper {

    //获取所有可用设备
    //演示版
//    @Select("select deviceId,substr(e.orgId,0,4) ||'00' as cityId , ip,appName,url from idCard.t_device d inner join " +
//            " idcard.t_enterprise e on d.enterpriseid=e.enterpriseid " +
//            " inner join ad.t_fileHost h on substr(e.orgId,0,4) ||'00' = cityid " +
//            "where d.deviStatesId !=4  ")
    //二次
//    @Select("select d.deviceId as deviceId,substr(e.orgid,1,4) || '00' as cityId,ip,appName,url from idcard.t_device d \n" +
//            "inner join\n" +
//            "(\n" +
//            "select deviceId as maxdeviceid,max(stateId) as maxstateid  from idcard.t_deviceState_flow df " +
//            "where df.stateid in ('2','3','4','6') group by df.deviceid\n" +
//            ") maxd on maxd.maxdeviceid=d.deviceid \n" +
//            "inner join idcard.t_devicestate_flow flow on flow.deviceid=maxd.maxdeviceid and flow.stateid=maxd.maxstateid \n" +
//            "inner join idcard.t_enterprise e on e.enterpriseid=flow.enterpriseid \n" +
//            "inner join ad.t_fileHost h on substr(e.orgid,0,4) ||'00' = h.cityid")
//    public List<AllDevices> usedDeviceList();

    // 获取所有该天策略(广告处于待分发状态 , nowstate=6)
    @Select("<script> " +
             " select  advertPolicysId,screenPolicyId,screenId,playTimeBegin,playTimeEnd,devices from ad.t_advert_policys" +
            "  where playDates like '%${theDate}%' and  advertPolicysId in ( " +
            "   select advertPolicysId from ad.t_sub_advert_policys p inner join ad.t_advert a on p.advertId=a.advertId and a.nowState in (6,7,8) " +
            ") </script>")
    public List<AllDayPolicy> dayPolicyList(Map<String,Object> paras);


    // 获取本天所有策略字表
    @Select("<script>" +
            "  select p.advertPolicysId as advertPolicysId,c.position as position,m.downloadfilename as downloadfilename,m.duration as duration,a.advertId as advertId from ad.t_sub_advert_policys p  \n" +
            "             inner join ad.t_advert a on p.advertId=a.advertId and a.nowState in (6,7,8) " +
            "             inner join ad.t_advert_media m on p.advertId=m.advertId  " +
            "            inner join ad.t_screen_cut c on p.screenCutId=c.screenCutId " +
            "             inner join  " +
            "            (select  advertPolicysId,devices from ad.t_advert_policys where playDates like '%${theDate}%')aa on p.advertPolicysId =aa.advertPolicysid\n " +
            " </script>")
    public List<AllDayPolicySub> dayPolicySubList(Map<String,Object> paras);


    // 获取本天所有策略字表 --计算服务器下发文件用
    @Select("<script>" +
            "  select p.advertPolicysId as advertPolicysId,c.position as position,m.downloadfilename as downloadfilename,m.duration as duration,aa.devices as devices from ad.t_sub_advert_policys p  \n" +
            "             inner join ad.t_advert a on p.advertId=a.advertId and a.nowState in (6,7,8) " +
            "             inner join ad.t_advert_media m on p.advertId=m.advertId  " +
            "            inner join ad.t_screen_cut c on p.screenCutId=c.screenCutId " +
            "             inner join  " +
            "            (select  advertPolicysId,devices from ad.t_advert_policys where playDates like '%${theDate}%')aa on p.advertPolicysId =aa.advertPolicysid\n " +
            " </script>")
    public List<AllDayPolicySub> dayPolicySubListfiledown(Map<String,Object> paras);



    // 保存当天的设备播放策略
    @Insert("upsert into ad.t_advert_day_policy_role(id,theDay,deviceId,roleContent,mediaFiles) values(" +
            "  next value for ad.t_advert_day_policy_role_seq, '${theDay}','${deviceId}','${roleContent}','${mediaFiles}')")
    public int insertAdvertDayPoliceRole(AdvertDayPolicyRole advertDayPolicyRole);

    //获取该设备当天策略
    @Select("select roleContent from  ad.t_advert_day_policy_role where theDay ='${theDay}' and deviceId='${deviceId}'")
    public List<Map<String,Object>> advertDayPolicyRoleList(Map<String,Object> paras);



    // 保存服务文件下载
    @Insert("upsert into ad.t_filehost_download_role(id,fileName,cityId,beginPlayTime,sended) values(" +
            " next value for ad.t_filehost_download_role_seq , '${fileName}','${cityId}','${beginPlayTime}', false)")
    public int insertFilehost(Map<String,Object> paras);

}
