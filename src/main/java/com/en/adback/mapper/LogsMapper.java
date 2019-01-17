package com.en.adback.mapper;

import com.en.adback.entity.Logs;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface LogsMapper {


    @Insert("upsert into ad.t_logs(id,userId,groupRoleId,roleId,ip,logContent,actionTime)" +
            " values(next value for ad.t_logs_seq,'${userId}','${groupRoleId}','${roleId}','${ip}','${logContent}',CONVERT_TZ(CURRENT_DATE(), 'UTC', 'Asia/Shanghai'))")
    public void insertLogs(Logs log);

    @Select("<script>" +
            "select userId,l.groupRoleId groupRoleId,gr.groupRoleName groupRoleName,l.roleId roleId,r.roleName roleName,ip,logContent,TO_CHAR(actionTime,'yyyy-MM-dd HH:mm:ss') as actionTime from ad.t_logs l\n" +
            "left join ad.t_groupRole gr on gr.groupRoleId=l.groupRoleId\n" +
            "left join ad.t_role r on r.roleId=l.roleId\n" +
            "where 1=1 \n" +
            "<if test='userId!=null and userId!=\"\" '>" +
            "<![CDATA[ and userId like '%${userId}%' ]]>" +
            "</if>" +
            "<if test='groupRoleId!=null and groupRoleId!=\"\" and groupRoleId!=\"0\" '>" +
            " and l.groupRoleId='${groupRoleId}' " +
            "</if>" +
            "<if test='roleId!=null and roleId!=\"\" and roleId!=\"0\" '>" +
            " and l.roleid='${roleId}' " +
            "</if>" +
            "<if test='beginTime!=null and beginTime!=\"\" and endTime!=null and endTime!=\"\" '>" +
            " and actionTime between to_date('${beginTime} 08','yyyy-MM-dd hh') and to_date('${endTime} 08','yyyy-MM-dd hh') " +
            "</if>" +
            " order by l.actionTime desc limit ${pageSize} offset ${pageBegin} " +
            "</script>")
    public List<Logs> getLogsList(Map<String, Object> map);


    @Select("<script>" +
            "select count(*) as total from ad.t_logs l\n" +
            "left join ad.t_groupRole gr on gr.groupRoleId=l.groupRoleId\n" +
            "left join ad.t_role r on r.roleId=l.roleId\n" +
            "where 1=1 \n" +
            "<if test='userId!=null and userId!=\"\" '>" +
            "<![CDATA[ and userId like '%${userId}%' ]]>" +
            "</if>" +
            "<if test='groupRoleId!=null and groupRoleId!=\"\" and groupRoleId!=\"0\" '>" +
            " and l.groupRoleId='${groupRoleId}' " +
            "</if>" +
            "<if test='roleId!=null and roleId!=\"\" and roleId!=\"0\" '>" +
            " and l.roleid='${roleId}' " +
            "</if>" +
            "<if test='beginTime!=null and beginTime!=\"\" and endTime!=null and endTime!=\"\" '>" +
            " and actionTime between to_date('${beginTime} 08','yyyy-MM-dd hh') and to_date('${endTime} 08','yyyy-MM-dd hh') " +
            "</if>" +
            "</script>")
    public List<Map<String, Object>> getLogsListTotal(Map<String, Object> map);
}
