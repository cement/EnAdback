package com.en.adback.controller.analisys;

import com.alibaba.fastjson.JSON;
import com.en.adback.common.Common;
import com.en.adback.common.MessageModel;
import com.en.adback.controller.MakeExcelCtrl;
import com.en.adback.controller.sys.UserLogs;
import com.en.adback.entity.AdvertAudience;
import com.en.adback.entity.AdvertCount;
import com.en.adback.entity.advertmgr.AdvertDayPolicyRole;
import com.en.adback.entity.advertmgr.AdvertPutIn;
import com.en.adback.service.analisys.AdvertCountService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Api(value="广告统计查询",tags={"广告统计查询webapi 接口"})
@RestController
@CrossOrigin
@RequestMapping(value = "/api/advertCount", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")

public class AdvertCountCtrl {

    @Autowired private AdvertCountService svr;
    @Autowired private UserLogs ulogs;

    @ApiOperation( value = "一年投放广告客户量",notes = "按条件查询一年投放广告客户量" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年份", required = false, dataType = "String", paramType = "query"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/getAdcorpAndBlankCount")
    public MessageModel getAdcorpAndBlankCount(String customerYear,String loginUserId,
                                               String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel model=new MessageModel();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("year",customerYear);
        List<AdvertCount> customerlist=svr.getAdcorpAndBlankCount(map);
        map.clear();
        map.put("list",customerlist);
        model.setData(map);
        model.setResultCode(customerlist.size()>0?"1":"2");
        model.setResultMsg("success");
        String ip = Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"查询一年广告投放客户量");
        return model;
    }

    @ApiOperation( value = "行业投放广告数量",notes = "按条件查询行业投放广告数量" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "endTime", value = "截止时间", required = false, dataType = "String", paramType = "query"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/getTradeCount")
    public MessageModel getTradeCount(String beginTime,String endTime,String loginUserId,
                                      String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel model=new MessageModel();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("endTime",endTime);
        map.put("beginTime",beginTime);
        List<AdvertCount> list=svr.getTradeCount(map);
        map.clear();
        map.put("list",list);
        model.setData(map);
        model.setResultCode(list.size()>0?"1":"2");
        model.setResultMsg("success");
        String ip = Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"查询行业广告投放数量");
        return model;
    }

    @ApiOperation( value = "品牌投放广告数量",notes = "按条件查询品牌投放广告数量" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "endTime", value = "截止时间", required = false, dataType = "String", paramType = "query"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/getBlankCount")
    public MessageModel getBlankCount(String beginTime,String endTime,String loginUserId,
                                      String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel model=new MessageModel();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("beginTime",beginTime);
        map.put("endTime",endTime);
        List<AdvertCount> list=svr.getBlankCount(map);
        map.clear();
        map.put("list",list);
        model.setData(map);
        model.setResultCode(list.size()>0?"1":"2");
        model.setResultMsg("success");
        String ip = Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"查询品牌广告投放数量");
        return model;
    }

    @ApiOperation( value = "每年各策略投放广告数量",notes = "按条件查询每年各策略投放广告数量" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年份", required = false, dataType = "String", paramType = "query"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/getPolicyCount")
    public MessageModel getPolicyCount(String policyYear,String loginUserId,
                                       String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel model=new MessageModel();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("year",policyYear);
        List<AdvertCount> list=svr.getPolicyCount(map);
        map.clear();
        map.put("list",list);
        model.setData(map);
        model.setResultCode(list.size()>0?"1":"2");
        model.setResultMsg("success");
        String ip = Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"查询每年各策略投放广告数量");
        return model;
    }

    @ApiOperation( value = "各区县投放广告数量",notes = "按条件查询各区县投放广告数量" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "endTime", value = "截止时间", required = false, dataType = "String", paramType = "query"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/getQuxianCount")
    public MessageModel getQuxianCount(String beginTime,String endTime,String loginUserId,
                                       String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel model=new MessageModel();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("beginTime",beginTime);
        map.put("endTime",endTime);
        List<AdvertCount> list=svr.getQuxianCount(map);
        map.clear();
        map.put("list",list);
        model.setData(map);
        model.setResultCode(list.size()>0?"1":"2");
        model.setResultMsg("success");
        String ip = Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"查询各区县投放广告数量");
        return model;
    }

    @ApiOperation( value = "广告统计查询对应列表",notes = "按条件查询广告统计查询对应列表" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "advertId", value = "广告id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "advertName", value = "广告名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "tradeId", value = "行业id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "blankId", value = "品牌id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "adCorpName", value = "广告公司名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "截止时间", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "页码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页行数", required = true, dataType = "String", paramType = "query"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/advertCountList")
    public MessageModel advertCountList(String advertId,String advertName,String tradeId,String blankId,String adCorpName,
                                        String beginTime,String endTime,int pageNo,int pageSize,
                                        String loginUserId,String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel model=new MessageModel();
        Map<String,Object> map = new HashMap<String,Object>();
        //把截止日期加长一天
        if (endTime !=null && !endTime.equals("")) {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date d = new Date(f.parse(endTime).getTime() + 24 * 3600 * 1000);
                endTime = f.format(d);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        map.put("advertId",advertId);
        map.put("advertName",advertName);
        map.put("tradeId",tradeId);
        map.put("blankId",blankId);
        map.put("adCorpName",adCorpName);
        map.put("beginTime",beginTime);
        map.put("endTime",endTime);
        map.put("pageBegin",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        List<AdvertPutIn> list=svr.getAdvertCountList(map);
        map.clear();
        map.put("list",list);
        model.setData(map);
        model.setResultCode(list.size()>0?"1":"2");
        model.setResultMsg("success");

        String ip=Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"按条件查询广告统计查询对应列表");

        return model;
    }

    @ApiOperation( value = "广告统计查询对应列表总数",notes = "按条件查询广告统计查询对应列表总数" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "advertId", value = "广告id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "advertName", value = "广告名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "tradeId", value = "行业id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "blankId", value = "品牌id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "adCorpName", value = "广告公司名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "截止时间", required = false, dataType = "String", paramType = "query"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/advertCountListTotal")
    public MessageModel advertCountListTotal(String advertId,String advertName,String tradeId,String blankId,String adCorpName,
                                        String beginTime,String endTime,
                                        String loginUserId,String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel model=new MessageModel();
        Map<String,Object> map = new HashMap<String,Object>();
        //把截止日期加长一天
        if (endTime !=null && !endTime.equals("")) {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date d = new Date(f.parse(endTime).getTime() + 24 * 3600 * 1000);
                endTime = f.format(d);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        map.put("advertId",advertId);
        map.put("advertName",advertName);
        map.put("tradeId",tradeId);
        map.put("blankId",blankId);
        map.put("adCorpName",adCorpName);
        map.put("beginTime",beginTime);
        map.put("endTime",endTime);
        String total=svr.getAdvertCountListTotal(map);
        map.clear();
        map.put("total",total);
        model.setData(map);
        model.setResultCode("1");
        model.setResultMsg("success");
        String ip=Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"按条件查询广告统计查询对应列表总数");
        return model;
    }

    //广告统计信息生成列表 并且下载
    @GetMapping(value = "/downloadExcel")
    public MessageModel downloadExcel(String advertId,String advertName,String tradeId,String blankId,String adCorpName,
                                      String beginTime,String endTime,int pageNo,int pageSize,
                                      String loginUserId,String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel model=new MessageModel();
        Map<String,Object> map = new HashMap<String,Object>();
        //把截止日期加长一天
        if (endTime !=null && !endTime.equals("")) {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date d = new Date(f.parse(endTime).getTime() + 24 * 3600 * 1000);
                endTime = f.format(d);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        map.put("advertId",advertId);
        map.put("advertName",advertName);
        map.put("tradeId",tradeId);
        map.put("blankId",blankId);
        map.put("adCorpName",adCorpName);
        map.put("beginTime",beginTime);
        map.put("endTime",endTime);
        map.put("pageBegin",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        List<AdvertPutIn> list=svr.getAdvertCountList(map);
        String title[]=new String[]{"广告编号","广告名称","公司全称","所属品牌","行业","广告格式","广告时长","文件大小","审核时间","审核人","广告状态"};
        MakeExcelCtrl mec=new MakeExcelCtrl();
        mec.writeAdvertCountQueryExcel(list,title);
        model.setData("excels/advert.xls");
        model.setResultCode("1");
        model.setResultMsg("success");
        String ip=Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"广告统计信息下载excel");
        return model;
    }


    //受众人群信息列表 导出表格
    @PostMapping(value = "/audienceDownload")
    public MessageModel audienceDownload(@RequestBody Map<String,Object> map,HttpServletRequest request){
        MessageModel model=new MessageModel();
        List<LinkedHashMap<String,Object>> list= (List<LinkedHashMap<String, Object>>) map.get("data");
        String titile[]=new String[]{"设备编号","性别","年龄","籍贯","投放时间"};
        MakeExcelCtrl mec=new MakeExcelCtrl();
        mec.writeAudienceExcel(list,titile);
        model.setData("excels/advertAudience.xls");
        String loginUserId = map.get("loginUserId").toString();
        String loginGroupRoleId = map.get("loginGroupRoleId").toString();
        String loginRoleId = map.get("loginRoleId").toString();
        String ip=Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"受众人群信息下载到excel");

        return model;
    }


}
