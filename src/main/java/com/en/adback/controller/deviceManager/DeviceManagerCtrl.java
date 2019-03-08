package com.en.adback.controller.deviceManager;

import com.en.adback.common.MessageModel;
import com.en.adback.service.deviceManager.IPolicySendDeviceService;
import com.en.adback.serviceimp.DeviceCalServiceImpl;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

@Slf4j
@Api(value="策略分发到设备统计",tags={"策略分发到设备统计API接口"})
@RestController
@CrossOrigin
@RequestMapping(value = "/api/device", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json;charset=UTF-8")
public class DeviceManagerCtrl {

    @Autowired
    private IPolicySendDeviceService service;
    @RequestMapping(value = "/upsertHistory", method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseEntity upsertHistory(){

        service.upsertHistoryAsync(123);
        MessageModel m = new MessageModel();
        m.setResultMsg("异步调用，立即返回！");

        return ResponseEntity.ok().body(m);
    }


}
