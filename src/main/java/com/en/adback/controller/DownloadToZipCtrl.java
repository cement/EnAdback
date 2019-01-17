package com.en.adback.controller;

import com.en.adback.common.MessageModel;
import com.en.adback.entity.advertmgr.FilehostDownloadRole;
import io.swagger.annotations.Api;
import org.apache.avro.ValidateAll;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Api(value="文件下载到压缩包",tags={"文件上传webapi接口"})
@RestController
@RequestMapping(value = "/api/download", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json;charset=UTF-8")
public class DownloadToZipCtrl {

    @Value("${advertfiles}")
    private String advertfiles;


    //将市州服务器未下载的文件下载到直径文件夹并且压缩
    @GetMapping(value = "/cityFileDownloadToZip")
    public MessageModel downloadZip(String fileNames,String dirPath){
        String fileName[]=fileNames.split(",");
        MessageModel model=new MessageModel();
        Map<String,Object> map=new HashMap<String, Object>();
        //1、将需要下载的目录清空
        emptyFolder(advertfiles+"/"+dirPath);
        //下载所有未下载的文件（屏幕播放文件）路径：文件上传路径中“downloadFiles”
        for (int i = 0; i < fileName.length; i++) {
            downloadFileToZip(dirPath,fileName[i]);
        }
        try {
            //将下载好的文件夹压缩
            compressedZip(advertfiles+dirPath,advertfiles);
            //下载压缩包
//            downloadZipToServer("downloadFiles.zip",response);

        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("fileName",dirPath+".zip");
        model.setData(map);
        model.setResultCode("1");
        model.setResultMsg("success");
        return model;
    }

    //将设备文件未下载的下载并压缩
    @GetMapping(value = "/deviceFileDownloadToZip")
    public MessageModel deviceFileDownloadToZip(String fileNames,String dirPath){
        MessageModel model=new MessageModel();
        Map<String,Object> map=new HashMap<String, Object>();
        String fileName[]=fileNames.split(",");
        //对应的文件夹清空                              1544754348649a757ad7d1d0d4d3ab2a88c1d1d9f25.jpg,
        emptyFolder(advertfiles+dirPath);
        //根据文件名称下载文件
        for (int i = 0; i < fileName.length; i++) {
            downloadFileToZip(dirPath,fileName[i]);
        }
        //文件夹压缩
        try {
            compressedZip(advertfiles+dirPath,advertfiles);
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("fileName",dirPath+".zip");
        model.setData(map);
        model.setResultCode("1");
        model.setResultMsg("success");
        return model;
    }


    //将指定文件夹内文件清空
    public void emptyFolder(String path){
        File f=new File(path);
        if(f.exists()){
            String lists[]=f.list();
            for (int i = 0; i < lists.length; i++) {
                File file=new File(path+"/"+lists[i]);
                if(file.isDirectory()){
                 emptyFolder(file.getPath());
                }else {
                    file.delete();
                }
            }
        }
    }


    //将文件下载到需要打包目录下
    public void downloadFileToZip(String dirPath,String fileName){
        //获取文件源文件路径
        String respath=advertfiles+"media/";
        //设置下载文件路径
        String path=advertfiles+"/"+dirPath;
        File f=new File(path);
        if(!f.exists()){
            f.mkdirs();
        }
        try {
            FileInputStream is=new FileInputStream(respath+fileName);
            OutputStream os=new FileOutputStream(new File(path+"/"+fileName));
            byte b[]=new byte[1024];
            int i=0;
            while ((i=is.read(b))!=-1){
                os.write(b,0,i);
            }
            is.close();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //将文件目录压缩
    public void compressFilesToZip(String path, File file, ZipOutputStream out) throws IOException {
        //如果当前的是文件夹，则进行进一步处理
        if (file.isDirectory()) {
            //得到文件列表信息
            File[] files = file.listFiles();
            //将文件夹添加到下一级打包目录
            out.putNextEntry(new ZipEntry(path + "/"));

            path = path.length() == 0 ? "" : path + "/";

            //循环将文件夹中的文件打包
            for (int i = 0; i < files.length; i++) {
                compressFilesToZip(path + files[i].getName(),files[i],out);         //递归处理
            }
        } else {   //当前的是文件，打包处理
            //文件输入流
            FileInputStream fis = new FileInputStream(file);

            out.putNextEntry(new ZipEntry(path));
            //进行写操作
            int length = 0;
            byte[] b = new byte[1024];
            while ((length = fis.read(b)) > 0) {
                out.write(b, 0, length);
            }
            //关闭输入流
            fis.close();
        }
    }

    //将文件夹压缩
    public void compressedZip(String resourcesPath, String targetPath) throws Exception {
        File resourcesFile = new File(resourcesPath);     //源文件
        File targetFile = new File(targetPath);           //目的
        //如果目的路径不存在，则新建
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        String targetName = resourcesFile.getName() + ".zip";   //目的压缩文件名
        FileOutputStream outputStream = new FileOutputStream(targetPath + "\\" + targetName);
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(outputStream));
        compressFilesToZip("",resourcesFile,out);
        out.close();
    }

    //将压缩包下载
    public void downloadZipToServer(String fileName,HttpServletResponse response){
        //设置响应头，控制浏览器下载该文件
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setContentType("application/octet-stream");
            //读取要下载的文件，保存到文件输入流
            FileInputStream is = new FileInputStream(advertfiles+fileName);
//            设置输出流为服务器响应流
            OutputStream os=response.getOutputStream();
            byte b[]=new byte[1024];
            int i=0;
            response.setContentLength((int) new File(advertfiles+fileName).length());
            //数据读取
            while ((i=is.read(b))!=-1){
                os.write(b,0,i);
            }
            //关闭流
            is.close();
            os.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
