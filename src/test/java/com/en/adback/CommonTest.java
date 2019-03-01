package com.en.adback;

import com.alibaba.fastjson.JSON;
import com.en.adback.entity.advertmgr.PlayPolicyScreen;
import com.en.adback.serviceimp.advertmgr.advertPolicyServiceImp;

import java.util.Date;
import java.util.List;

public class CommonTest {
    public static void main(String[] args) {
//        List<PlayPolicyScreen> playPolicyScreens = new advertPolicyServiceImp().allPlayPolicyScreen();
////        System.out.println(playPolicyScreens);
//        System.out.println(JSON.toJSONString(playPolicyScreens,true));
      String s = "or20190222-0001";
//        String ss = s.substring(s.indexOf("-")+1);
//        System.out.println(ss);

        String ss = String.format("%04d", 2);
        System.out.println(ss);

        String[] split = s.split("-");
        System.out.println(split[0]);
        System.out.println(split[1]);



        System.out.println(new Date("1551084098388"));
    }
}
