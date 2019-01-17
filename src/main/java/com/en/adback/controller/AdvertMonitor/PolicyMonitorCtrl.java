package com.en.adback.controller.AdvertMonitor;

import com.en.adback.common.Common;
import com.en.adback.common.MessageModel;
import com.en.adback.controller.sys.UserLogs;
import com.en.adback.entity.advertmgr.AdvertDayPolicyRole;
import com.en.adback.service.advertMonitor.PolicyMonitorService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value="广告预警",tags={"广告预警webapi接口"})
@RestController
@CrossOrigin
@RequestMapping(value = "/api/advertMonitor", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json;charset=UTF-8")
public class PolicyMonitorCtrl {

    @Autowired private PolicyMonitorService svr;
    @Autowired private UserLogs ulogs;

    @GetMapping(value = "/getPolicyMonList")
    public MessageModel getPolicyMonList(String putinBeginTime,String loginUserId,
                                         String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel model=new MessageModel();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("putinBeginTime",putinBeginTime);
        List<AdvertDayPolicyRole> list=svr.getPolicyMonList(map);
        map.clear();
        map.put("list",list);
        model.setData(map);
        model.setResultCode(list.size()>0?"1":"2");
        model.setResultMsg("success");
        String ip = Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"查询广告预警");
        return model;
    }
}


