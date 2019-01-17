package com.en.adback.entity.advertmgr;


/**
 * 广告播放媒体类
 */
public class Media {
    private String fileName ; // 文件名称
    private String fileSize ; // 文件大小 （单位kb,mb）
    private int duration ; // 播放时长
    private String downLoadUrl ; // 下载地址
    private String downLoadFileName; // 下载时的文件名(唯一)
    public Media() {
    }
}
