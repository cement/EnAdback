package com.en.adback.controller.advertmgr;


import com.en.adback.common.MessageModel;
import com.en.adback.entity.dic.Blank;
import com.en.adback.service.advertmgr.IDevicePolicyService;
import com.en.adback.service.advertmgr.IFilePutinCityHostService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

// @Api(value="广告策略计算",tags={"广告策略计算webapi接口"})
@RestController
@CrossOrigin
@RequestMapping(value = "/api/policy", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json;charset=UTF-8")

public class AdvertPolicysCalOrderCtrl {
     @Autowired
     private IDevicePolicyService psvr;
     @Autowired
     private IFilePutinCityHostService fsvr;
    //计算设备策略
     @GetMapping(value="/calp")
    public MessageModel calPolicy(String theDate){
        MessageModel m= new MessageModel();
        System.out.print("ok1");
        String wrongmsg="ok";

            psvr.calDayPolicy(theDate);

        System.out.print("ok2");
        m.setResultCode("1");
        m.setResultMsg(wrongmsg);
        return m;
    }

     //计算每日下发文件
     @GetMapping(value="/calh")
    public MessageModel calHostDownload(String theDate){
        MessageModel m= new MessageModel();
        fsvr.calFilePutin(theDate);
        m.setResultCode("1");
        m.setResultMsg("ok");
        return m;
    }

    @GetMapping(value="/test")
    public MessageModel test() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        Date newTime = calendar.getTime();
        String theDate = df.format(newTime);
        //计算每日下发文件
        fsvr.calFilePutin(theDate);
        // 计算播放策略
        psvr.calDayPolicy(theDate);
        return null;
    }


    @GetMapping(value="/test1")
    public String test1() {
         Blank b = new Blank();
         b.setBlankId("001");
         b.setBlankName("safdew");
        Optional<Blank> blank=Optional.of(b);

        return null;
    }



}
