package com.en.adback.controller;


import com.alibaba.fastjson.JSON;
import com.en.adback.common.Common;
import com.en.adback.common.MessageModel;
import com.en.adback.controller.sys.UserLogs;
import com.en.adback.entity.advertmgr.Advert;
import com.en.adback.entity.advertmgr.AdvertPutIn;
import com.en.adback.service.PlayCertifyExcelService;
import io.swagger.annotations.*;
import org.apache.avro.data.Json;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Api(value="播出证明单导出Excel",tags={"播出证明单导出Excel"})
@RestController
@CrossOrigin
@RequestMapping(value = "/api/certify", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
public class playCertifyExcelCtrl {
    @Autowired
    private PlayCertifyExcelService svr;
    @Autowired private UserLogs ulogs;

    private static SimpleDateFormat sdfDD=new SimpleDateFormat("dd");
    private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

    @ApiOperation( value = "条件查询导出Excel，广告审核",notes = "条件查询，广告审核" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "advertName", value = "广告名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "tradeId", value = "行业ID", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "adCorpName", value = "广告公司名称", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "blankId", value = "品牌id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uploadTimeBegin", value = "上传日期起", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uploadTimeEnd", value = "上传日期止", required = false, dataType = "String", paramType = "query"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/getPlaycertifyExcel1")
    public MessageModel getPlaycertifyExcel(@RequestBody Map<String,Object> param, HttpServletRequest request) throws ParseException {
        MessageModel model=new MessageModel();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("beginDate", param.get("beginDate"));
        map.put("endDate", param.get("endDate"));
        map.put("advertId", param.get("advertId"));
        map.put("advertName", param.get("advertName"));
        map.put("putInKind", param.get("putInKind"));
        map.put("playAlone", param.get("playAlone"));
        map.put("choosedDevices", param.get("choosedDevices"));
        map.put("policyName", param.get("policyName"));
        map.put("detail", param.get("detail"));
        map.put("screen", param.get("screen"));
        map.put("screenPosition", param.get("screenPosition"));
        map.put("deviceIds", param.get("deviceIds"));
        map.put("duration", param.get("duration"));
        List<Map<String,Object>> list = svr.getPlayCertifyExcel(map);
        List<Map<String,Object>> list1 = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> list4 = new ArrayList<Map<String,Object>>();

        List<Map<String,Object>> list3 = new ArrayList<Map<String,Object>>();
        for (int i =0;i< list.size();i++) {
            String policyName = list.get(i).get("POLICYNAME").toString().substring(0,2);
            String screenName = list.get(i).get("SCREENNAME").toString();
            if(screenName.indexOf(policyName) != -1){
                list4.add(list.get(i));
            }
        }
        String str = map.get("deviceIds").toString();
        String[] strings = str.split(",");
        for(int j=0;j<strings.length;j++){
            String deviceId = strings[j];
            if(null == deviceId   || "".equals(deviceId)){
                break;
            }
            for(int i=0;i<list4.size();i++){
                if(deviceId.split("\\|")[0].equals(list4.get(i).get("DEVICEID"))){
                    list1.add(list4.get(i));
                }
            }

            Date beginDates = new SimpleDateFormat("yyyy-MM-dd").parse(map.get("beginDate").toString());
            Date endDates = new SimpleDateFormat("yyyy-MM-dd").parse(map.get("endDate").toString());
            Calendar begin = Calendar.getInstance();
            begin.setTime(beginDates);
            int monthbegin = begin.get(Calendar.MONTH);//第几个月
            Calendar end = Calendar.getInstance();
            end.setTime(endDates);
            int monthend = end.get(Calendar.MONTH);//第几个月
            if(monthbegin < monthend){
                for(int i=0;i<monthend-monthbegin;i++){
                    int num = monthbegin+1+i;
                    if(list1.size() == 0){
                        Map<String,Object> map1 = new HashMap<>();
                        map1.put("advertId",map.get("advertId"));
                        map1.put("advertName",map.get("advertName"));
                        map1.put("putInKind",map.get("putInKind"));
                        map1.put("deviceIds",map.get("deviceIds"));
                        map1.put("playAlone",map.get("playAlone"));
                        map1.put("tjrq",map.get("beginDate")+"至"+map.get("endDate"));
                        map1.put("choosedDevices",map.get("choosedDevices").toString());
                        map1.put("policyName",map.get("policyName").toString());
                        map1.put("detail",map.get("detail"));
                        map1.put("screen",map.get("screen"));
                        map1.put("screenPosition",map.get("screenPosition"));
                        map1.put("deviceId",deviceId.split("\\|")[0]);
                        map1.put("deviceIdName",deviceId.split("\\|")[1]);
                        map1.put("duration",map.get("duration"));
                        map1.put("month",end.get(Calendar.YEAR)+"-"+ num);
                        map1.put("d1",0);
                        map1.put("d2",0);
                        map1.put("d3",0);
                        map1.put("d4",0);
                        map1.put("d5",0);
                        map1.put("d6",0);
                        map1.put("d7",0);
                        map1.put("d8",0);
                        map1.put("d9",0);
                        map1.put("d10",0);
                        map1.put("d11",0);
                        map1.put("d12",0);
                        map1.put("d13",0);
                        map1.put("d14",0);
                        map1.put("d15",0);
                        map1.put("d16",0);
                        map1.put("d17",0);
                        map1.put("d18",0);
                        map1.put("d19",0);
                        map1.put("d20",0);
                        map1.put("d21",0);
                        map1.put("d22",0);
                        map1.put("d23",0);
                        map1.put("d24",0);
                        map1.put("d25",0);
                        map1.put("d26",0);
                        map1.put("d27",0);
                        map1.put("d28",0);
                        map1.put("d29",0);
                        map1.put("d30",0);
                        map1.put("d31",0);
                        list3.add(map1);
                    }else{
                        List<Map<String,Object>> list2 = list1.stream().filter(a -> Integer.parseInt(a.get("YUE").toString()) == num).collect(Collectors.toList());
                        Map<String,Object> map1 = new HashMap<>();
                        int d1 = 0;int d2 = 0;int d3 = 0;int d4 = 0;int d5 = 0;int d6 = 0;int d7 = 0;int d8 = 0;int d9 = 0;
                        int d10 = 0;int d11 = 0;int d12 = 0;int d13 = 0;int d14 = 0;int d15 = 0;int d16 = 0;int d17 = 0;int d18 = 0;
                        int d19 = 0;int d20 = 0;int d21 = 0;int d22 = 0;int d23 = 0;int d24 = 0;int d25 = 0;int d26 = 0;int d27 = 0;int d28 = 0;
                        int d29 = 0;int d30 = 0;int d31 = 0;
                        if(list2.size() == 0){
                            Map<String,Object> map3 = new HashMap<>();
                            map3.put("advertId",map.get("advertId"));
                            map3.put("advertName",map.get("advertName"));
                            map3.put("putInKind",map.get("putInKind"));
                            map3.put("deviceIds",map.get("deviceIds"));
                            map3.put("playAlone",map.get("playAlone"));
                            map3.put("tjrq",map.get("beginDate")+"至"+map.get("endDate"));
                            map3.put("choosedDevices",map.get("choosedDevices").toString());
                            map3.put("policyName",map.get("policyName").toString());
                            map3.put("detail",map.get("detail"));
                            map3.put("screen",map.get("screen"));
                            map3.put("screenPosition",map.get("screenPosition"));
                            map3.put("deviceId",deviceId.split("\\|")[0]);
                            map3.put("deviceIdName",deviceId.split("\\|")[1]);
                            map3.put("duration",map.get("duration"));
                            map3.put("month",end.get(Calendar.YEAR)+"-"+ num);
                            map3.put("d1",0);
                            map3.put("d2",0);
                            map3.put("d3",0);
                            map3.put("d4",0);
                            map3.put("d5",0);
                            map3.put("d6",0);
                            map3.put("d7",0);
                            map3.put("d8",0);
                            map3.put("d9",0);
                            map3.put("d10",0);
                            map3.put("d11",0);
                            map3.put("d12",0);
                            map3.put("d13",0);
                            map3.put("d14",0);
                            map3.put("d15",0);
                            map3.put("d16",0);
                            map3.put("d17",0);
                            map3.put("d18",0);
                            map3.put("d19",0);
                            map3.put("d20",0);
                            map3.put("d21",0);
                            map3.put("d22",0);
                            map3.put("d23",0);
                            map3.put("d24",0);
                            map3.put("d25",0);
                            map3.put("d26",0);
                            map3.put("d27",0);
                            map3.put("d28",0);
                            map3.put("d29",0);
                            map3.put("d30",0);
                            map3.put("d31",0);
                            list3.add(map3);
                        }else{
                            for(int s=0;s<list2.size();s++){
                                map1.put("advertId",map.get("advertId"));
                                map1.put("advertName",map.get("advertName"));
                                map1.put("putInKind",map.get("putInKind"));
                                map1.put("deviceIds",map.get("deviceIds"));
                                map1.put("playAlone",map.get("playAlone"));
                                map1.put("tjrq",map.get("beginDate")+"至"+map.get("endDate"));
                                map1.put("choosedDevices",map.get("choosedDevices").toString());
                                map1.put("policyName",map.get("policyName").toString());
                                map1.put("detail",map.get("detail"));
                                map1.put("screen",map.get("screen"));
                                map1.put("screenPosition",map.get("screenPosition"));
                                map1.put("deviceId",deviceId.split("\\|")[0]);
                                map1.put("deviceIdName",deviceId.split("\\|")[1]);
                                map1.put("duration",map.get("duration"));
                                map1.put("month",list2.get(i).get("NIAN")+"-"+ list2.get(i).get("YUE"));
                                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(list2.get(i).get("RQ").toString());
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(date);
                                int DAY_OF_MONTH = end.get(Calendar.DAY_OF_MONTH);//第几号
                                map1.put("d1",DAY_OF_MONTH == 1 ? ++d1: d1);
                                map1.put("d2",DAY_OF_MONTH == 2 ? ++d2: d2);
                                map1.put("d3",DAY_OF_MONTH == 3 ? ++d3: d3);
                                map1.put("d4",DAY_OF_MONTH == 4 ? ++d4: d4);
                                map1.put("d5",DAY_OF_MONTH == 5 ? ++d5: d5);
                                map1.put("d6",DAY_OF_MONTH == 6 ? ++d6: d6);
                                map1.put("d7",DAY_OF_MONTH == 7 ? ++d7: d7);
                                map1.put("d8",DAY_OF_MONTH == 8 ? ++d8: d8);
                                map1.put("d9",DAY_OF_MONTH == 9 ? ++d9: d9);
                                map1.put("d10",DAY_OF_MONTH == 10 ? ++d10: d10);
                                map1.put("d11",DAY_OF_MONTH == 11 ? ++d11: d11);
                                map1.put("d12",DAY_OF_MONTH == 12 ? ++d12: d12);
                                map1.put("d13",DAY_OF_MONTH == 13 ? ++d13: d13);
                                map1.put("d14",DAY_OF_MONTH == 14 ? ++d14: d14);
                                map1.put("d15",DAY_OF_MONTH == 15 ? ++d15: d15);
                                map1.put("d16",DAY_OF_MONTH == 16 ? ++d16: d16);
                                map1.put("d17",DAY_OF_MONTH == 17 ? ++d17: d17);
                                map1.put("d18",DAY_OF_MONTH == 18 ? ++d18: d18);
                                map1.put("d19",DAY_OF_MONTH == 19 ? ++d19: d19);
                                map1.put("d20",DAY_OF_MONTH == 20 ? ++d20: d20);
                                map1.put("d21",DAY_OF_MONTH == 21 ? ++d21: d21);
                                map1.put("d22",DAY_OF_MONTH == 22 ? ++d22: d22);
                                map1.put("d23",DAY_OF_MONTH == 23 ? ++d23: d23);
                                map1.put("d24",DAY_OF_MONTH == 24 ? ++d24: d24);
                                map1.put("d25",DAY_OF_MONTH == 25 ? ++d25: d25);
                                map1.put("d26",DAY_OF_MONTH == 26 ? ++d26: d26);
                                map1.put("d27",DAY_OF_MONTH == 27 ? ++d27: d27);
                                map1.put("d28",DAY_OF_MONTH == 28 ? ++d28: d28);
                                map1.put("d29",DAY_OF_MONTH == 29 ? ++d29: d29);
                                map1.put("d30",DAY_OF_MONTH == 30 ? ++d30: d30);
                                map1.put("d31",DAY_OF_MONTH == 31 ? ++d31: d31);
                            }
                        }
                        list3.add(map1);
                    }


                }
            }else{
                Map<String,Object> map1 = new HashMap<>();
                int d1 = 0;int d2 = 0;int d3 = 0;int d4 = 0;int d5 = 0;int d6 = 0;int d7 = 0;int d8 = 0;int d9 = 0;
                int d10 = 0;int d11 = 0;int d12 = 0;int d13 = 0;int d14 = 0;int d15 = 0;int d16 = 0;int d17 = 0;int d18 = 0;
                int d19 = 0;int d20 = 0;int d21 = 0;int d22 = 0;int d23 = 0;int d24 = 0;int d25 = 0;int d26 = 0;int d27 = 0;int d28 = 0;
                int d29 = 0;int d30 = 0;int d31 = 0;
                if(list1.size() == 0){
                    map1.put("advertId",map.get("advertId"));
                    map1.put("advertName",map.get("advertName"));
                    map1.put("putInKind",map.get("putInKind"));
                    map1.put("deviceIds",map.get("deviceIds"));
                    map1.put("playAlone",map.get("playAlone"));
                    map1.put("tjrq",map.get("beginDate")+"至"+map.get("endDate"));
                    map1.put("choosedDevices",map.get("choosedDevices").toString());
                    map1.put("policyName",map.get("policyName").toString());
                    map1.put("detail",map.get("detail"));
                    map1.put("screen",map.get("screen"));
                    map1.put("screenPosition",map.get("screenPosition"));
                    map1.put("deviceId",deviceId.split("\\|")[0]);
                    map1.put("deviceIdName",deviceId.split("\\|")[1]);
                    map1.put("duration",map.get("duration"));
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(map.get("beginDate").toString());
                    Calendar ss = Calendar.getInstance();
                    ss.setTime(date);
                    int DAY_OF_MONTH = ss.get(Calendar.DAY_OF_MONTH);//第几号
                    map1.put("month",ss.get(Calendar.YEAR)+"-"+ss.get(Calendar.MONTH)+1);
                    map1.put("d1",0);
                    map1.put("d2",0);
                    map1.put("d3",0);
                    map1.put("d4",0);
                    map1.put("d5",0);
                    map1.put("d6",0);
                    map1.put("d7",0);
                    map1.put("d8",0);
                    map1.put("d9",0);
                    map1.put("d10",0);
                    map1.put("d11",0);
                    map1.put("d12",0);
                    map1.put("d13",0);
                    map1.put("d14",0);
                    map1.put("d15",0);
                    map1.put("d16",0);
                    map1.put("d17",0);
                    map1.put("d18",0);
                    map1.put("d19",0);
                    map1.put("d20",0);
                    map1.put("d21",0);
                    map1.put("d22",0);
                    map1.put("d23",0);
                    map1.put("d24",0);
                    map1.put("d25",0);
                    map1.put("d26",0);
                    map1.put("d27",0);
                    map1.put("d28",0);
                    map1.put("d29",0);
                    map1.put("d30",0);
                    map1.put("d31",0);
                    list3.add(map1);
                }else{
                    for(int s=0;s<list1.size();s++){
                        map1.put("advertId",map.get("advertId"));
                        map1.put("advertName",map.get("advertName"));
                        map1.put("putInKind",map.get("putInKind"));
                        map1.put("deviceIds",map.get("deviceIds"));
                        map1.put("playAlone",map.get("playAlone"));
                        map1.put("tjrq",map.get("beginDate")+"至"+map.get("endDate"));
                        map1.put("choosedDevices",map.get("choosedDevices").toString());
                        map1.put("policyName",map.get("policyName").toString());
                        map1.put("detail",map.get("detail"));
                        map1.put("screen",map.get("screen"));
                        map1.put("screenPosition",map.get("screenPosition"));
                        map1.put("deviceId",deviceId.split("\\|")[0]);
                        map1.put("deviceIdName",deviceId.split("\\|")[1]);
                        map1.put("duration",map.get("duration"));
                        map1.put("month",list1.get(s).get("NIAN")+"-"+ list1.get(s).get("YUE"));
                        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(list1.get(s).get("RQ").toString());
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        int DAY_OF_MONTH = calendar.get(Calendar.DAY_OF_MONTH);//第几号
                        map1.put("d1",DAY_OF_MONTH == 1 ? ++d1: d1);
                        map1.put("d2",DAY_OF_MONTH == 2 ? ++d2: d2);
                        map1.put("d3",DAY_OF_MONTH == 3 ? ++d3: d3);
                        map1.put("d4",DAY_OF_MONTH == 4 ? ++d4: d4);
                        map1.put("d5",DAY_OF_MONTH == 5 ? ++d5: d5);
                        map1.put("d6",DAY_OF_MONTH == 6 ? ++d6: d6);
                        map1.put("d7",DAY_OF_MONTH == 7 ? ++d7: d7);
                        map1.put("d8",DAY_OF_MONTH == 8 ? ++d8: d8);
                        map1.put("d9",DAY_OF_MONTH == 9 ? ++d9: d9);
                        map1.put("d10",DAY_OF_MONTH == 10 ? ++d10: d10);
                        map1.put("d11",DAY_OF_MONTH == 11 ? ++d11: d11);
                        map1.put("d12",DAY_OF_MONTH == 12 ? ++d12: d12);
                        map1.put("d13",DAY_OF_MONTH == 13 ? ++d13: d13);
                        map1.put("d14",DAY_OF_MONTH == 14 ? ++d14: d14);
                        map1.put("d15",DAY_OF_MONTH == 15 ? ++d15: d15);
                        map1.put("d16",DAY_OF_MONTH == 16 ? ++d16: d16);
                        map1.put("d17",DAY_OF_MONTH == 17 ? ++d17: d17);
                        map1.put("d18",DAY_OF_MONTH == 18 ? ++d18: d18);
                        map1.put("d19",DAY_OF_MONTH == 19 ? ++d19: d19);
                        map1.put("d20",DAY_OF_MONTH == 20 ? ++d20: d20);
                        map1.put("d21",DAY_OF_MONTH == 21 ? ++d21: d21);
                        map1.put("d22",DAY_OF_MONTH == 22 ? ++d22: d22);
                        map1.put("d23",DAY_OF_MONTH == 23 ? ++d23: d23);
                        map1.put("d24",DAY_OF_MONTH == 24 ? ++d24: d24);
                        map1.put("d25",DAY_OF_MONTH == 25 ? ++d25: d25);
                        map1.put("d26",DAY_OF_MONTH == 26 ? ++d26: d26);
                        map1.put("d27",DAY_OF_MONTH == 27 ? ++d27: d27);
                        map1.put("d28",DAY_OF_MONTH == 28 ? ++d28: d28);
                        map1.put("d29",DAY_OF_MONTH == 29 ? ++d29: d29);
                        map1.put("d30",DAY_OF_MONTH == 30 ? ++d30: d30);
                        map1.put("d31",DAY_OF_MONTH == 31 ? ++d31: d31);
                    }
                    list3.add(map1);
                }
            }
        }
        if(list3.size() != 0){
            try {
                PlaycertifyExcel(list3);
                map.clear();
                map.put("url","static/excels/playcertify.xls");
                model.setData(map);
            } catch (IOException e) {
                model.setData(2);
                e.printStackTrace();
            }
        }
        model.setResultCode("1");
        model.setResultMsg("success");


        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(param.get("loginUserId").toString(),param.get("loginGroupRoleId").toString(),param.get("loginRoleId").toString(),ip,"播出证明单导出Excel");
        return model;
    }




    //将数据写入表格里   播出日志表
    public void PlaycertifyExcel(List<Map<String,Object>> list) throws IOException {
        Resource resource=new ClassPathResource("/static/excels/playcertify.xls");
        File file=resource.getFile();
        HSSFWorkbook wb=new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet sheet=wb.getSheetAt(0);
        sheet.setDefaultColumnWidth(15);
        int rowCount=1;
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            sheet.removeRow(sheet.getRow(i));
        }
        if(list.size() == 0){
            return;
        }
        //创建表头单元格样式 以及表头的字体样式
        HSSFCellStyle style = wb.createCellStyle();
        style.setWrapText(true);// 设置自动换行
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setFillBackgroundColor(HSSFColor.WHITE.index);//设置背景色
        style.setFillForegroundColor(HSSFColor.WHITE.index);//设置前景色
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        HSSFFont headerFont = (HSSFFont) wb.createFont(); // 创建字体样式
        headerFont.setFontName("宋体"); // 设置字体类型
        headerFont.setFontHeightInPoints((short) 12); // 设置字体大小
        headerFont.setColor(HSSFColor.BLACK.index);  //设置字体颜色
        style.setFont(headerFont); // 为标题样式设置字体样式

        int num = 0;
        //将信息写入表中
        for (Map<String,Object> log:list){
            HSSFRow row=sheet.createRow(rowCount);


            Cell advertId = row.createCell(0); // 创建指定单元格对象。如本身有数据会替换掉
            advertId.setCellValue(log.get("advertId").toString()); // 广告id
            advertId.setCellStyle(style);

            Cell advertName = row.createCell(1); // 创建指定单元格对象。如本身有数据会替换掉
            advertName.setCellValue(log.get("advertName").toString()); // 广告名称
            advertName.setCellStyle(style);

            Cell putInKind = row.createCell(2); // 创建指定单元格对象。如本身有数据会替换掉
            putInKind.setCellValue(log.get("putInKind").toString()); // 投放类别
            putInKind.setCellStyle(style);

            Cell deviceIds = row.createCell(3); // 创建指定单元格对象。如本身有数据会替换掉
            deviceIds.setCellValue(log.get("playAlone").toString()); // 播放形式
            deviceIds.setCellStyle(style);

            Cell playAlone = row.createCell(4); // 创建指定单元格对象。如本身有数据会替换掉
            playAlone.setCellValue(log.get("tjrq").toString()); // 统计日期
            playAlone.setCellStyle(style);


            Cell choosedDevices = row.createCell(5); // 创建指定单元格对象。如本身有数据会替换掉
            choosedDevices.setCellValue(log.get("choosedDevices").toString()); // 设备数量
            choosedDevices.setCellStyle(style);

            Cell policyName = row.createCell(6);
            policyName.setCellValue(log.get("policyName").toString()); // 播放策略
            policyName.setCellStyle(style);

            Cell detail = row.createCell(7);
            detail.setCellValue(log.get("detail").toString()); // 策略详情
            detail.setCellStyle(style);

            Cell cellPolicyName = row.createCell(8);
            cellPolicyName.setCellValue(log.get("screen").toString()); // 屏幕模式
            cellPolicyName.setCellStyle(style);

            Cell screenPosition = row.createCell(9);
            screenPosition.setCellValue(log.get("screenPosition").toString()); // 屏幕位置
            screenPosition.setCellStyle(style);

            Cell cellScreenPosition = row.createCell(10);
            cellScreenPosition.setCellValue(log.get("deviceId").toString()); // 设备编号
            cellScreenPosition.setCellStyle(style);

            Cell cellFormerAdvertising = row.createCell(11);
            cellFormerAdvertising.setCellValue(log.get("deviceIdName").toString()); // 场所名称
            cellFormerAdvertising.setCellStyle(style);

            Cell duration = row.createCell(12);
            duration.setCellValue(log.get("duration").toString()); // 时长
            duration.setCellStyle(style);

            Cell month = row.createCell(13);
            month.setCellValue(log.get("month").toString()); // 月份
            month.setCellStyle(style);


            Cell d1 = row.createCell(14);
            d1.setCellValue(log.get("d1").toString()); // d1
            d1.setCellStyle(style);

            Cell d2 = row.createCell(15);
            d2.setCellValue(log.get("d2").toString()); // d2
            d2.setCellStyle(style);

            Cell d3 = row.createCell(16);
            d3.setCellValue(log.get("d3").toString()); // d3
            d3.setCellStyle(style);

            Cell d4 = row.createCell(17);
            d4.setCellValue(log.get("d4").toString()); // d4
            d4.setCellStyle(style);

            Cell d5 = row.createCell(18);
            d5.setCellValue(log.get("d5").toString()); // d5
            d5.setCellStyle(style);

            Cell d6 = row.createCell(19);
            d6.setCellValue(log.get("d6").toString()); // d6
            d6.setCellStyle(style);

            Cell d7 = row.createCell(20);
            d7.setCellValue(log.get("d7").toString()); // d7
            d7.setCellStyle(style);

            Cell d8 = row.createCell(21);
            d8.setCellValue(log.get("d8").toString()); // d8
            d8.setCellStyle(style);

            Cell d9 = row.createCell(22);
            d9.setCellValue(log.get("d9").toString()); // d9
            d9.setCellStyle(style);

            Cell d10 = row.createCell(23);
            d10.setCellValue(log.get("d10").toString()); // d10
            d10.setCellStyle(style);

            Cell d11 = row.createCell(24);
            d11.setCellValue(log.get("d11").toString()); // d11
            d11.setCellStyle(style);

            Cell d12 = row.createCell(25);
            d12.setCellValue(log.get("d12").toString()); // d12
            d12.setCellStyle(style);

            Cell d13 = row.createCell(26);
            d13.setCellValue(log.get("d13").toString()); // d13
            d13.setCellStyle(style);

            Cell d14 = row.createCell(27);
            d14.setCellValue(log.get("d14").toString()); // d14
            d14.setCellStyle(style);

            Cell d15 = row.createCell(28);
            d15.setCellValue(log.get("d15").toString()); // d15
            d15.setCellStyle(style);

            Cell d16 = row.createCell(29);
            d16.setCellValue(log.get("d16").toString()); // d16
            d16.setCellStyle(style);

            Cell d17 = row.createCell(30);
            d17.setCellValue(log.get("d17").toString()); // d17
            d17.setCellStyle(style);

            Cell d18 = row.createCell(31);
            d18.setCellValue(log.get("d18").toString()); // d18
            d18.setCellStyle(style);

            Cell d19 = row.createCell(32);
            d19.setCellValue(log.get("d19").toString()); // d19
            d19.setCellStyle(style);

            Cell d20 = row.createCell(33);
            d20.setCellValue(log.get("d20").toString()); // d20
            d20.setCellStyle(style);

            Cell d21 = row.createCell(34);
            d21.setCellValue(log.get("d21").toString()); // d21
            d21.setCellStyle(style);

            Cell d22 = row.createCell(35);
            d22.setCellValue(log.get("d22").toString()); // d22
            d22.setCellStyle(style);

            Cell d23 = row.createCell(36);
            d23.setCellValue(log.get("d23").toString()); // d23
            d23.setCellStyle(style);

            Cell d24 = row.createCell(37);
            d24.setCellValue(log.get("d24").toString()); // d24
            d24.setCellStyle(style);

            Cell d25 = row.createCell(38);
            d25.setCellValue(log.get("d25").toString()); // d25
            d25.setCellStyle(style);

            Cell d26 = row.createCell(39);
            d26.setCellValue(log.get("d26").toString()); // d26
            d26.setCellStyle(style);

            Cell d27 = row.createCell(40);
            d27.setCellValue(log.get("d27").toString()); // d27
            d27.setCellStyle(style);

            Cell d28 = row.createCell(41);
            d28.setCellValue(log.get("d28").toString()); // d28
            d28.setCellStyle(style);

            Cell d29 = row.createCell(42);
            d29.setCellValue(log.get("d29").toString()); // d29
            d29.setCellStyle(style);

            Cell d30 = row.createCell(43);
            d30.setCellValue(log.get("d30").toString()); // d30
            d30.setCellStyle(style);

            Cell d31 = row.createCell(44);
            d31.setCellValue(log.get("d31").toString()); // d31
            d31.setCellStyle(style);

            Cell hj = row.createCell(45);
            num = num + Integer.parseInt(log.get("d1").toString())+Integer.parseInt(log.get("d2").toString())+Integer.parseInt(log.get("d3").toString())
                    +Integer.parseInt(log.get("d4").toString())+Integer.parseInt(log.get("d5").toString())+Integer.parseInt(log.get("d6").toString())+Integer.parseInt(log.get("d7").toString())
                    +Integer.parseInt(log.get("d8").toString())+Integer.parseInt(log.get("d9").toString())+Integer.parseInt(log.get("d10").toString())+Integer.parseInt(log.get("d11").toString())
                    +Integer.parseInt(log.get("d12").toString())+Integer.parseInt(log.get("d13").toString())+Integer.parseInt(log.get("d14").toString())+Integer.parseInt(log.get("d15").toString())
                    +Integer.parseInt(log.get("d16").toString())+Integer.parseInt(log.get("d17").toString())+Integer.parseInt(log.get("d18").toString())+Integer.parseInt(log.get("d19").toString())
                    +Integer.parseInt(log.get("d20").toString())+Integer.parseInt(log.get("d21").toString())+Integer.parseInt(log.get("d22").toString())+Integer.parseInt(log.get("d23").toString())
                    +Integer.parseInt(log.get("d24").toString())+Integer.parseInt(log.get("d25").toString())+Integer.parseInt(log.get("d26").toString())
                    +Integer.parseInt(log.get("d27").toString())+Integer.parseInt(log.get("d28").toString())+Integer.parseInt(log.get("d29").toString())+Integer.parseInt(log.get("d30").toString())+Integer.parseInt(log.get("d31").toString());

            hj.setCellValue(Integer.parseInt(log.get("d1").toString())+Integer.parseInt(log.get("d2").toString())+Integer.parseInt(log.get("d3").toString())
                    +Integer.parseInt(log.get("d4").toString())+Integer.parseInt(log.get("d5").toString())+Integer.parseInt(log.get("d6").toString())+Integer.parseInt(log.get("d7").toString())
                    +Integer.parseInt(log.get("d8").toString())+Integer.parseInt(log.get("d9").toString())+Integer.parseInt(log.get("d10").toString())+Integer.parseInt(log.get("d11").toString())
                    +Integer.parseInt(log.get("d12").toString())+Integer.parseInt(log.get("d13").toString())+Integer.parseInt(log.get("d14").toString())+Integer.parseInt(log.get("d15").toString())
                    +Integer.parseInt(log.get("d16").toString())+Integer.parseInt(log.get("d17").toString())+Integer.parseInt(log.get("d18").toString())+Integer.parseInt(log.get("d19").toString())
                    +Integer.parseInt(log.get("d20").toString())+Integer.parseInt(log.get("d21").toString())+Integer.parseInt(log.get("d22").toString())+Integer.parseInt(log.get("d23").toString())
                    +Integer.parseInt(log.get("d24").toString())+Integer.parseInt(log.get("d25").toString())+Integer.parseInt(log.get("d26").toString())
                    +Integer.parseInt(log.get("d27").toString())+Integer.parseInt(log.get("d28").toString())+Integer.parseInt(log.get("d29").toString())+Integer.parseInt(log.get("d30").toString())+Integer.parseInt(log.get("d31").toString())); // d31
            hj.setCellStyle(style);

            rowCount++;
        }
        int num1 = 0;
        int num2 = 0;
        int num3 = 0;
        int num4 = 0;
        int num5 = 0;
        int num6 = 0;
        int num7 = 0;
        int num8 = 0;
        int num9 = 0;
        int num10 = 0;
        int num11 = 0;
        int num12 = 0;
        int num13 = 0;
        int num14 = 0;
        int num15 = 0;
        int num16 = 0;
        int num17 = 0;
        int num18 = 0;
        int num19 = 0;
        int num20 = 0;
        int num21 = 0;
        int num22 = 0;
        int num23 = 0;
        int num24 = 0;
        int num25 = 0;
        int num26 = 0;
        int num27 = 0;
        int num28 = 0;
        int num29 = 0;
        int num30 = 0;
        int num31 = 0;
        for (Map<String,Object> log:list){
            num1 = num1 + Integer.parseInt(log.get("d1").toString());
            num2 = num2 + Integer.parseInt(log.get("d2").toString());
            num3 = num3 + Integer.parseInt(log.get("d3").toString());
            num4 = num4 + Integer.parseInt(log.get("d4").toString());
            num5 = num5 + Integer.parseInt(log.get("d5").toString());
            num6 = num6 + Integer.parseInt(log.get("d6").toString());
            num7 = num7 + Integer.parseInt(log.get("d7").toString());
            num8 = num8 + Integer.parseInt(log.get("d8").toString());
            num9 = num9 + Integer.parseInt(log.get("d9").toString());
            num10 = num10 + Integer.parseInt(log.get("d10").toString());
            num11 = num11 + Integer.parseInt(log.get("d11").toString());
            num12 = num12 + Integer.parseInt(log.get("d12").toString());
            num13 = num13 + Integer.parseInt(log.get("d13").toString());
            num14 = num14 + Integer.parseInt(log.get("d14").toString());
            num15 = num15 + Integer.parseInt(log.get("d15").toString());
            num16 = num16 + Integer.parseInt(log.get("d16").toString());
            num17 = num17 + Integer.parseInt(log.get("d17").toString());
            num18 = num18 + Integer.parseInt(log.get("d18").toString());
            num19 = num19 + Integer.parseInt(log.get("d19").toString());
            num20 = num20 + Integer.parseInt(log.get("d20").toString());
            num21 = num21 + Integer.parseInt(log.get("d21").toString());
            num22 = num22 + Integer.parseInt(log.get("d22").toString());
            num23 = num23 + Integer.parseInt(log.get("d23").toString());
            num24 = num24 + Integer.parseInt(log.get("d24").toString());
            num25 = num25 + Integer.parseInt(log.get("d25").toString());
            num26 = num26 + Integer.parseInt(log.get("d26").toString());
            num27 = num27 + Integer.parseInt(log.get("d27").toString());
            num28 = num28 + Integer.parseInt(log.get("d28").toString());
            num29 = num29 + Integer.parseInt(log.get("d29").toString());
            num30 = num30 + Integer.parseInt(log.get("d30").toString());
            num31 = num31 + Integer.parseInt(log.get("d31").toString());
        }
        HSSFRow row=sheet.createRow(rowCount);
        Cell hjs = row.createCell(13);
        hjs.setCellValue("合计"); // d1
        hjs.setCellStyle(style);

        Cell d1 = row.createCell(14);
        d1.setCellValue(num1); // d1
        d1.setCellStyle(style);

        Cell d2 = row.createCell(15);
        d2.setCellValue(num2); // d2
        d2.setCellStyle(style);

        Cell d3 = row.createCell(16);
        d3.setCellValue(num3); // d3
        d3.setCellStyle(style);

        Cell d4 = row.createCell(17);
        d4.setCellValue(num4); // d4
        d4.setCellStyle(style);

        Cell d5 = row.createCell(18);
        d5.setCellValue(num5); // d5
        d5.setCellStyle(style);

        Cell d6 = row.createCell(19);
        d6.setCellValue(num6); // d6
        d6.setCellStyle(style);

        Cell d7 = row.createCell(20);
        d7.setCellValue(num7); // d7
        d7.setCellStyle(style);

        Cell d8 = row.createCell(21);
        d8.setCellValue(num8); // d8
        d8.setCellStyle(style);

        Cell d9 = row.createCell(22);
        d9.setCellValue(num9); // d9
        d9.setCellStyle(style);

        Cell d10 = row.createCell(23);
        d10.setCellValue(num10); // d10
        d10.setCellStyle(style);

        Cell d11 = row.createCell(24);
        d11.setCellValue(num11); // d11
        d11.setCellStyle(style);

        Cell d12 = row.createCell(25);
        d12.setCellValue(num12); // d12
        d12.setCellStyle(style);

        Cell d13 = row.createCell(26);
        d13.setCellValue(num13); // d13
        d13.setCellStyle(style);

        Cell d14 = row.createCell(27);
        d14.setCellValue(num14); // d14
        d14.setCellStyle(style);

        Cell d15 = row.createCell(28);
        d15.setCellValue(num15); // d15
        d15.setCellStyle(style);

        Cell d16 = row.createCell(29);
        d16.setCellValue(num16); // d16
        d16.setCellStyle(style);

        Cell d17 = row.createCell(30);
        d17.setCellValue(num17); // d17
        d17.setCellStyle(style);

        Cell d18 = row.createCell(31);
        d18.setCellValue(num18); // d18
        d18.setCellStyle(style);

        Cell d19 = row.createCell(32);
        d19.setCellValue(num19); // d19
        d19.setCellStyle(style);

        Cell d20 = row.createCell(33);
        d20.setCellValue(num20); // d20
        d20.setCellStyle(style);

        Cell d21 = row.createCell(34);
        d21.setCellValue(num21); // d21
        d21.setCellStyle(style);

        Cell d22 = row.createCell(35);
        d22.setCellValue(num22); // d22
        d22.setCellStyle(style);

        Cell d23 = row.createCell(36);
        d23.setCellValue(num23); // d23
        d23.setCellStyle(style);

        Cell d24 = row.createCell(37);
        d24.setCellValue(num24); // d24
        d24.setCellStyle(style);

        Cell d25 = row.createCell(38);
        d25.setCellValue(num25); // d25
        d25.setCellStyle(style);

        Cell d26 = row.createCell(39);
        d26.setCellValue(num26); // d26
        d26.setCellStyle(style);

        Cell d27 = row.createCell(40);
        d27.setCellValue(num27); // d27
        d27.setCellStyle(style);

        Cell d28 = row.createCell(41);
        d28.setCellValue(num28); // d28
        d28.setCellStyle(style);

        Cell d29 = row.createCell(42);
        d29.setCellValue(num29); // d29
        d29.setCellStyle(style);

        Cell d30 = row.createCell(43);
        d30.setCellValue(num30); // d30
        d30.setCellStyle(style);

        Cell d31 = row.createCell(44);
        d31.setCellValue(num31); // d31
        d31.setCellStyle(style);

        Cell hj = row.createCell(45);
        hj.setCellValue(num); // d31
        hj.setCellStyle(style);

        FileOutputStream os=new FileOutputStream(file);
        wb.write(os);
    }



    @PostMapping(value = "/getPlaycertifyExcel")
    public MessageModel getPlayCertifyExcel(@RequestBody String data,HttpServletRequest request){
        MessageModel model=new MessageModel();
        Map<String,Object> map= JSON.parseObject(data,Map.class);
        Map<String,Object> dataMap=JSON.parseObject(map.get("data").toString(),Map.class);
        List<Map<String,Object>> playCList=JSON.parseObject(JSON.parseObject(dataMap.get("reportData").toString(),String.class).toString(),List.class);
        Map<String,Object> choosedCondition=JSON.parseObject(dataMap.get("choosedCondition").toString(),Map.class);
        AdvertPutIn advertPutIn=JSON.parseObject(choosedCondition.get("advertPutin").toString(), AdvertPutIn.class);
        String deviceIds[]=JSON.parseObject(choosedCondition.get("deviceIds").toString(),String[].class);

        map.clear();
        map.put("beginTime",dataMap.get("beginTime").toString());
        map.put("endTime",dataMap.get("endTime").toString());

        int fristDay=1;
        String endDay=null;

        String month=dataMap.get("month").toString();
        SimpleDateFormat sdfM=new SimpleDateFormat("yyyyMM");
        Calendar cal=Calendar.getInstance();

        if (Integer.parseInt(dataMap.get("month").toString())<10){
            month="0"+Integer.parseInt(dataMap.get("month").toString());
        }
        try {
            Date nowDate=sdfM.parse(dataMap.get("year").toString()+month);
            cal.setTime(nowDate);
            //获取选定月的最后一天
            cal.set(Calendar.DAY_OF_MONTH,cal.getLeastMaximum(Calendar.DAY_OF_MONTH));
            endDay=sdfDD.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //excel表头
        List<String> title=new ArrayList<String>();
        title.add("广告id");title.add("广告名称");title.add("投放类别");title.add("播放形式");title.add("统计日期");title.add("设备数量");
        title.add("播放策略");title.add("策略详情");title.add("屏幕模式");title.add("屏幕位置");title.add("设备编号");title.add("场所名称");
        title.add("时长");title.add("月份");
        while (fristDay<=Integer.parseInt(endDay)){
            if (fristDay==1){title.add("d1");}; if (fristDay==2){title.add("d2");};
            if (fristDay==3){title.add("d3");}; if (fristDay==4){title.add("d4");};
            if (fristDay==5){title.add("d5");}; if (fristDay==6){title.add("d6");};
            if (fristDay==7){title.add("d7");}; if (fristDay==8){title.add("d8");};
            if (fristDay==9){title.add("d9");}; if (fristDay==10){title.add("d10");};
            if (fristDay==11){title.add("d11");}; if (fristDay==12){title.add("d12");};
            if (fristDay==13){title.add("d13");}; if (fristDay==14){title.add("d14");};
            if (fristDay==15){title.add("d15");}; if (fristDay==16){title.add("d16");};
            if (fristDay==17){title.add("d17");}; if (fristDay==18){title.add("d18");};
            if (fristDay==19){title.add("d19");}; if (fristDay==20){title.add("d20");};
            if (fristDay==21){title.add("d21");}; if (fristDay==22){title.add("d22");};
            if (fristDay==23){title.add("d23");}; if (fristDay==24){title.add("d24");};
            if (fristDay==25){title.add("d25");}; if (fristDay==26){title.add("d26");};
            if (fristDay==27){title.add("d27");}; if (fristDay==28){title.add("d28");};
            if (fristDay==29){title.add("d29");}; if (fristDay==30){title.add("d30");};
            if (fristDay==31){title.add("d31");};
            fristDay++;
        }
        title.add("合计");
        map.put("endDay",endDay);
        map.put("year",dataMap.get("year").toString());
        map.put("month",month);
        try {
            writePlayCertifyExcel(title,advertPutIn,playCList,map,deviceIds);
        } catch (IOException e) {
           model.setResultCode("2");
        }
        model.setResultCode("1");
        return model;
    }


    public void writePlayCertifyExcel(List<String> title, AdvertPutIn advertPutIn,List<Map<String,Object>> list,Map<String,Object> map,String deviceIds[]) throws IOException {
        Resource resource=new ClassPathResource("/static/excels/playcertify.xls");
        File file=resource.getFile();
        HSSFWorkbook wb=new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet sheet=wb.getSheetAt(0);
        sheet.setDefaultColumnWidth(15);
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            if (sheet.getRow(i)!=null){
                sheet.removeRow(sheet.getRow(i));
            }
        }
        if(list.size() == 0){
            return;
        }
        //创建表头单元格样式 以及表头的字体样式
        HSSFCellStyle style = wb.createCellStyle();
        style.setWrapText(true);// 设置自动换行
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setFillBackgroundColor(HSSFColor.WHITE.index);//设置背景色
        style.setFillForegroundColor(HSSFColor.WHITE.index);//设置前景色
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        HSSFFont headerFont = (HSSFFont) wb.createFont(); // 创建字体样式
        headerFont.setFontName("宋体"); // 设置字体类型
        headerFont.setFontHeightInPoints((short) 12); // 设置字体大小
        headerFont.setColor(HSSFColor.BLACK.index);  //设置字体颜色
        style.setFont(headerFont); // 为标题样式设置字体样式

        //设置表头
        HSSFRow  titleRow=sheet.createRow(0);
        for (int i = 0; i < title.size(); i++) {
            HSSFCell titleCell=titleRow.createCell(i);
            titleCell.setCellValue(title.get(i).toString());
            titleCell.setCellStyle(style);
        }
        //播放证明单信息写入表中
        HSSFRow row=sheet.createRow(1);
        HSSFCell advertIdCell=row.createCell(0);
        advertIdCell.setCellValue(advertPutIn.getAdvertId());
        advertIdCell.setCellStyle(style);                          //广告编号
        HSSFCell advertNameCell=row.createCell(1);
        advertNameCell.setCellValue(advertPutIn.getAdvertName());
        advertNameCell.setCellStyle(style);                        //广告名称
        HSSFCell putinKindCell=row.createCell(2);
        if ("1".equals(advertPutIn.getPutInKind())){
            putinKindCell.setCellValue("购买");
        }else if ("2".equals(advertPutIn.getPutInKind())){
            putinKindCell.setCellValue("赠送");
        }else{
            putinKindCell.setCellValue("");
        }
        putinKindCell.setCellStyle(style);                           //播放类别
        HSSFCell playAloneCell=row.createCell(3);
        if (advertPutIn.getPlayAlone()==1){
            playAloneCell.setCellValue("轮播");
        }else if (advertPutIn.getPlayAlone()==2){
            playAloneCell.setCellValue("独播");
        }else{
            playAloneCell.setCellValue("");
        }

        playAloneCell.setCellStyle(style);                          //播放形式
        HSSFCell playTimeCell=row.createCell(4);
        playTimeCell.setCellValue(map.get("beginTime").toString()+"至"+map.get("endTime").toString());
        playTimeCell.setCellStyle(style);                           //统计时间
        HSSFCell deviceCountCell=row.createCell(5);
        deviceCountCell.setCellValue(String.valueOf(deviceIds.length));
        deviceCountCell.setCellStyle(style);                          //设备数量
        HSSFCell playPolicyCell=row.createCell(6);
        playPolicyCell.setCellValue(advertPutIn.getPolicyName());
        playPolicyCell.setCellStyle(style);                          //策略名称
        HSSFCell policyDetailCell=row.createCell(7);
        policyDetailCell.setCellValue(advertPutIn.getDetail());
        policyDetailCell.setCellStyle(style);                        //策略详情
        HSSFCell screenCell=row.createCell(8);
        screenCell.setCellValue(advertPutIn.getScreen());
        screenCell.setCellStyle(style);                              //屏幕模式
        HSSFCell locationCell=row.createCell(9);
        if ("up".equals(advertPutIn.getScreenPosition())){
            locationCell.setCellValue("上");
        }else if ("mid".equals(advertPutIn.getScreenPosition())){
            locationCell.setCellValue("中");
        }else if ("down".equals(advertPutIn.getScreenPosition())){
            locationCell.setCellValue("下");
        }else{
            locationCell.setCellValue("");
        }
        locationCell.setCellStyle(style);                            //屏幕位置

        int column=1;
        //column行统计总数
        int[] columnSum=new int[Integer.parseInt(map.get("endDay").toString())];
        int totalSum=0;

        for (Map<String,Object> report:list){
            int rowSum=0;
            //创建行
            HSSFRow hssfRow=null;
            if (column==1){
                hssfRow=sheet.getRow(1);
            }else{
                hssfRow=sheet.createRow(column);
            }
            //设备编号和设备名称
            HSSFCell deviceIdCell=hssfRow.createCell(10);
            deviceIdCell.setCellValue(report.get("device").toString().split("\\|")[0]);
            deviceIdCell.setCellStyle(style);
            HSSFCell deviceNameCell=hssfRow.createCell(11);
            deviceNameCell.setCellValue(report.get("device").toString().split("\\|")[1]);
            deviceNameCell.setCellStyle(style);
            HSSFCell durationCell=hssfRow.createCell(12);
            durationCell.setCellValue(advertPutIn.getDuration());
            durationCell.setCellStyle(style);
            HSSFCell monthCell=hssfRow.createCell(13);
            monthCell.setCellValue(map.get("year").toString()+"-"+map.get("month").toString());
            monthCell.setCellStyle(style);
            //获取具体某天数据
            for (int i = 14; i < title.size()-1; i++) {
                HSSFCell cell=hssfRow.createCell(i);
                cell.setCellValue(report.get(title.get(i).toString()).toString());
                String aa=title.get(i).toString();
                String bb=report.get(title.get(i).toString()).toString();


                cell.setCellStyle(style);
                rowSum+=Integer.parseInt(report.get(title.get(i).toString()).toString());
                columnSum[i-14]+= Integer.parseInt(report.get(title.get(i).toString()).toString());
            }
            //获取统计数量
            HSSFCell rowSumCell=hssfRow.createCell(title.size()-1);
            rowSumCell.setCellValue(rowSum);
            rowSumCell.setCellStyle(style);
            totalSum+=rowSum;
            column++;
        }
        //列总合计
        HSSFRow totalRow=sheet.createRow(list.size()+1);
        HSSFCell totalnamecell=totalRow.createCell(13);
        totalnamecell.setCellValue("总计");
        totalnamecell.setCellStyle(style);
        for (int i = 0; i < columnSum.length; i++) {
            HSSFCell columnSumcell=totalRow.createCell(14+i);
            columnSumcell.setCellValue(columnSum[i]);
            columnSumcell.setCellStyle(style);
        }
        HSSFCell columnSumcell=totalRow.createCell(14+columnSum.length);
        columnSumcell.setCellValue(totalSum);
        columnSumcell.setCellStyle(style);

        FileOutputStream os=new FileOutputStream(file);
        wb.write(os);
    }
}
