package com.en.adback.service.sys;

import com.en.adback.entity.sys.FileHost;

import java.util.List;

/**
 * Created by Administrator on 2018/12/5.
 */
public interface FileHostService {

    List<FileHost> getFileHostList();

    int insertFileHost(FileHost fileHost);
}
