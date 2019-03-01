package com.en.adback.controller.Adorder;

import com.en.adback.AdbackApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdbackApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdorderCtrlTest {


    @Autowired
    private AdorderCtrl adorderCtrl;

    @Test
    public void allScreenPolicys() {
//           adorderCtrl.allScreenPolicys("userId","groupId","roleId", null);
//        List<PlayPolicyScreen> list= advertPolicyService.allPlayPolicyScreen();
//        log.info("=== 测试 === allScreenPolicys:{}", JSON.toJSONString(list,true));
    }

    @Test
    public void getAdorderPolicys() {
//        AdorderPolicy adorderPolicy = adorderService.readAdorderPolicys("123456789");
//        log.info("=== 测试 === adorderPolicy:{}", JSON.toJSONString(adorderPolicy,true));
    }

    @Test
    public void readAdorderPolicyByOrderId() {


    }
}