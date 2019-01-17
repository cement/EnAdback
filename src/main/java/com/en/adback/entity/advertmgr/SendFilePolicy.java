package com.en.adback.entity.advertmgr;

import lombok.Data;
import java.util.List;
import java.util.Map;

//媒体文件下发策略
@Data
public class SendFilePolicy {
    private String fileName ; // 文件名
    private String beginPlayTime; // 开始播放时间(格式:'yyyy-mm-dd hh:mi:ss')
    private List<Map<String,Object>> targetHostUrl; // 下发的目标服务器url
    private boolean sended ; // 是否下发完成标志

}
