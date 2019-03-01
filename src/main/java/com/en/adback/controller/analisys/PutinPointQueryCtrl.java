package com.en.adback.controller.analisys;

import com.alibaba.fastjson.JSON;
import com.en.adback.common.Common;
import com.en.adback.common.MessageModel;
import com.en.adback.controller.MakeExcelCtrl;
import com.en.adback.controller.sys.UserLogs;
import com.en.adback.service.analisys.PutinPointQueryService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value="投放点位查询",tags={"投放点位查询webapi接口"})
@RestController
@CrossOrigin
@RequestMapping(value = "/api/putinpointquery", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
public class PutinPointQueryCtrl {
    @Autowired
    private PutinPointQueryService svr;
    @Autowired
    private UserLogs ulogs;

    @ApiOperation( value = "投放点位查询",notes = "投放点位查询" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "beginDate", value = "开始日期", required = true, dataType = "String", paramType = "query"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/point")
    public MessageModel putInPointQuery(String beginDate,String endDate ,String deviceIds,String loginUserId,
                                           String loginGroupRoleId,String loginRoleId,HttpServletRequest request
    ){
        MessageModel model=new MessageModel();
        Map<String,Object> map=new HashMap<String, Object>();
        List<Map<String,Object>> list = svr.putinPointQueryList(beginDate,endDate,deviceIds);
        map.put("list",list);
        model.setData(map);
        model.setResultCode(list.size()>0?"1":"2");
        model.setResultMsg("success");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"投放点位统计查询");
        return model;
    }

    /**
     * 播放点位统计生成表格
     * @param data
     * @return
     */

    @PostMapping(value = "/downloadExcel")
    public MessageModel downloadExcel(@RequestBody String data,HttpServletRequest request){
        MessageModel model=new MessageModel();
        Map<String,Object> map= JSON.parseObject(data,Map.class);
        List<Map<String,Object>> list= JSON.parseObject(map.get("data").toString(),List.class);
        List<Map<String,Object>> list1= (List<Map<String, Object>>) list.get(0).get("putinList");
        //生成表格
        MakeExcelCtrl mec=new MakeExcelCtrl();
        mec.writePutinQueryExcel(list1);
        model.setData("excels/putinPoint.xls");
        model.setResultCode("1");
        model.setResultMsg("success");
        String loginUserId = map.get("loginUserId").toString();
        String loginGroupRoleId = map.get("loginGroupRoleId").toString();
        String loginRoleId = map.get("loginRoleId").toString();
        String ip=Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"将下发广告详情下载到excel");

        return model;
    }

}
