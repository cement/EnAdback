package com.en.adback.serviceimp.Adorder;

import com.alibaba.fastjson.JSON;
import com.en.adback.AdbackApplication;
import com.en.adback.entity.Adorder.AdorderPolicy;
import com.en.adback.entity.advertmgr.PlayPolicyScreen;
import com.en.adback.service.Adorder.IAdorderService;
import com.en.adback.service.advertmgr.IAdvertPolicyService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdbackApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdorderServiceImplTest {

    @Autowired
    private IAdorderService adorderService;
    @Autowired
    private IAdvertPolicyService advertPolicyService;

    @Test
    public void allScreenPolicys() {
        List<PlayPolicyScreen> list= advertPolicyService.allPlayPolicyScreen();
        log.info("=== 测试 === allScreenPolicys:{}", JSON.toJSONString(list,true));
    }
    @Test
    public void readAdorderPolicy() {
        AdorderPolicy adorderPolicy = adorderService.readAdorderPolicy("0123456789");
        log.info("=== 测试 === adorderPolicy:{}", JSON.toJSONString(adorderPolicy,true));
    }


    @Test
    public void maxOrderId() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("baseDate","20190101");
        String result = adorderService.maxOrderId(map);
        log.info("=== 测试 ===  maxOrderId：{}",result);

    }
}