package com.en.adback.controller.Adreplace;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.en.adback.common.MessageModel;
import com.en.adback.entity.adreplace.AdReplaceInParams;
import com.en.adback.entity.adreplace.BusinessEnum;
import com.en.adback.entity.adreplace.ResponseModel;
import com.en.adback.serviceimp.adreplace.AdreplaceServiceImpl;
import com.en.adback.websocket.WsSessionManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author YSH
 * @created 20190122
 * @desc 替换广告分发、下载、回调接口
 */

@Api(value="广告替换、分发、下载、回调",tags={"广告分发、下载、回调webapi接口"})
@Slf4j
@RestController
@RequestMapping(value = "/api/adreplace", method = {RequestMethod.GET,RequestMethod.POST})
public class AdreplaceCtrl {


    @Autowired
    private AdreplaceServiceImpl adreplaceService;

    @Value("${advertfiles}")
    private String advertfilesPath;
    @Value("${api.replace.upload.path}")
    private String fileHostUploadPath;
    @Autowired
    @Qualifier("ThreadExecutor")
    private ExecutorService threadExecutor;

/*-----------------------------------------------------------------------------------------------------------文件操作-----------------------------------------------------------------------------------------------------------------------------------------*/
    @ApiOperation( value = "广告下载分发",notes = "广告下载分发api接口")
    @RequestMapping(value = "/dispatch",method = {RequestMethod.POST})
    public ResponseEntity<ResponseModel> dispatch(String targetUrl,@RequestParam(value = "isAsync",required = false,defaultValue = "false")Boolean isAsync,@RequestParam("fileList") String fileName){
        if (isAsync){
            adreplaceService.dispatchAsync(targetUrl, fileName,isAsync);
            ResponseModel model = ResponseModel.warp(BusinessEnum.EXECUTING).setData(fileName);
            return ResponseEntity.ok(model);
        }else{
            ResponseModel result = adreplaceService.dispatch(targetUrl, fileName, isAsync);
            return ResponseEntity.ok(result);
        }
    }

    @ApiOperation( value = "多广告下载分发",notes = "广告下载分发api接口")
    @RequestMapping(value = "/dispatchs",method = {RequestMethod.POST})
    public ResponseEntity<List<ResponseModel>> dispatchs(String targetUrl,@RequestParam(value = "isAsync",required = false,defaultValue = "false")Boolean isAsync,@RequestParam("fileList") String... fileList){
        if (isAsync){
            Arrays.stream(fileList).parallel().forEach(fileName -> adreplaceService.dispatchAsync(targetUrl, fileName,isAsync));
            ResponseModel model = ResponseModel.warp(BusinessEnum.EXECUTING).setData(fileList);
            List<ResponseModel> collect = Stream.of(model).collect(Collectors.toList());
            return ResponseEntity.ok(collect);
        }else{
            List<ResponseModel> result = Arrays.stream(fileList).parallel().map(fileName -> adreplaceService.dispatch(targetUrl, fileName,isAsync)).collect(Collectors.toList());
           return ResponseEntity.ok(result);
        }
    }



