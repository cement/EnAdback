package com.en.adback.serviceimp.analisys;

import com.en.adback.entity.PlayLog;
import com.en.adback.mapper.analisys.PlayLogMapper;
import com.en.adback.service.analisys.PlayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PlayLogServiceImp implements PlayLogService {
    @Autowired
    private PlayLogMapper mapper;

    @Override
    public List<PlayLog> getPlayLlogList(Map<String, Object> map) throws ParseException {
        List<PlayLog> list = mapper.getPlayLlogList(map);
        for(int i = 0;i<list.size()-1;i++){
            // 过滤 是否在指定时间段内（已时分秒为准）
            SimpleDateFormat df = new SimpleDateFormat("HH-mm-ss");
            String strDate = df.format(df.parse(list.get(i).getPlayTime()));
            Date now = df.parse(strDate); // 获取list中一条数据的时间
            Date begin = df.parse(map.get("beginPlayTime").toString());// 开始时间
            Date end = df.parse(map.get("endPlayTime").toString()); // 结束时间
            Calendar nowTime = Calendar.getInstance();
            nowTime.setTime(now);
            Calendar beginTime = Calendar.getInstance();
            beginTime.setTime(begin);
            Calendar endTime = Calendar.getInstance();
            endTime.setTime(end);
            // 在指定时间段内为 true
            if (nowTime.before(endTime) && nowTime.after(beginTime) && map.get("advertId").equals(list.get(i).getAdvertId())) {
                SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat df2 = new SimpleDateFormat("HH-mm-ss");
                list.get(i).setBroadcastDate(df1.format(df1.parse(list.get(i).getPlayTime())));
                list.get(i).setAirTime(df2.format(df2.parse(list.get(i).getPlayTime())));
                if(list.get(i).getScreen().contains("-")){
                    String str = list.get(i).getPolicyName().substring(0,list.get(i).getPolicyName().indexOf("策略"));
                    String str2 = list.get(i).getScreen().substring(0,list.get(i).getScreen().indexOf("-"));
                    list.get(i).setDetail(str+str2+"屏");
                    list.get(i).setScreen(list.get(i).getScreen().substring(list.get(i).getScreen().indexOf("-")+1,list.get(i).getScreen().length()-1)+"屏");
                }else{
                    String str = list.get(i).getPolicyName().substring(0,list.get(i).getPolicyName().indexOf("策略"));
                    String str2 = list.get(i).getScreen().substring(0,list.get(i).getScreen().indexOf("-"));
                    list.get(i).setDetail(str+str2+"屏");
                }
                if(i > 0){
                    list.get(i).setFormerAdvertising(list.get(i-1).getAdvertName());
                }
                if(i < list.size()-1){
                    list.get(i).setAfterTheAdvertising(list.get(i+1).getAdvertName());
                }
            }
        }
        List<PlayLog> filterList = list.stream().filter(a -> a.getDetail()!=null && !"".equals(a.getDetail()) && map.get("advertId").equals(a.getAdvertId())).collect(Collectors.toList());
        return filterList;
    }
}
