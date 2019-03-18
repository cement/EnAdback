package com.en.adback;

import com.alibaba.fastjson.JSON;
import com.en.adback.entity.advertmgr.PlayPolicyScreen;
import com.en.adback.serviceimp.advertmgr.advertPolicyServiceImp;
import org.apache.catalina.servlets.DefaultServlet;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CommonTest {
    public static void main(String[] args) {
//        List<PlayPolicyScreen> playPolicyScreens = new advertPolicyServiceImp().allPlayPolicyScreen();
////        System.out.println(playPolicyScreens);
//        System.out.println(JSON.toJSONString(playPolicyScreens,true));
//        String replaceDevices="a,b,c,c";
//        String replaceDevicesStr = Arrays.stream(replaceDevices.split(",")).sorted().collect(Collectors.joining("','", "‘", "'"));
//        System.out.println(replaceDevicesStr);


        String url = "http://192.168.1.99:8080/";
//        String pattern = "(http|https)://(\\w+|\\w+/)";
        String pattern = "(http|https)://([\\w.:]+|[\\w.:]+/)";
        boolean isMatch = Pattern.matches(pattern, url);
        System.out.println("isMatch:"+isMatch);
    }
}
