package com.en.adback.service.analisys;

import java.util.List;
import java.util.Map;

public interface PutinPointCountSerivce {

    public  List<Map<String,Object>> getPlacementPointStatistics(Map<String,Object> map);

    public List<Map<String,Object>> getPointStatistics(Map<String,Object> map);

    public List<Map<String,Object>> getOrientationOnTheArray(Map<String,Object> map);

    public List<Map<String,Object>> getTheTailArray(Map<String,Object> map);

    public List<Map<String,Object>> getBroadcastFrequency(Map<String,Object> map);


}
