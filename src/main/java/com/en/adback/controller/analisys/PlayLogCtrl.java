package com.en.adback.controller.analisys;

import com.en.adback.common.MessageModel;
import com.en.adback.entity.PlayLog;
import com.en.adback.service.analisys.PlayLogService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value="播放日志统计",tags={"播放日志统计webapi接口"})
@RestController
@RequestMapping(value = "/api/playlog", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
public class PlayLogCtrl {

    @Autowired
    private PlayLogService svr;

    @ApiOperation( value = "播放日志统计查询",notes = "播放日志统计查询" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "beginDate", value = "开始日期", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束日期", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beginPlayTime", value = "开始时间", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endPlayTime", value = "结束时间", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "advertId", value = "广告ID", required = true, dataType = "String", paramType = "query"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/getPlayLlogList")
    public MessageModel getPlayLlogList(String beginDate,String endDate, String beginPlayTime, String endPlayTime, String advertId) throws ParseException {
        MessageModel model=new MessageModel();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("beginDate",beginDate);
        map.put("endDate",endDate);
        map.put("beginPlayTime",beginPlayTime);
        map.put("endPlayTime",endPlayTime);
        map.put("advertId",advertId);
        List<PlayLog> list = svr.getPlayLlogList(map);
        map.put("list",list);
        model.setData(map);
        model.setResultCode(list.size()>0?"1":"2");
        model.setResultMsg("success");
        return model;
    }
}
