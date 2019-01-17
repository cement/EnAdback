package com.en.adback.service.analisys;

import com.en.adback.entity.PlayLog;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface PlayLogService {
    public List<PlayLog> getPlayLlogList(Map<String,Object> map) throws ParseException;
}
