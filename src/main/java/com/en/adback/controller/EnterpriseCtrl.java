package com.en.adback.controller;

import com.en.adback.entity.dic.Enterprise;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/advertCorpQuery", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json;charset=UTF-8")
public class EnterpriseCtrl {

    @GetMapping(value = "selectEnterprise")
    public List<Enterprise> selectEnterprise(){
        return null;
    }
    @PostMapping(value = "saveEnterprise")
    public Map<String,Object> saveEnterprise(){
        Map<String,Object> map=new HashMap<String, Object>();
        if(1==1){
            map.put("status","success");
        }else{
            map.put("status","err");
        }
        return null;
    }

    @PostMapping(value = "updateEnterprise")
    public Map<String,Object> updateEnterprise(){
        Map<String,Object> map=new HashMap<String, Object>();
        if(1==1){
            map.put("status","success");
        }else{
            map.put("status","err");
        }
        return null;
    }

    @GetMapping(value = "deleteEnterprise")
    public Map<String,Object> deleteEnterprise(){
        Map<String,Object> map=new HashMap<String, Object>();
        if(1==1){
            map.put("status","success");
        }else{
            map.put("status","err");
        }
        return null;
    }
}
