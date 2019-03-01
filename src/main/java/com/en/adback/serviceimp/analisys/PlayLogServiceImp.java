package com.en.adback.serviceimp.analisys;

import com.en.adback.entity.PlayLog;
import com.en.adback.mapper.analisys.PlayLogMapper;
import com.en.adback.service.analisys.PlayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class PlayLogServiceImp implements PlayLogService {
    @Autowired
    private PlayLogMapper mapper;

    @Override
    public List<PlayLog> getPlayLlogList(Map<String, Object> map) throws ParseException {
        List<PlayLog> list = mapper.getPlayLlogList(map);
        List<PlayLog> list3 = new ArrayList<PlayLog>();
        String screen = "";
        for(int k=0;k<list.size();k++){
            if(map.get("advertId").toString().equals(list.get(k).getAdvertId())){
                screen =  list.get(k).getScreen();
                break;
            }
        }
        for(int i = 0;i<list.size()-1;i++){
            String str = list.get(i).getPolicyName();
           if(list.get(i).getScreenName().indexOf(str.substring(0,1)) != -1 && screen.equals(list.get(i).getScreen())){
               list3.add(list.get(i));
           }
        }
        for(int i = 0;i<list3.size()-1;i++){
            if(null == map.get("beginPlayTime") || "".equals(map.get("beginPlayTime")) || null == map.get("endPlayTime")
                    || "".equals(map.get("endPlayTime"))){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
                DateFormat df3 = DateFormat.getTimeInstance();//只显示出时分秒
                DateFormat df = DateFormat.getDateInstance();//日期格式，精确到日
                Date date = sdf.parse(list3.get(i).getPlayTime());
                list3.get(i).setBroadcastDate(df.format(date));
                list3.get(i).setAirTime(df3.format(date));
                if(list3.get(i).getScreen().contains("-")){
                    list3.get(i).setDetail(list3.get(i).getScreenName());
                    list3.get(i).setScreen(list3.get(i).getScreen().substring(list3.get(i).getScreen().indexOf("-")+1,list3.get(i).getScreen().length()));
                }else{
                    list3.get(i).setDetail(list3.get(i).getScreenName());
                    list3.get(i).setScreen(list3.get(i).getScreen());
                }
                if(i > 0){
                    list3.get(i).setFormerAdvertising(list3.get(i-1).getAdvertName());
                }
                if(i < list3.size()-1){
                    list3.get(i).setAfterTheAdvertising(list3.get(i+1).getAdvertName());
                }
            }else{
                // 过滤 是否在指定时间段内（已时分秒为准）
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
                DateFormat df3 = DateFormat.getTimeInstance();//只显示出时分秒
                DateFormat df = DateFormat.getDateInstance();//日期格式，精确到日
                Date date = sdf.parse(list3.get(i).getPlayTime());
                String format = "HH:mm:ss";
                Date now = new SimpleDateFormat(format).parse(df3.format(date)); // 获取list中一条数据的时间
                Date begin = new SimpleDateFormat(format).parse(map.get("beginPlayTime").toString());// 开始时间
                Date end = new SimpleDateFormat(format).parse(map.get("endPlayTime").toString()); // 结束时间
                Calendar nowTime = Calendar.getInstance();
                nowTime.setTime(now);
                Calendar beginTime = Calendar.getInstance();
                beginTime.setTime(begin);
                Calendar endTime = Calendar.getInstance();
                endTime.setTime(end);
                // 在指定时间段内为 true
                if (nowTime.before(endTime) && nowTime.after(beginTime) && map.get("advertId").equals(list3.get(i).getAdvertId())) {
                    list3.get(i).setBroadcastDate(df.format(date));
                    list3.get(i).setAirTime(df3.format(date));
                    if(list3.get(i).getScreen().contains("-")){
                        list3.get(i).setDetail(list3.get(i).getScreenName());
                        list3.get(i).setScreen(list3.get(i).getScreen().substring(list3.get(i).getScreen().indexOf("-")+1,list3.get(i).getScreen().length()));
                    }else{
                        list3.get(i).setDetail(list3.get(i).getScreenName());
                        list3.get(i).setScreen(list3.get(i).getScreen());
                    }
                    if(i > 0){
                        list3.get(i).setFormerAdvertising(list3.get(i-1).getAdvertName());
                    }
                    if(i < list3.size()-1){
                        list3.get(i).setAfterTheAdvertising(list3.get(i+1).getAdvertName());
                    }
                }
            }

        }
       List<PlayLog> filterList = list3.stream().filter(a -> a.getDetail()!=null && !"".equals(a.getDetail()) && map.get("advertId").equals(a.getAdvertId())).collect(Collectors.toList());
        return filterList;
    }
}
