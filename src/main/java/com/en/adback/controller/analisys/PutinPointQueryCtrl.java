package com.en.adback.controller.analisys;

import com.en.adback.common.Common;
import com.en.adback.common.MessageModel;
import com.en.adback.controller.sys.UserLogs;
import com.en.adback.service.analisys.PutinPointQueryService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value="投放点位查询",tags={"投放点位查询webapi接口"})
@RestController
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

}
