package com.en.adback.controller.dic;

import com.en.adback.common.Common;
import com.en.adback.common.MessageModel;
import com.en.adback.controller.sys.UserLogs;
import com.en.adback.entity.dic.Trade;
import com.en.adback.service.dic.ITradeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/7.
 */
@Api(value="行业管理",tags={"行业管理webapi 接口"})
@RestController
@CrossOrigin
@RequestMapping(value = "/api/trade", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
public class TradeCtrl {

    @Autowired private ITradeService svr;
    @Autowired private UserLogs ulogs;
    @Autowired private TradeLogs logs;

    @ApiOperation( value = "插入行业信息",notes = "" +
            " 返回字段：{" +
            "   data : null ,   " +
            "    resultCode," +
            "    resultMsg : 'ok' ：成功 ，否则返回错误信息" +
            "}")
    @ApiImplicitParams({
    }
    )
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @PostMapping(value = "/insertTrade")
    public synchronized MessageModel insertTrade(@RequestBody Trade trade,HttpServletRequest request){
        MessageModel m = new MessageModel();
        //取出数据库中tradeId最大值，如为空  则赋值T0001 如果不为空  则数值加一
        String tradeId = svr.getTradeMaxTradeId();
        if(null==tradeId){
            tradeId = "T0001";
        }else{
            tradeId = tradeId.substring(1);
            int id = Integer.parseInt(tradeId);
            id = id + 1;
            int length = String.valueOf(id).length();
            tradeId = "T";
            if(length<4){
                for(int i =0;i<4-length;i++){
                    tradeId += "0";
                }
            }
            tradeId = tradeId + id;
        }
        trade.setTradeId(tradeId);
        int i = svr.insertTrade(trade);
        m.setData(i);
        m.setResultCode("1");
        m.setResultMsg("ok");
        String ip = Common.getIpAddr(request);
        logs.postTradeLogs(trade,ip,"新增行业信息");
        return m;
    }


    @ApiOperation( value = "条件查询行业信息",notes = "" +
            " 返回字段：{" +
            "   data : null ,   " +
            "    resultCode," +
            "    resultMsg : 'ok' ：成功 ，否则返回错误信息" +
            "}")
    @ApiImplicitParams({
    }
    )
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/getTradeList")
    public MessageModel getTradeList(String tradeId,String tradeName,int pageNo,int pageSize,String loginUserId,
                                     String loginGroupRoleId,String loginRoleId,HttpServletRequest request ){
        MessageModel m = new MessageModel();
        Map<String,Object> re = new HashMap<>();
        re.put("tradeId",tradeId);
        re.put("tradeName",tradeName);
        re.put("pageBegin",(pageNo-1)*pageSize);
        re.put("pageSize",pageSize);
        List<Trade> list = svr.getTradeList(re);
        re.clear();
        re.put("list",list);
        m.setData(re);
        m.setResultCode(list.size()>0?"1":"2");
        m.setResultMsg("ok");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"查询行业信息");
        return m;
    }

    //查询总数
    @GetMapping(value = "/getTradeListTotal")
    public MessageModel getTradeListTotal(String tradeId,String tradeName){
        MessageModel m = new MessageModel();
        Map<String,Object> re = new HashMap<>();
        re.put("tradeId",tradeId);
        re.put("tradeName",tradeName);
        int total = svr.getTradeListTotal(re);
        m.setData(total);
        m.setResultCode(total>0?"1":"2");
        m.setResultMsg("ok");
        return m;
    }

    @ApiOperation( value = "更新行业信息",notes = "" +
            " 返回字段：{" +
            "   data : null ,   " +
            "    resultCode," +
            "    resultMsg : 'ok' ：成功 ，否则返回错误信息" +
            "}")
    @ApiImplicitParams({
    }
    )
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @PostMapping(value = "/upsertTrade")
    public MessageModel upsertTrade(@RequestBody Trade trade,HttpServletRequest request){
        MessageModel m = new MessageModel();
        int i = svr.upsertTrade(trade);
        m.setData(i);
        m.setResultCode("1");
        m.setResultMsg("ok");
        String ip = Common.getIpAddr(request);
        logs.postTradeLogs(trade,ip,"修改行业信息");
        return m;
    }

    @ApiOperation( value = "删除行业信息",notes = "" +
            " 返回字段：{" +
            "   data : null ,   " +
            "    resultCode," +
            "    resultMsg : 'ok' ：成功 ，否则返回错误信息" +
            "}")
    @ApiImplicitParams({
    }
    )
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/deteleTrade")
    public MessageModel deteleTrade(String tradeId,String loginUserId,
                                    String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
         MessageModel m = new MessageModel();
        Map<String,Object> re = new HashMap<>();
        re.put("tradeId",tradeId);
         int i = svr.deteleTrade(re);
        m.setData(i);
        m.setResultCode("1");
        m.setResultMsg("ok");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"删除行业信息");
        return m;
    }
}
