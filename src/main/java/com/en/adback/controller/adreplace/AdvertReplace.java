package com.en.adback.controller.adreplace;

import com.en.adback.serviceimp.adreplace.AdreplaceServiceImpl;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Api(value="广告分发、下载、回调",tags={"广告分发、下载、回调webapi接口"})
@RestController
@RequestMapping(value = "/api/adreplace",method = {RequestMethod.GET,RequestMethod.POST})
public class AdvertReplace {


    @Autowired
    private AdreplaceServiceImpl adreplaceService;



    @RequestMapping(value = "/dispatch",method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseEntity dispatch(String targetUrl,List<String> fileList){

        List<String> result = fileList.stream().map(fileName -> adreplaceService.dispatch(targetUrl, fileName)).collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/callback",method = {RequestMethod.GET,RequestMethod.POST})
    public ResponseEntity dispatchCallback(String jsonMessage){

        log.info("收到下载结果汇报：",jsonMessage);
        return ResponseEntity.ok(jsonMessage);
    }


    @RequestMapping(value = "/download", method = {RequestMethod.GET, RequestMethod.POST})
    public void downLoad(@RequestParam("fileName") String fileName, HttpServletResponse response) throws IOException {
        adreplaceService.downLoad(fileName,response);
    }

    @RequestMapping(value = "/upload", method = {RequestMethod.GET, RequestMethod.POST})
    public String upLoad(String targetUrl, @RequestParam("fileName") String fileName) throws IOException {
        String result = adreplaceService.upLoad(targetUrl, fileName);
        return result;
    }
}
