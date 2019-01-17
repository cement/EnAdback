package com.en.adback.serviceimp.advertMonitor;

import com.en.adback.entity.advertmgr.FilehostDownloadRole;
import com.en.adback.mapper.advertMonitor.CityHostFileMonMapper;
import com.en.adback.service.advertMonitor.CityHostFileMonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CityHostFileMonServiceImp implements CityHostFileMonService {

    @Autowired private CityHostFileMonMapper mapper;

    @Override
    public List<FilehostDownloadRole> cityHostFileList(Map<String, Object> map) {
        return mapper.cityHostFileList(map);
    }
}
