package com.en.adback.controller.AdvertMonitor;

import com.en.adback.common.Common;
import com.en.adback.common.MessageModel;
import com.en.adback.controller.sys.UserLogs;
import com.en.adback.entity.advertmgr.FilehostDownloadRole;
import com.en.adback.service.advertMonitor.CityHostFileMonService;
import com.en.adback.serviceimp.advertmgr.FilePutinCityHostServiceImp;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value="市州服务器预警",tags={"市州服务器预警webapi接口"})
@RestController
@CrossOrigin
@RequestMapping(value = "/api/cityHostMonitor", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json;charset=UTF-8")
public class CityHostFileMonCtrl {

    @Autowired private CityHostFileMonService svr;
    @Autowired private UserLogs ulogs;

    @GetMapping(value = "/cityHostFileList")
    public MessageModel  cityHostFileList(String putinBeginTime,String loginUserId,
                                          String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel messageModel=new MessageModel();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("putinBeginTime",putinBeginTime);
        List<FilehostDownloadRole> list=svr.cityHostFileList(map);
        map.clear();
        map.put("list",list);
        messageModel.setData(map);
        messageModel.setResultCode(list.size()>0?"1":"2");
        messageModel.setResultMsg("success");
        String ip = Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"查询城市服务器预警");
        return messageModel;
    }
}
