package com.en.adback.serviceimp.sys;

import com.en.adback.entity.Logs;
import com.en.adback.mapper.LogsMapper;
import com.en.adback.service.sys.LogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LogsServiceImp implements LogsService {

    @Autowired private LogsMapper mapper;

    @Override
    public List<Logs> getLogsList(Map<String, Object> map) {
        return mapper.getLogsList(map);
    }

    @Override
    public String getLogsListTotal(Map<String, Object> map) {
        List<Map<String,Object>> list=mapper.getLogsListTotal(map);
        return list.get(0).get("TOTAL").toString();
    }
}
