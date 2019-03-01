package com.en.adback.serviceimp;

import com.en.adback.mapper.playCertifyExcelMapper;
import com.en.adback.service.PlayCertifyExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PlayCertifyExcelServiceImp implements PlayCertifyExcelService {
    @Autowired
    private playCertifyExcelMapper mapper;

    @Override
    public List<Map<String, Object>> getPlayCertifyExcel(Map<String, Object> map) {
       return mapper.getPlayCertifyExcel(map);
    }
}
