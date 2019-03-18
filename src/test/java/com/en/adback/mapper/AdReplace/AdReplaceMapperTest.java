package com.en.adback.mapper.AdReplace;

import com.en.adback.AdbackApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdbackApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class AdReplaceMapperTest {


    @Autowired
    private AdReplaceMapper replaceMapper;


    @Test
    public void getSubAdvertPolicysByAdvertPolicysIdAndScreenCutIdTest(){
        Map<String, Object> po = replaceMapper.getSubAdvertPolicysByAdvertPolicysIdAndScreenCutId("po1551316532545", "09");
        log.info("=== 测试 === po:{}",po);
    }
    @Test
    public void getAdvertPolicysByIdTest(){
        Map<String, String> po = replaceMapper.getAdvertPolicysById("po1551244153887");
        log.info("=== 测试 === po:{}",po);
    }
    @Test
    public void getFileNameByAdvertIdTest(){
        String po = replaceMapper.getFileNameByAdvertId("ad1552270089720");
        log.info("=== 测试 === \n po:{}",po);

    }
}