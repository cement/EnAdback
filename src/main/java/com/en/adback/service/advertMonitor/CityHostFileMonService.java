package com.en.adback.service.advertMonitor;

import com.en.adback.entity.advertmgr.FilehostDownloadRole;

import java.util.List;
import java.util.Map;

public interface CityHostFileMonService {

    public List<FilehostDownloadRole> cityHostFileList(Map<String, Object> map);
}
