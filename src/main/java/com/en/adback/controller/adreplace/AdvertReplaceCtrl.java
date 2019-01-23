package com.en.adback.controller.adreplace;

import com.en.adback.entity.adreplace.BusinessEnum;
import com.en.adback.entity.adreplace.ResponseModel;
import com.en.adback.serviceimp.adreplace.AdreplaceServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author YSH
 * @created 20190122
 * @desc 替换广告分发、下载、回调接口
 */

@Slf4j
@Api(value="广告分发、下载、回调",tags={"广告分发、下载、回调webapi接口"})
@RestController
@RequestMapping(value = "/api/adreplace",method = {RequestMethod.GET,RequestMethod.POST})
public class AdvertReplaceCtrl {


    @Autowired
    private AdreplaceServiceImpl adreplaceService;



    @ApiOperation( value = "广告下载(同步)分发",notes = "广告下载分发api接口")
    @RequestMapping(value = "/dispatch",method = {RequestMethod.POST})
    public ResponseEntity<List<ResponseModel>> dispatch(String targetUrl,@RequestParam(value = "isAsync",required = false,defaultValue = "false")Boolean isAsync,@RequestParam("fileList") String... fileList){
        if (isAsync){
            Arrays.stream(fileList).parallel().forEach(fileName -> adreplaceService.dispatchAsync(targetUrl, fileName,isAsync));
            ResponseModel model = ResponseModel.warp(BusinessEnum.EXECUTING).setData(fileList);
            List<ResponseModel> collect = Stream.of(model).collect(Collectors.toList());
            return ResponseEntity.ok(collect);
        }else{
            List<ResponseModel> result = Arrays.stream(fileList).parallel().map(fileName -> adreplaceService.dispatch(targetUrl, fileName,isAsync)).collect(Collectors.toList());
           return ResponseEntity.ok(result);
        }
    }




    @ApiOperation( value = "广告上传(同步)分发",notes = "广告上传分发api接口")
    @RequestMapping(value = "/upload", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<List<ResponseModel>> upoad(String targetUrl, @RequestParam(value = "isAsync",required = false,defaultValue = "false")Boolean isAsync,@RequestParam("fileList") String... fileList) {
        if (isAsync){
            Arrays.stream(fileList).parallel().forEach(fileName -> adreplaceService.upLoadAsync(targetUrl, fileName,isAsync));
            ResponseModel model = ResponseModel.warp(BusinessEnum.EXECUTING).setData(fileList);
            List<ResponseModel> collect = Stream.of(model).collect(Collectors.toList());
            return ResponseEntity.ok(collect);
        }else{
            List<ResponseModel> result = Arrays.stream(fileList).parallel().map(fileName -> adreplaceService.upLoad(targetUrl, fileName,isAsync)).collect(Collectors.toList());
            return ResponseEntity.ok(result);
        }
    }


    @ApiOperation( value = "广告分发结果回调",notes = "广告分发结果回调api接口",hidden = true)
    @RequestMapping(value = "/callback",method = {RequestMethod.POST})
    public void asyncCallback(@RequestParam("report") String callbackJson){
          adreplaceService.asyncCallback(callbackJson);
    }


    @ApiOperation( value = "广告下载接口,测试使用",notes = "广告下载接口api接口",hidden = true)
    @RequestMapping(value = "/download/{fileName:.+}", method = {RequestMethod.GET, RequestMethod.POST})
    public void downLoad(@PathVariable("fileName") String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
         adreplaceService.downLoad(fileName,response);
    }







}
