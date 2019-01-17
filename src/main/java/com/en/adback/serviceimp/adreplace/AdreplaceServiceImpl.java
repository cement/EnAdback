package com.en.adback.serviceimp.adreplace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AdreplaceServiceImpl {

    @Value("${advertfiles}")
    private String advertDir;


    @Autowired
    private RestTemplate restTemplate;

    public void downLoad(String fileName, HttpServletResponse response) throws IOException {

        Path path = Paths.get(advertDir, fileName);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "iso-8859-1"));
        long readed = Files.copy(path, response.getOutputStream());

    }

    public String upLoad(String targetUrl, String fileName) {

        Path filePath = Paths.get(advertDir, fileName);
        FileSystemResource resource = new FileSystemResource(filePath);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("file", resource);
        param.add("fileName", fileName);
        String result = restTemplate.postForObject(targetUrl, param, String.class);
        return result;
    }

    public String dispatch(String targetUrl,String fileName){
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("fileName", fileName);
        String result = restTemplate.postForObject(targetUrl, param, String.class);
        return result;
    }
}
