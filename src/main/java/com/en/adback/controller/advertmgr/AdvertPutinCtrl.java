package com.en.adback.controller.advertmgr;


import com.en.adback.common.Common;
import com.en.adback.common.MessageModel;
import com.en.adback.controller.sys.UserLogs;
import com.en.adback.entity.advertmgr.AdvertPolicys;
import com.en.adback.entity.advertmgr.AdvertPutIn;
import com.en.adback.entity.advertmgr.TableSubAdvertPolicys;
import com.en.adback.mapper.advertmgr.AdvertPolicyMapper;
import com.en.adback.service.advertmgr.IAdvertPolicyService;
import com.en.adback.service.advertmgr.IAdvertPutinService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value="广告发布",tags={"广告发布webapi接口"})
@RestController
@CrossOrigin
@RequestMapping(value = "/api/advertPutin", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json;charset=UTF-8")
public class AdvertPutinCtrl {
    @Autowired
    private IAdvertPutinService svr;
    @Autowired
    private AdvertPolicyMapper policyMapper;
    @Autowired
    private UserLogs ulogs;

    @ApiOperation( value = "广告发布按条件查询",notes = "广告按条件查询" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "advertId", value = "广告编码", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "advertName", value = "广告名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "adCorpName", value = "广告公司名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "blankId", value = "品牌id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "nowState", value = "状态id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "checkTimeBegin", value = "审核日期起", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "checkTimeEnd", value = "审核日期止", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "当前页", required = true, dataType = "String", paramType = "query"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/advertPutinList")
    public MessageModel advertPutinList(String advertId,String advertName, String adCorpName, String blankId ,String tradeId,int nowState,
                                        String checkTimeBegin, String checkTimeEnd, String pageSize, String pageNo,String loginUserId,
                                        String loginGroupRoleId,String loginRoleId,HttpServletRequest request)
    {
        MessageModel m= new MessageModel();
        Map<String,Object> re = new HashMap<String,Object>();

        //把截止日期加长一天
        if (checkTimeEnd !=null && !checkTimeEnd.equals("")) {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date d = new Date(f.parse(checkTimeEnd).getTime() + 24 * 3600 * 1000);
                checkTimeEnd = f.format(d);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        re.put("checkTimeBegin",checkTimeBegin);
        re.put("checkTimeEnd",checkTimeEnd);
        re.put("advertId",advertId);
        re.put("advertName",advertName);
        re.put("adCorpName",adCorpName);
        re.put("blankId",blankId);
        re.put("tradeId",tradeId);
        re.put("nowState",nowState);
        re.put("pageBegin",(Integer.parseInt(pageNo) - 1) * Integer.parseInt(pageSize));
        re.put("pageSize",pageSize);
        List<AdvertPutIn> list=svr.advertPutInList(re);
        re.clear();
        re.put("list",list);
        m.setData(re);
        m.setResultCode(list.size()>0?"1":"2");
        m.setResultMsg("success");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"广告发布查询");
        return m;
    }



    @ApiOperation( value = "广告发布按条件查询总数",notes = "广告发布按条件查询总数" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "advertId", value = "广告编码", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "advertName", value = "广告名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "adCorpName", value = "广告公司名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "blankId", value = "品牌id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "nowState", value = "广告状态id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "checkTimeBegin", value = "审核日期起", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "checkTimeEnd", value = "审核日期止", required = false, dataType = "String", paramType = "query"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/advertPutinListTotal")
    public MessageModel advertPutinListTotal(String advertId,String advertName, String adCorpName, String blankId ,String tradeId,
                                             int nowState,String checkTimeBegin, String checkTimeEnd)
    {
        MessageModel m= new MessageModel();
        Map<String,Object> re = new HashMap<String,Object>();

        //把截止日期加长一天
        if (checkTimeEnd !=null && !checkTimeEnd.equals("")) {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date d = new Date(f.parse(checkTimeEnd).getTime() + 24 * 3600 * 1000);
                checkTimeEnd = f.format(d);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        re.put("checkTimebegin",checkTimeBegin);
        re.put("checkTimeEnd",checkTimeEnd);
        re.put("advertId",advertId);
        re.put("advertName",advertName);
        re.put("adCorpName",adCorpName);
        re.put("blankId",blankId);
        re.put("tradeId",tradeId);
        re.put("nowState",nowState);
        List<Map<String,Object>> list= svr.advertPutInListTotal(re);
        re.clear();
        re.put("total",list.get(0).get("TOTAL").toString());
        m.setData(re);
        m.setResultCode( list.size()>0 ? "1" :"2");
        m.setResultMsg("ok");
        return m;
    }

    @ApiOperation( value = "广告下发",notes = "广告下发" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "advertId", value = "广告编码", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "maker", value = "操作人账号", required = false, dataType = "String", paramType = "query"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/advertSendDown")
    public MessageModel advertSendDown(String advertPolicyId,String maker,String loginUserId,
                                       String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel m=new MessageModel();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("advertPolicysId",advertPolicyId);
        // 查询该策略下所有广告
        List<TableSubAdvertPolicys> list=  policyMapper.subAdvertPolicysList(map);
        if (list.size()>0)
        {
             for (TableSubAdvertPolicys ts : list)
             {
                 map.put("advertId",ts.getAdvertId());
                 map.put("maker",maker);
                 svr.advertSendDown(map);
                 svr.addAdvertHis(map);
             }

            m.setResultCode("1");
            m.setResultMsg("success");
        }else {
            m.setResultCode("2");
            m.setResultMsg("fail");
        }
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"广告下发");
        return m;
    }


    @ApiOperation( value = "广告发布按条件查询",notes = "广告按条件查询" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "advertId", value = "广告编码", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "advertName", value = "广告名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "adCorpName", value = "广告公司名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "blankId", value = "品牌id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "nowState", value = "状态id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "checkTimeBegin", value = "审核日期起", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "checkTimeEnd", value = "审核日期止", required = false, dataType = "String", paramType = "query"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/getAdvertPutinExcel")
    public MessageModel getAdvertPutinExcel(String advertId,String advertName, String adCorpName, String blankId ,String tradeId,int nowState,
                                        String checkTimeBegin, String checkTimeEnd,String loginUserId,
                                        String loginGroupRoleId,String loginRoleId,HttpServletRequest request)
    {
        MessageModel m= new MessageModel();
        Map<String,Object> re = new HashMap<String,Object>();

        //把截止日期加长一天
        if (checkTimeEnd !=null && !checkTimeEnd.equals("")) {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date d = new Date(f.parse(checkTimeEnd).getTime() + 24 * 3600 * 1000);
                checkTimeEnd = f.format(d);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        re.put("checkTimeBegin",checkTimeBegin);
        re.put("checkTimeEnd",checkTimeEnd);
        re.put("advertId",advertId);
        re.put("advertName",advertName);
        re.put("adCorpName",adCorpName);
        re.put("blankId",blankId);
        re.put("tradeId",tradeId);
        re.put("nowState",nowState);
        List<AdvertPutIn> list=svr.getAdvertPutInExcel(re);
        AdvertCheckExcelCtrl acec = new AdvertCheckExcelCtrl();
        try {
            acec.writeAdvertPutinExcel(list);
            m.setData(1);
        } catch (IOException e) {
            m.setData(2);
            e.printStackTrace();
        }
        m.setResultCode(list.size()>0?"1":"2");
        m.setResultMsg("success");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"广告发布查询Excel导出");
        return m;
    }

}