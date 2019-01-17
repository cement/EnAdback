package com.en.adback.entity.advertmgr;

import lombok.Data;

/**
 * 资料文件类
 */
@Data
public class DataFile {
    private String fileName ; // 文件名称
    private String fileSize ; // 文件大小 （单位kb,mb）
    private String downLoadUrl ; // 下载地址
    private String downLoadFileName; // 下载时的文件名(唯一)
}
