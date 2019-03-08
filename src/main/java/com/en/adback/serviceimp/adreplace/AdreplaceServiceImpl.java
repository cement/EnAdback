package com.en.adback.serviceimp.adreplace;

import com.alibaba.fastjson.JSON;
import com.en.adback.entity.adreplace.BusinessEnum;
import com.en.adback.entity.adreplace.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author ysh
 * @created 20190122
 * @desc 文件上传下发的实现
 */
@Slf4j
@Service
public class AdreplaceServiceImpl {

    @Value("${advertfiles}")
    private String advertDir;


    @Autowired
    private RestTemplate restTemplate;

    /*提供从本服务器下载功能*/
    public void downLoad(String fileName, HttpServletResponse response) throws IOException {
        Path path = Paths.get(advertDir, fileName);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "iso-8859-1"));
        long readed = Files.copy(path, response.getOutputStream());

    }

    /*从本服务器上传到市州服务器*/
    public ResponseModel upLoad(String targetUrl, String fileName,Boolean isAsync) {

        log.info("开始上传：{}",fileName);
        Path filePath = Paths.get(advertDir, fileName);
        if (StringUtils.isEmpty(fileName) || Files.notExists(filePath)){
            ResponseModel model = ResponseModel.warp(BusinessEnum.UNEXIST).setData(fileName);
            return model;
        }
        Boolean isexist = restTemplate.getForObject(targetUrl + "/isexist?fileName="+fileName, Boolean.class);
        if (isexist){
            ResponseModel model = ResponseModel.warp(BusinessEnum.EXISTED).setData(fileName);
            return model;
        }
        FileSystemResource resource = new FileSystemResource(filePath);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("file", resource);
        param.add("fileName", fileName);
        param.add("isAsync", isAsync);
        ResponseModel model = restTemplate.postForObject(targetUrl, param, ResponseModel.class);

        return model;
    }

    @Async
    public void upLoadAsync(String targetUrl, String fileName,Boolean isAsync) {
        ResponseModel model = upLoad(targetUrl, fileName, isAsync);
        this.asyncCallback(JSON.toJSONString(model));
    }

    /*从本服务器下发文件名，市州服务器自主从本服务器下载*/
    public ResponseModel dispatch(String targetUrl,String fileName,Boolean isAsync){
        log.info("开始下发：{}",fileName);
        Path filePath = Paths.get(advertDir, fileName);
        if (StringUtils.isEmpty(fileName) || Files.notExists(filePath)){
            ResponseModel model = ResponseModel.warp(BusinessEnum.UNEXIST).setData(fileName);
            return model;
        }else{
            MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
            param.add("fileName", fileName);
            param.add("isAsync", isAsync);
            ResponseModel model = restTemplate.postForObject(targetUrl, param, ResponseModel.class);
            return model;
        }
    }
    @Async
    public void dispatchAsync(String targetUrl,String fileName,Boolean isAsync){
        ResponseModel model = dispatch(targetUrl, fileName, isAsync);
        this.asyncCallback(JSON.toJSONString(model));
    }




    /*市州服务器上传或下载结果报告*/
    public void asyncCallback(String callbackJson){

        log.info("callback 收到的信息：{}",callbackJson);
    }
}
