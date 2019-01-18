package com.en.adback.controller.adreplace;

import com.en.adback.entity.adreplace.BusinessEnum;
import com.en.adback.entity.adreplace.ResponseModel;
import com.en.adback.serviceimp.adreplace.AdreplaceServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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



    @ApiOperation( value = "广告下载分发",notes = "广告下载分发api接口")
    @RequestMapping(value = "/dispatch",method = {RequestMethod.POST})
    public ResponseEntity<List<ResponseModel>> dispatch(String targetUrl,@RequestParam("fileList") List<String> fileList){

        List<ResponseModel> result = fileList.parallelStream().map(fileName -> adreplaceService.dispatch(targetUrl, fileName)).collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }


    @ApiOperation( value = "广告上传分发",notes = "广告上传分发api接口")
    @RequestMapping(value = "/upload", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<List<ResponseModel>> upLoad(String targetUrl, @RequestParam("fileList") List<String> fileList) throws IOException {
        List<ResponseModel> result = fileList.parallelStream().map(fileName -> adreplaceService.upLoad(targetUrl, fileName)).collect(Collectors.toList());
//        ResponseModel model = adreplaceService.upLoad(targetUrl, fileName);
        return ResponseEntity.ok(result);
    }


    @ApiOperation( value = "广告分发结果回调",notes = "广告分发结果回调api接口",hidden = true)
    @RequestMapping(value = "/callback",method = {RequestMethod.POST})
    public ResponseEntity dispatchCallback(@RequestParam("report") String jsonMessage){

        log.info("收到下载结果汇报：{}",jsonMessage);

        return ResponseEntity.ok(jsonMessage);
    }


    @ApiOperation( value = "广告下载接口",notes = "广告下载接口api接口",hidden = true)
    @RequestMapping(value = "/download", method = {RequestMethod.GET, RequestMethod.POST})
    public void downLoad(@RequestParam("fileName") String fileName, HttpServletResponse response) throws IOException {
        adreplaceService.downLoad(fileName,response);
    }


}
