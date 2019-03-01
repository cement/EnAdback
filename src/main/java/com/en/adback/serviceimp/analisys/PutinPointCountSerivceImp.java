package com.en.adback.serviceimp.analisys;

import com.en.adback.mapper.analisys.PutinPointCountMapper;
import com.en.adback.service.analisys.PutinPointCountSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PutinPointCountSerivceImp implements PutinPointCountSerivce {
    @Autowired
    private PutinPointCountMapper mapper;

    @Override
    public  List<Map<String,Object>> getPlacementPointStatistics(Map<String, Object> map) {
        List<Map<String,Object>> PutinPointCount = mapper.PutinPointCount(map);
        List<Map<String,Object>> TPlayPolicyScreen =  mapper.getTPlayPolicyScreen();
        List<String> list1 = new ArrayList<String>();
        for (Map<String,Object> map1: PutinPointCount) {
            list1.add(map1.get("POLICYNAME").toString());
        }
        List<String> list2 = new ArrayList<String>();
        for (Map<String,Object> map2: TPlayPolicyScreen) {
            list2.add(map2.get("POLICYNAME").toString());
        }
        for(int i=0;i<list2.size();i++){
            if(!list1.contains(list2.get(i))){
                Map<String,Object> NonExistent = new HashMap<String,Object>();
                NonExistent.put("POLICYNAME",list2.get(i));
                NonExistent.put("INMARCH",0);
                NonExistent.put("OCTOBER",0);
                NonExistent.put("DECEMBER",0);
                NonExistent.put("INMAY",0);
                NonExistent.put("SEPTEMBER",0);
                NonExistent.put("NOVEMBER",0);
                NonExistent.put("INJANUARY",0);
                NonExistent.put("INFEBRUARY",0);
                NonExistent.put("INJUNE",0);
                NonExistent.put("AUGUST",0);
                NonExistent.put("JULY",0);
                NonExistent.put("APRIL",0);
                PutinPointCount.add(NonExistent);
            }
        }
        return PutinPointCount;
    }

    @Override
    public List<Map<String, Object>> getPointStatistics(Map<String, Object> map) {
        List<Map<String,Object>> list = mapper.getPointStatistics(map);
        List<Map<String,Object>> TScreenCount =mapper.TScreenCount();
        List<Map<String,Object>> list3 = new ArrayList<Map<String,Object>>();
        for(int i=0;i<list.size();i++){
            Map<String,Object> NonExistent = new HashMap<String,Object>();
            String str = list.get(i).get("SCREENNAME").toString();
            String str1 = list.get(i).get("SCREENCUTNAME").toString();
            if(str1.contains("-")){
                str1 = str1.substring(str1.indexOf("-")+1,str1.length());
            }
            NonExistent.put("POLICYNAME",str + "--" +str1);
            NonExistent.put("STRATEGYCOUNT",list.get(i).get("STRATEGYCOUNT"));
            String policyName = list.get(i).get("POLICYNAME").toString();
            String policyname = NonExistent.get("POLICYNAME").toString();
            if(policyname.indexOf(policyName.substring(0,1))!= -1){
                list3.add(NonExistent);
            }
        }
        List<String> list1 = new ArrayList<String>();
        for (Map<String,Object> map1: list3) {
            list1.add(map1.get("POLICYNAME").toString());
        }
        List<String> list2 = new ArrayList<String>();
        for(int i=0;i<TScreenCount.size();i++){
            String str = TScreenCount.get(i).get("DETAIL").toString();
            String str1 = TScreenCount.get(i).get("SCREEN").toString();
            if(str1.contains("-")){
                str1 = str1.substring(str1.indexOf("-")+1,str1.length());
            }
            list2.add(str+"--"+str1);
        }

        for(int i=0;i<list2.size();i++){
            if(!list1.contains(list2.get(i))){
                Map<String,Object> NonExistent = new HashMap<String,Object>();
                NonExistent.put("POLICYNAME",list2.get(i));
                NonExistent.put("STRATEGYCOUNT",0);
                list3.add(NonExistent);
            }
        }
        return list3;
    }


    public List<Map<String,Object>> getOrientationOnTheArray(Map<String,Object> map){
        List<Map<String,Object>> list = mapper.getOrientationOnTheArray(map);
        List<Map<String,Object>> TPlayPolicyScreen =  mapper.getTPlayPolicyScreen();
        List<String> list1 = new ArrayList<String>();
        for (Map<String,Object> map1: list) {
            list1.add(map1.get("POLICYNAME").toString());
        }
        List<String> list2 = new ArrayList<String>();
        for (Map<String,Object> map2: TPlayPolicyScreen) {
            list2.add(map2.get("POLICYNAME").toString());
        }
        for(int i=0;i<list2.size();i++){
            if(!list1.contains(list2.get(i))){
                Map<String,Object> map1 = new HashMap<String,Object>();
                map1.put("POLICYNAME",list2.get(i));
                map1.put("STRATEGYCOUNT",0);
                list.add(map1);
            }
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> getTheTailArray(Map<String, Object> map) {
        List<Map<String,Object>> list = mapper.getTheTailArray(map);
        List<Map<String,Object>> TScreenCount =mapper.TScreenCount();
        List<Map<String,Object>> list3 = new ArrayList<Map<String,Object>>();
        for(int i=0;i<list.size();i++){
            Map<String,Object> NonExistent = new HashMap<String,Object>();
            String str = list.get(i).get("SCREENNAME").toString();
            String str1 = list.get(i).get("SCREENCUTNAME").toString();
            if(str1.contains("-")){
                str1 = str1.substring(str1.indexOf("-")+1,str1.length());
            }
            NonExistent.put("POLICYNAME",str + "--" +str1);
            NonExistent.put("STRATEGYCOUNT",list.get(i).get("STRATEGYCOUNT"));
            String policyName = list.get(i).get("POLICYNAME").toString();
            String policyname = NonExistent.get("POLICYNAME").toString();

            if(policyname.indexOf(policyName.substring(0,1))!= -1){
                list3.add(NonExistent);
            }
        }
        List<String> list1 = new ArrayList<String>();
        for (Map<String,Object> map1: list3) {
            list1.add(map1.get("POLICYNAME").toString());
        }
        List<String> list2 = new ArrayList<String>();
        for(int i=0;i<TScreenCount.size();i++){
            String str = TScreenCount.get(i).get("DETAIL").toString();
            String str1 = TScreenCount.get(i).get("SCREEN").toString();
            if(str1.contains("-")){
                str1 = str1.substring(str1.indexOf("-")+1,str1.length());
            }
            list2.add(str+"--"+str1);
        }

        for(int i=0;i<list2.size();i++){
            if(!list1.contains(list2.get(i))){
                Map<String,Object> NonExistent = new HashMap<String,Object>();
                NonExistent.put("POLICYNAME",list2.get(i));
                NonExistent.put("STRATEGYCOUNT",0);
                list3.add(NonExistent);
            }
        }
        return list3;
    }


    @Override
    public List<Map<String, Object>> getBroadcastFrequency(Map<String, Object> map) {
        List<Map<String,Object>> list  = mapper.getBroadcastFrequency(map);
        List<Map<String,Object>> TScreenCount =mapper.TScreenCount();


        List<Map<String,Object>> list3 = new ArrayList<Map<String,Object>>();
        for(int i=0;i<list.size();i++){
            Map<String,Object> NonExistent = new HashMap<String,Object>();
            String str = list.get(i).get("SCREENNAME").toString();
            String str1 = list.get(i).get("SCREENCUTNAME").toString();
            if(str1.contains("-")){
                str1 = str1.substring(str1.indexOf("-")+1,str1.length());
            }
            NonExistent.put("POLICYNAME",str + "--" +str1);
            NonExistent.put("INJANUARY",list.get(i).get("INJANUARY"));
            NonExistent.put("INFEBRUARY",list.get(i).get("INFEBRUARY"));
            NonExistent.put("INMARCH",list.get(i).get("INMARCH"));
            NonExistent.put("APRIL",list.get(i).get("APRIL"));
            NonExistent.put("INMAY",list.get(i).get("INMAY"));
            NonExistent.put("INJUNE",list.get(i).get("INJUNE"));
            NonExistent.put("JULY",list.get(i).get("JULY"));
            NonExistent.put("AUGUST",list.get(i).get("AUGUST"));
            NonExistent.put("SEPTEMBER",list.get(i).get("SEPTEMBER"));
            NonExistent.put("OCTOBER",list.get(i).get("OCTOBER"));
            NonExistent.put("NOVEMBER",list.get(i).get("NOVEMBER"));
            NonExistent.put("DECEMBER",list.get(i).get("DECEMBER"));

            String policyName = list.get(i).get("POLICYNAME").toString();
            String policyname = NonExistent.get("POLICYNAME").toString();

            if(policyname.indexOf(policyName.substring(0,1))!= -1){
                list3.add(NonExistent);
            }
        }
        List<String> list1 = new ArrayList<String>();
        for (Map<String,Object> map1: list3) {
            list1.add(map1.get("POLICYNAME").toString());
        }
        List<String> list2 = new ArrayList<String>();
        for(int i=0;i<TScreenCount.size();i++){
            String str = TScreenCount.get(i).get("DETAIL").toString();
            String str1 = TScreenCount.get(i).get("SCREEN").toString();
            if(str1.contains("-")){
                str1 = str1.substring(str1.indexOf("-")+1,str1.length());
            }
            list2.add(str+"--"+str1);
        }

        for(int i=0;i<list2.size();i++){
            if(!list1.contains(list2.get(i))){
                Map<String,Object> NonExistent = new HashMap<String,Object>();
                NonExistent.put("POLICYNAME",list2.get(i));
                NonExistent.put("INJANUARY",0);
                NonExistent.put("INFEBRUARY",0);
                NonExistent.put("INMARCH",0);
                NonExistent.put("APRIL",0);
                NonExistent.put("INMAY",0);
                NonExistent.put("INJUNE",0);
                NonExistent.put("JULY",0);
                NonExistent.put("AUGUST",0);
                NonExistent.put("SEPTEMBER",0);
                NonExistent.put("OCTOBER",0);
                NonExistent.put("NOVEMBER",0);
                NonExistent.put("DECEMBER",0);
                list3.add(NonExistent);
            }
        }
        return list3;
    }
}
