package com.en.adback.controller.advertmgr;

import com.alibaba.fastjson.JSON;
import com.en.adback.common.Common;
import com.en.adback.common.MessageModel;
import com.en.adback.controller.MakeExcelCtrl;
import com.en.adback.controller.sys.UserLogs;
import com.en.adback.entity.Logs;
import com.en.adback.entity.advertmgr.AdvertDayPolicyRole;
import com.en.adback.entity.advertmgr.AdvertPutIn;
import com.en.adback.entity.calpolicy.DownDayPolicy;
import com.en.adback.entity.devicemgr.AllDevice;
import com.en.adback.service.advertmgr.IAdvertSendMgrService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value="广告下发管理",tags={"广告下发管理webapi接口"})
@RestController
@CrossOrigin
@RequestMapping(value = "/api/advertSendMgr", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json;charset=UTF-8")
public class AdvertSendMgrCtrl {
    @Autowired
    private IAdvertSendMgrService svr;
    @Autowired
    private UserLogs ulogs;

    @ApiOperation( value = "广告管理按条件查询",notes = "广告管理按条件查询" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "advertId", value = "广告编码", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "advertName", value = "广告名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "tradeId", value = "行业名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "blankId", value = "品牌", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "adCorpName", value = "广告公司名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "nowState", value = "广告状态", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "checkTime", value = "审核日期", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endCheckTime", value = "审核日期结束", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "当前页", required = true, dataType = "String", paramType = "query"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/advertSendList")
    public MessageModel advertSendList(String advertId, String advertName, String tradeId,String blankId,String adCorpName,
                                       String checkTime, String endCheckTime, String nowState,int pageSize, int pageNo,String loginUserId,
                                       String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel m= new MessageModel();
        Map<String,Object> map = new HashMap<String,Object>();
        //把截止日期加长一天
        if (endCheckTime !=null && !endCheckTime.equals("")) {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date d = new Date(f.parse(endCheckTime).getTime() + 24 * 3600 * 1000);
                endCheckTime = f.format(d);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }//
        }
        map.put("checkTime",checkTime);
        map.put("advertId",advertId);
        map.put("advertName",advertName);
        map.put("adCorpName",adCorpName);
        map.put("blankId",blankId);
        map.put("tradeId",tradeId);
        map.put("nowState",nowState == null ? 0 : nowState);
        map.put("endCheckTime",endCheckTime);
        map.put("pageBegin",(pageNo - 1) * pageSize);
        map.put("pageSize",pageSize);
        List<AdvertPutIn> list=svr.getAdvertSendList(map);
        map.clear();
        map.put("list",list);
        m.setData(map);
        m.setResultCode(list.size()>0?"1":"2");
        m.setResultMsg("success");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"查询广告管理");
        return m;
    }



    @ApiOperation( value = "广告管理查询总数",notes = "广告管理按条件查询总数" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "advertId", value = "广告编码", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "advertName", value = "广告名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "tradeId", value = "行业名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "blankId", value = "品牌ID", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "adCorpName", value = "广告公司名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "checkTime", value = "审核日期", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endCheckTime", value = "审核日期结束", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "nowState", value = "广告ID", required = false, dataType = "String", paramType = "query"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/advertSendListTotal")
    public MessageModel advertSendListTotal(String advertId, String advertName, String tradeId, String adCorpName,
                                            String checkTime,String endCheckTime,String blankId,int nowState)
    {
        MessageModel m= new MessageModel();
        Map<String,Object> map = new HashMap<String,Object>();
        //把截止日期加长一天
        if (endCheckTime !=null && !endCheckTime.equals("")) {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date d = new Date(f.parse(endCheckTime).getTime() + 24 * 3600 * 1000);
                endCheckTime = f.format(d);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }//
        }
        map.put("endCheckTime",endCheckTime);
        map.put("checkTime",checkTime);
        map.put("advertId",advertId);
        map.put("advertName",advertName);
        map.put("adCorpName",adCorpName);
        map.put("tradeId",tradeId);
        map.put("blankId",blankId);
        map.put("nowState",nowState);
        List<Map<String,Object>> list=svr.getAdvertSendListTotal(map);
        map.clear();
        map.put("total",list.get(0).get("TOTAL").toString());
        m.setData(map);
        m.setResultCode(list.size()>0?"1":"2");
        m.setResultMsg("success");
        return m;
    }

    @ApiOperation( value = "查询供下载的播放策略",notes = "根据条件查询可供下载的播放策略" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "theDay", value = "日期", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "deviceId", value = "设备id", required = true, dataType = "String", paramType = "query"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足"),
            @ApiResponse(code =4, message = "无该设备数据")})
    @GetMapping(value = "/deviceDayPolicy")
    public MessageModel deviceDayPolicy(String theDay, String deviceId)
    {
        MessageModel m= new MessageModel();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("theDay",theDay);
        map.put("deviceId",deviceId);
        DownDayPolicy pRole =svr.dayPolicyRole(map);
        if (pRole==null)
        {
            m.setResultCode("4");
            m.setResultMsg("no data");
        }else {
            m.setResultCode("1");
            m.setResultMsg("success");
        }
        m.setData(pRole);
//        String ip= Common.getIpAddr(request);
//        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"查询供下载的播放策略");
        return m;
    }

    //查询广告下发信息和相关屏幕信息
    @GetMapping(value = "/getAdvertSendDetail")
    public MessageModel getAdvertSendDetail(String advertId,String loginUserId,
                                            String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel model=new MessageModel();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("advertId",advertId);
        List<AdvertPutIn> list=svr.getAdvertSendDetail(map);
        map.clear();
        map.put("list",list);
        model.setData(map);
        model.setResultCode(list.size()>0?"1":"2");
        model.setResultMsg("success");

        String ip=Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"查询广告下发详情和关联屏幕信息");

        return model;
    }


    //将下发广告详情下载到excel
    @PostMapping(value = "/downloadExcel")
    public MessageModel downloadExcel(@RequestBody String data,HttpServletRequest request){
        MessageModel model=new MessageModel();
        Map<String,Object> map= JSON.parseObject(data,Map.class);
        MakeExcelCtrl mec=new MakeExcelCtrl();
        try {
            mec.writeAdvertPutinExcel(JSON.parseObject(map.get("data").toString(),Map.class));
            model.setData("ok");
        } catch (IOException e) {
            model.setData("error");
            e.printStackTrace();
        }
        String loginUserId = map.get("loginUserId").toString();
        String loginGroupRoleId = map.get("loginGroupRoleId").toString();
        String loginRoleId = map.get("loginRoleId").toString();
        String ip=Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"将下发广告详情下载到excel");
        return model;
    }



    @ApiOperation( value = "广告管理按条件查询",notes = "广告管理按条件查询" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "advertId", value = "广告编码", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "advertName", value = "广告名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "tradeId", value = "行业名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "blankId", value = "品牌", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "adCorpName", value = "广告公司名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "nowState", value = "广告状态", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "checkTime", value = "审核日期", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endCheckTime", value = "审核日期结束", required = false, dataType = "String", paramType = "query"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/getExcelAdvertSendMgr")
    public MessageModel getExcelAdvertSendMgr(String advertId, String advertName, String tradeId,String blankId,String adCorpName, String checkTime,
                                       String endCheckTime, String nowState,String loginUserId,
                                       String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel m= new MessageModel();
        Map<String,Object> map = new HashMap<String,Object>();
        //把截止日期加长一天
        if (endCheckTime !=null && !endCheckTime.equals("")) {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date d = new Date(f.parse(endCheckTime).getTime() + 24 * 3600 * 1000);
                endCheckTime = f.format(d);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }//
        }
        map.put("checkTime",checkTime);
        map.put("advertId",advertId);
        map.put("advertName",advertName);
        map.put("adCorpName",adCorpName);
        map.put("blankId",blankId);
        map.put("tradeId",tradeId);
        map.put("nowState",nowState == null ? 0 : nowState);
        map.put("endCheckTime",endCheckTime);
        List<AdvertPutIn> list=svr.getExcelAdvertSendMgr(map);
        AdvertCheckExcelCtrl acec = new AdvertCheckExcelCtrl();
        try {
            acec.writeAdvertSendMsgExcel(list);
            m.setData(1);
        } catch (IOException e) {
            m.setData(2);
            e.printStackTrace();
        }
        m.setResultCode(list.size()>0?"1":"2");
        m.setResultMsg("success");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"生成广告管理Excel");
        return m;
    }

    @ApiOperation( value = "广告管理查看修改历史",notes = "广告管理按条件查询" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "advertPolicyId", value = "广告策略编号", required = false, dataType = "String", paramType = "query")
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/getChangeHistory")
    public MessageModel getChangeHistory(String advertPolicyId){
        MessageModel m = new MessageModel();
        Map<String,Object> re = new HashMap<>();
        re.put("advertPolicyId",advertPolicyId);
        List<Logs> list = svr.getChangeHistory(re);
        re.clear();
        re.put("list",list);
        m.setData(re);
        m.setResultMsg("ok");
        m.setResultCode("1");
        return m;
    }




}