    @ApiOperation( value = "广告上传分发",notes = "广告上传分发api接口")
    @RequestMapping(value = "/upload", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<ResponseModel> upload(String targetUrl, @RequestParam(value = "isAsync",required = false,defaultValue = "false")Boolean isAsync,@RequestParam("fileList") String fileName) {
        if (isAsync){
            adreplaceService.upLoadAsync(targetUrl, fileName,isAsync);
            ResponseModel model = ResponseModel.warp(BusinessEnum.EXECUTING).setData(fileName);
            return ResponseEntity.ok(model);
        }else{
            ResponseModel model = adreplaceService.upLoad(targetUrl, fileName, isAsync);
            return ResponseEntity.ok(model);
        }
    }

    @ApiOperation( value = "多广告上传分发",notes = "广告上传分发api接口")


    @RequestMapping(value = "/uploads", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<List<ResponseModel>> uploads(String targetUrl, @RequestParam(value = "isAsync",required = false,defaultValue = "false")Boolean isAsync,@RequestParam("fileList") String... fileList) {
        if (isAsync){
            Arrays.stream(fileList).parallel().forEach(fileName -> adreplaceService.upLoadAsync(targetUrl, fileName,isAsync));
            ResponseModel model = ResponseModel.warp(BusinessEnum.EXECUTING).setData(fileList);
            List<ResponseModel> collect = Stream.of(model).collect(Collectors.toList());
            return ResponseEntity.ok(collect);
        }else{
            List<ResponseModel> result = Arrays.stream(fileList).parallel().map(fileName -> adreplaceService.upLoad(targetUrl, fileName,isAsync)).collect(Collectors.toList());
            return ResponseEntity.ok(result);
        }
    }


    @ApiOperation( value = "广告分发结果回调",notes = "广告分发结果回调api接口",hidden = true)
    @RequestMapping(value = "/callback",method = {RequestMethod.POST})
    public void asyncCallback(@RequestParam("report") String callbackJson){
          adreplaceService.asyncCallback(callbackJson);
    }


    @ApiOperation( value = "广告下载接口,测试使用",notes = "广告下载接口api接口",hidden = true)
    @RequestMapping(value = "/download/{fileName:.+}", method = {RequestMethod.GET, RequestMethod.POST})
    public void downLoad(@PathVariable("fileName") String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
         adreplaceService.downLoad(fileName,response);
    }


/* ---------------------------------------------------------------------------------------------广告替换操作--------------------------------------------------------------------------------------------------*/

    @ApiOperation( value = "广告替换",notes = "广告替换api接口")
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足"),
            @ApiResponse(code =4, message = "参数错误") })
    @PostMapping(value = "/advertReplace")
    public ResponseEntity<MessageModel> advertReplace(@RequestBody AdReplaceInParams adReplaceInParams){


        System.out.println(JSON.toJSONString(adReplaceInParams,SerializerFeature.WriteMapNullValue,SerializerFeature.PrettyFormat));

        //原策略Id
        String sourceAdvertPolicyId = adReplaceInParams.getSourceAdvertPolicyId();
        //新策略Id
        String newAdvertPolicyId = adReplaceInParams.getAdvertPolicyId();


        //整理要替换的信息
        List<AdReplaceInParams.AdvertInfo> advertInfoList = adReplaceInParams.getAdverts();
        advertInfoList.stream().forEach(info->{
            String advertName = adreplaceService.getFileNameByAdvertId(info.getAdvertId());
            info.setAdvertName(advertName);

            Map<String, Object> subAdvertPolicys = adreplaceService.getSubAdvertPolicysByAdvertPolicysIdAndScreenCutId(sourceAdvertPolicyId, info.getScreenCutId());

            if (!Objects.isNull(subAdvertPolicys)){
                String advertId = String.valueOf(subAdvertPolicys.get("advertId"));
                info.setSrcAdvertId(advertId);
                String oldAdvertName= adreplaceService.getFileNameByAdvertId(advertId);
                info.setSrcAdvertName(oldAdvertName);
            }else{
                throw new RuntimeException("AdvertPolicyId+ScreenCutId不唯一！");
            }
        });

        System.out.println(JSON.toJSONString(adReplaceInParams, SerializerFeature.WriteMapNullValue,SerializerFeature.PrettyFormat));

        /*1. 备份原策略.*/
        adreplaceService.advertPolicysBackup(sourceAdvertPolicyId);

        //查出原策略
        Map<String, String> advertPolicys = adreplaceService.getAdvertPolicysById(sourceAdvertPolicyId);
        //克隆一份，用于传参
        HashMap<String, Object> advertPolicysClone = new LinkedHashMap<>(advertPolicys);
        log.info("advertPolicys:{}",advertPolicys);
        // 需要替换的设备  ,devices为null 或 "" 为全部设备,否则为部分设备
        String inputDevices = adReplaceInParams.getDevices();
        String replaceDevices = StringUtils.isEmpty(inputDevices)?advertPolicys.get("devices"): inputDevices;

        /*2. 根据devices 字段 ，判断 全部、部分替换*/
        if (StringUtils.isEmpty(inputDevices)){

            //日期分成两组,大于等于今天的，作为原策略保存，小于的作为新策略保存.
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String curDate = format.format(new Date());
            String sourcePlayDates = String.valueOf(advertPolicysClone.get("playDates"));
            /* 今天(含)以前的 */
            String  srcPlayDates = Arrays.stream(sourcePlayDates.split(",")).filter(palyDate -> {
                return palyDate.compareTo(curDate) <= 0;
            }).collect(Collectors.joining(","));
            srcPlayDates = Objects.isNull(srcPlayDates)?"":srcPlayDates;

            /* 今天(含)以后的 */
            String  newPlayDates = Arrays.stream(sourcePlayDates.split(",")).filter(palyDate -> {
                return palyDate.compareTo(curDate) >= 0;
            }).collect(Collectors.joining(","));
            newPlayDates=Objects.isNull(newPlayDates)?"":newPlayDates;

            //更新原策略
            advertPolicysClone.put("advertPolicysId",sourceAdvertPolicyId);
            advertPolicysClone.put("playDates",srcPlayDates);
            adreplaceService.upsertAdvertPolicys(advertPolicysClone);


            //生成并插入新策略
            advertPolicysClone.put("advertPolicysId",newAdvertPolicyId);
            advertPolicysClone.put("playDates",newPlayDates);
            advertPolicysClone.put("sourceId",sourceAdvertPolicyId);
            adreplaceService.upsertAdvertPolicys(advertPolicysClone);



        }else {
            String sourceDevices = String.valueOf(advertPolicysClone.get("devices"));

            LinkedList<String> sourceDeviceIdList = new LinkedList<>(Arrays.asList(sourceDevices.split(",")));
            LinkedList<String> replaceDeviceIdList = new LinkedList<>(Arrays.asList(replaceDevices.split(",")));
            sourceDeviceIdList.removeAll(replaceDeviceIdList);

            String retainDevices = sourceDeviceIdList.stream().sorted().collect(Collectors.joining(","));

            //更新原策略
            advertPolicysClone.put("advertPolicysId", sourceAdvertPolicyId);
            advertPolicysClone.put("devices", retainDevices);
            adreplaceService.upsertAdvertPolicys(advertPolicysClone);


            //保存新策略
            advertPolicysClone.put("advertPolicysId", newAdvertPolicyId);
            advertPolicysClone.put("devices", replaceDevices);
            advertPolicysClone.put("sourceId", sourceAdvertPolicyId);
            adreplaceService.upsertAdvertPolicys(advertPolicysClone);

        }

            adReplaceInParams.getAdverts().stream().forEach(info->{

                Map<String, Object> paramMap = new HashMap<>();


                //TODO 新策略插入 策略子表(只插入新生成策略)
                paramMap.put("advertPolicysId",newAdvertPolicyId);
                paramMap.put("advertId",info.getAdvertId());
                paramMap.put("screenCutId",info.getScreenCutId());
                adreplaceService.upsertSubAdvertPolicys(paramMap);

                paramMap.clear();


                //TODO  插入广告历史表和广告表 新广告 nowstate->8
                paramMap.put("advertId",info.getAdvertId());
                paramMap.put("nowState",8);//8是替换状态
                paramMap.put("maker",adReplaceInParams.getLoginUserId());
                adreplaceService.updateAdvertState(paramMap);//更新
                adreplaceService.insertAdvertHisState(paramMap); //新增

                paramMap.clear();

                //TODO  插入广告历史表和广告表 原广告 nowstate->9
                paramMap.put("advertId",info.getSrcAdvertId());
                paramMap.put("nowState",9);//9是被替换状态
                paramMap.put("maker",adReplaceInParams.getLoginUserId());
                adreplaceService.updateAdvertState(paramMap);//更新
                adreplaceService.insertAdvertHisState(paramMap);//更新

                paramMap.clear();

                // TODO  修改每日播放策略文件表 ad.t_advert_day_policy_role,更新需替换设备对应的所有广告
                paramMap.put("newFileName", info.getAdvertName());
                paramMap.put("oldFileName",info.getSrcAdvertName());
                String replaceDevicesStr = Arrays.stream(replaceDevices.split(",")).collect(Collectors.joining("','"));
                paramMap.put("replaceDevices",replaceDevicesStr);
                adreplaceService.updateDayPolicysRole(paramMap);

            });


            //TODO  通知APP 进行替换
        List<Object> srcFileList = adReplaceInParams.getAdverts().stream().map(info -> {
            return info.getSrcAdvertName();
        }).collect(Collectors.toList());
        List<Object> newFileList = adReplaceInParams.getAdverts().stream().map(info -> {
            return info.getAdvertName();
        }).collect(Collectors.toList());

        HashMap<String, Object> cmdMap = new HashMap<>();
        cmdMap.put("action","stop");
        cmdMap.put("data",srcFileList);
        String stopCmd = JSON.toJSONString(cmdMap);
            Arrays.stream(replaceDevices.split(",")).forEach(id-> WsSessionManager.sendActionByDeviceId(id,stopCmd));

            // TODO 异步执行
            threadExecutor.execute(()->{
                String replaceDevicesStr = Arrays.stream(replaceDevices.split(",")).collect(Collectors.joining("','"));
                List<Map<String, String>> filehostAddressList = adreplaceService.getFilehostAddress(replaceDevicesStr);
                //  TODO 上传广告文件到市州文件服务器
                filehostAddressList.stream().filter(map->Pattern.matches("(http|https)://([\\w.:]+|[\\w.:]+/)", map.get("ip"))).forEach(map->{
                        String url = map.get("ip")+fileHostUploadPath;
                        log.info("url："+url);
                        /*测试使用*/
                        /*String url = "http://192.168.1.99:8008/api/replace/upload";*/
                            advertInfoList.parallelStream().forEach(info->{
                                try {
                                    /*String advertName = adreplaceService.getFileNameByAdvertId(info.getAdvertId());*/
                                    ResponseModel model = adreplaceService.upLoad(url, info.getAdvertName(),false);
                                    log.info("=== 上传結果 ===：{}",model);
                                } catch (Exception e) {
                                    log.error("=== 上传错误 ===,url:{}",url,e);
                                }
                            });

                });


                // TODO 通知设备
                HashMap<String, Object> cmdReplaceMap = new HashMap<>();
                cmdReplaceMap.put("action","replace");
                cmdReplaceMap.put("data",newFileList);
                String replaceCmd = JSON.toJSONString(cmdReplaceMap);
                Arrays.stream(replaceDevices.split(",")).forEach(id-> WsSessionManager.sendActionByDeviceId(id,replaceCmd));
            });

            /*返回*/
        MessageModel m = new MessageModel();
        m.setResultCode("1");
        m.setResultMsg("ok");
        m.setData("");
        return ResponseEntity.ok(m);
    }

}
