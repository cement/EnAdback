package com.en.adback.service.sys;

import com.en.adback.entity.Logs;

import java.util.List;
import java.util.Map;

public interface LogsService {


    public List<Logs> getLogsList(Map<String, Object> map);

    public String getLogsListTotal(Map<String, Object> map);
}
