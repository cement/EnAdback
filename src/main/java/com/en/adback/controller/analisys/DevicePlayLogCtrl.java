package com.en.adback.controller.analisys;

// 设备播放日志报表

import com.en.adback.common.MessageModel;
import com.en.adback.entity.calpolicy.DownDayPolicy;
import com.en.adback.service.advertmgr.IAdvertSendMgrService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(value="广告统计查询",tags={"广告统计查询webapi 接口"})
@RestController
@CrossOrigin
@RequestMapping(value = "/api/analisys", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
public class DevicePlayLogCtrl {
    @Autowired
    private IAdvertSendMgrService svr;

    @ApiOperation( value = "设备播放日志",notes = "根据条件查询可供下载的播放策略" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "theDay", value = "日期", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "deviceId", value = "设备id", required = true, dataType = "String", paramType = "query"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足"),
            @ApiResponse(code =4, message = "无该设备数据")})
    @GetMapping(value = "/devicePlayLog")
    public MessageModel devicePlayLog(String beginDate ,String endDate,String  deviceId,String beginPlayTime ,String endPlayTime )
    {
        MessageModel m= new MessageModel();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("beginDate",beginDate);
        map.put("endDate",endDate);
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

        return m;
    }













}
