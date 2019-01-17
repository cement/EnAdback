package com.en.adback.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Api(value="文件上传",tags={"文件上传webapi接口"})
@RestController
@CrossOrigin
@RequestMapping(value = "/api/upload", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json;charset=UTF-8")
public class UploadToServer {

    @RequestMapping(value = "/UploadToServer")
    public void uploadFileToServer(@RequestParam MultipartFile file, HttpServletRequest request, HttpServletResponse response){
        String path=request.getSession().getServletContext().getRealPath("/WEB-INF/classes/static/files/");
        System.out.print(path);
        String fileName =file.getOriginalFilename();
        try {
            InputStream is=file.getInputStream();
            FileOutputStream os=new FileOutputStream(path+fileName);
            int length=0;
            byte b[]=new byte[1024];
            while( (length=is.read(b))>0)
            {
                os.write(b, 0, length);
            }
            os.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
