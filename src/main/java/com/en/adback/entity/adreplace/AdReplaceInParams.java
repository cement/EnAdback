package com.en.adback.entity.adreplace;

import com.en.adback.entity.UserAction;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AdReplaceInParams extends UserAction {

    private  String sourceAdvertPolicyId;
    private String advertPolicyId;
    private String devices;
    private List<AdvertInfo> adverts;


       @Data
       @NoArgsConstructor
        public static class AdvertInfo {
           private String  screenCutId;   // 切屏编号
           private  String advertId;   // 广告编号
           private   String advertName; //广告文件名称
           private  String  srcAdvertId;  //被替换的广告Id
           private  String  srcAdvertName;  //被替换的广告名称
       }
}
