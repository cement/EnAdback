package com.en.adback.controller;

import com.en.adback.common.Common;
import com.en.adback.common.MessageModel;
import com.en.adback.controller.sys.UserLogs;
import com.en.adback.entity.devicemgr.Device;
import com.en.adback.service.DeviceService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value="设备",tags={"设备api接口"})
@RestController
@CrossOrigin
@RequestMapping(value = "/device", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json;charset=UTF-8")

public class DeviceCtrl {

    @Autowired private DeviceService svr;
    @Autowired private UserLogs ulogs;

    @ApiOperation( value = "设备信息查询",notes = "设备信息按条件查询不分页" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceId", value = "设备id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "enterpriseName", value = "场所名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "address", value = "场所地址", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "cityId", value = "市州id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "areaId", value = "区县id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "minBeds", value = "床位数最小值", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "maxBeds", value = "床位数最大查询值", required = false, dataType = "String", paramType = "query"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/deviceAllList")
    public MessageModel deviceAllList(String deviceId,String enterpriseName,String address,String cityId,String areaId,String minBeds,String maxBeds,
                                      String loginUserId,String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel model=new MessageModel();
        Map<String,Object> re=new HashMap<String, Object>();
        re.put("deviceId",deviceId);
        re.put("enterpriseName",enterpriseName);
        re.put("address",address);
        re.put("cityId",cityId);
        re.put("areaId",areaId);
        re.put("minBeds",minBeds);
        re.put("maxBeds",maxBeds);
        List<Device> list=svr.getDeviceAllList(re);
        re.clear();
        re.put("list",list);
        model.setData(re);
        model.setResultCode(list.size()>0?"1":"2");
        model.setResultMsg("success");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"查询设备信息");
        return model;
    }

}
