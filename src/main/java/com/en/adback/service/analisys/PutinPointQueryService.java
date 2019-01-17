package com.en.adback.service.analisys;

import java.util.List;
import java.util.Map;

public interface PutinPointQueryService {

   // 投放点位查询
   public List<Map<String,Object>> putinPointQueryList(String beginDate,String endDate,String deviceIds);

}
