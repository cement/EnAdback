package com.en.adback.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@RestController
@CrossOrigin
@RequestMapping(value = "/transFile", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json;charset=UTF-8")
public class fileDownload {
    @Value("${advertfiles}")
    private String advertfiles;

    @RequestMapping(value = "/fileDownload")
    public void downloadLocal(HttpServletRequest request, HttpServletResponse response,@RequestParam("fileName") String fileName) throws IOException {

        String   path = advertfiles + "media/";
                 //处理文件名
                 String realname = fileName.substring(fileName.indexOf("_")+1);
                 //设置响应头，控制浏览器下载该文件
                 response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realname, "UTF-8"));
                 //读取要下载的文件，保存到文件输入流
                 FileInputStream in = new FileInputStream(path + "\\" + fileName);
                 //创建输出流
                 OutputStream out = response.getOutputStream();
                         byte buffer[] = new byte[1024];
                 //创建缓冲区
                 int len = 0;
                 //循环将输入流中的内容读取到缓冲区当中
                 while((len=in.read(buffer))>0){
                         //输出缓冲区的内容到浏览器，实现文件下载
                         out.write(buffer, 0, len);
                     }
                 //关闭文件输入流
                 in.close();
                 //关闭输出流
                 out.close();
    }

    @RequestMapping(value = "/dataFileDownload")
    public void dataFiledownloadLocal(HttpServletRequest request, HttpServletResponse response,@RequestParam("fileName") String fileName) throws IOException {

        String   path = advertfiles + "datafile/";
        //处理文件名
        String realname = fileName.substring(fileName.indexOf("_")+1);
        //设置响应头，控制浏览器下载该文件
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realname, "UTF-8"));
        //读取要下载的文件，保存到文件输入流
        FileInputStream in = new FileInputStream(path + "\\" + fileName);
        //创建输出流
        OutputStream out = response.getOutputStream();
        byte buffer[] = new byte[1024];
        //创建缓冲区
        int len = 0;
        //循环将输入流中的内容读取到缓冲区当中
        while((len=in.read(buffer))>0){
            //输出缓冲区的内容到浏览器，实现文件下载
            out.write(buffer, 0, len);
        }
        //关闭文件输入流
        in.close();
        //关闭输出流
        out.close();
    }

}
