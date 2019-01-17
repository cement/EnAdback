package com.en.adback.controller;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/transFile", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json;charset=UTF-8")
public class UploadFileCtrl {

    @Value("${advertfiles}")
    private String advertfiles;

    @PostMapping(value = "/mediaUpLoadUrl")
    @ResponseBody
    public void upload(@RequestParam MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        String s = UUID.randomUUID().toString();
        String guid= System.currentTimeMillis()+s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
        response.setHeader("Access-Control-Allow-Methods", "POST,PUT,OPTIONS,DELETE,GET");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,content-type,Origin");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        Map<String, Object> resMap = new HashMap<String, Object>();
        String realPath = "";
        String saveurl = "";
        String retstr = "";
        if (file.isEmpty()) {
            // 未选择文件
            resMap.put("status", "未选择文件");
        } else {
            // 文件后缀名
            String originFileName = file.getOriginalFilename().split("\\.")[file.getOriginalFilename().split("\\.").length-1];
                realPath = advertfiles + "media";
                saveurl = advertfiles + "media/";

            try {
                FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath, guid + "." + originFileName));
                resMap.put("url", saveurl + guid + "." + originFileName);
                resMap.put("filename", file.getOriginalFilename());
                resMap.put("size", file.getSize());
                resMap.put("guid", guid);
                resMap.put("downloadFileName", guid + "." + originFileName);
                resMap.put("status", "ok");

            } catch (IOException e) {
                System.out.println("文件上传失败");
                resMap.put("status", "服务器IO异常");
                e.printStackTrace();
            }

        }
        retstr= JSON.toJSONString(resMap);
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        writer.print(retstr);
    }



    @PostMapping(value = "/dataFileUpLoadUrl")
    @ResponseBody
    public void uploadDataFile(@RequestParam MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        String s = UUID.randomUUID().toString();
        String guid= System.currentTimeMillis()+s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
        response.setHeader("Access-Control-Allow-Methods", "POST,PUT,OPTIONS,DELETE,GET");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,content-type,Origin");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        Map<String, Object> resMap = new HashMap<String, Object>();
        String realPath = "";
        String saveurl = "";
        String retstr = "";
        if (file.isEmpty()) {
            // 未选择文件
            resMap.put("status", "未选择文件");
        } else {
            // 文件后缀名
            String originFileName = file.getOriginalFilename().split("\\.")[file.getOriginalFilename().split("\\.").length-1];
            realPath = advertfiles + "datafile";
            saveurl = advertfiles + "datafile/";

            try {
                FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath, guid + "." + originFileName));
                resMap.put("url", saveurl + guid + "." + originFileName);
                resMap.put("filename", file.getOriginalFilename());
                resMap.put("size", file.getSize());
                resMap.put("guid", guid);
                resMap.put("downloadFileName", guid + "." + originFileName);
                resMap.put("status", "ok");

            } catch (IOException e) {
                System.out.println("文件上传失败");
                resMap.put("status", "服务器IO异常");
                e.printStackTrace();
            }

        }
        retstr= JSON.toJSONString(resMap);
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        writer.print(retstr);
    }


}
