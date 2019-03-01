package com.en.adback.controller;

import com.alibaba.fastjson.JSON;
import com.en.adback.common.CutFileSize;
import com.en.adback.entity.Adorder.OrderQueryList;
import com.en.adback.entity.Logs;
import com.en.adback.entity.advertmgr.AdvertPutIn;
import com.en.adback.entity.devicemgr.AllDevice;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class MakeExcelCtrl {

    //设置表格样式 加粗加边框（黑边） 12号宋体
    public static HSSFCellStyle getTitleCellStyle(HSSFWorkbook wb){
        //创建表头单元格样式 以及表头的字体样式 加边框
        HSSFCellStyle titleTableStyle = wb.createCellStyle();
        titleTableStyle.setWrapText(true);// 设置自动换行
        titleTableStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titleTableStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式
        titleTableStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        titleTableStyle.setFillBackgroundColor(HSSFColor.WHITE.index);//设置背景色
        titleTableStyle.setFillForegroundColor(HSSFColor.WHITE.index);//设置前景色
        titleTableStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        titleTableStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        titleTableStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        titleTableStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        titleTableStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        HSSFFont titleTableFont = (HSSFFont) wb.createFont(); // 创建字体样式
        titleTableFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 设置字体加粗
        titleTableFont.setFontName("宋体"); // 设置字体类型
        titleTableFont.setFontHeightInPoints((short) 12); // 设置字体大小
        titleTableFont.setColor(HSSFColor.BLACK.index);  //设置字体颜色
        titleTableStyle.setFont(titleTableFont); // 为标题样式设置字体样式
        return titleTableStyle;
    }

    public static void setRegionStyle(HSSFSheet sheet,int row, int number, HSSFCellStyle cs) {
        HSSFRow hssfRow=sheet.getRow(row-1);
        for (int i = 1; i < number; i++) {
            Cell cell=hssfRow.createCell(i);
            cell.setCellStyle(cs);
        }
    }

    //将数据写入表格里   日志表
    public void writeLogExcel(List<Logs> logsList) throws IOException {
        Resource resource=new ClassPathResource("/static/excels/logs.xls");
        File file=resource.getFile();
        HSSFWorkbook wb=new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet sheet=wb.getSheetAt(0);
        sheet.setDefaultColumnWidth(15);
        int rowCount=1;
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            sheet.removeRow(sheet.getRow(i));
        }
        //创建内容单元格样式 以及字体样式
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
        HSSFFont font = (HSSFFont) wb.createFont(); // 创建字体样式
        font.setFontName("宋体"); // 设置字体类型
        font.setFontHeightInPoints((short) 12); // 设置字体大小
        font.setColor(HSSFColor.BLACK.index);  //设置字体颜色
        style.setFont(font); // 为标题样式设置字体样式

        //将信息写入表中
        for (Logs log:logsList){
            HSSFRow row=sheet.createRow(rowCount);

            Cell cellUserId = row.createCell(0); // 创建指定单元格对象。如本身有数据会替换掉
            cellUserId.setCellValue(log.getUserId()); // 人员编号
            cellUserId.setCellStyle(style);

            Cell cellGroupRoleName = row.createCell(1);
            cellGroupRoleName.setCellValue(log.getGroupRoleName()); // 账户所属分组id
            cellGroupRoleName.setCellStyle(style);

            Cell cellRoleName = row.createCell(2);
            cellRoleName.setCellValue(log.getRoleName()); // 角色
            cellRoleName.setCellStyle(style);

            Cell cellIp = row.createCell(3);
            cellIp.setCellValue(log.getIp()); // ip
            cellIp.setCellStyle(style);

            Cell cellLogContent = row.createCell(4);
            cellLogContent.setCellValue(log.getLogContent()); // 日志内容
            cellLogContent.setCellStyle(style);

            Cell cellActionTime = row.createCell(5);
            cellActionTime.setCellValue(log.getActionTime()); // 操作日期
            cellActionTime.setCellStyle(style);

            rowCount++;
        }
        FileOutputStream os=new FileOutputStream(file);
        wb.write(os);
    }

    //广告分发详情 表
    public void writeAdvertPutinExcel(Map<String, Object> map) throws IOException {
        AdvertPutIn advert= JSON.parseObject(map.get("advertPutin").toString(),AdvertPutIn.class);
        List<AdvertPutIn> linkAdvertList= Arrays.stream(JSON.parseObject(map.get("linkAdvertList").toString(), AdvertPutIn[].class)).collect(Collectors.toList());
        List<AllDevice> deviceList= Arrays.stream(JSON.parseObject(map.get("deviceList").toString(), AllDevice[].class)).collect(Collectors.toList());
        String playDaysArray[]=JSON.parseObject(map.get("playDaysArray").toString(),String[].class);
        String inputType = map.get("inputType").toString();

        Map<String,Object> mapDays = JSON.parseObject(playDaysArray[0].toString(),Map.class);

        Resource resource=new ClassPathResource("/static/excels/advertPutinDetail.xls");
        FileOutputStream os=null;
        File file=resource.getFile();
        HSSFWorkbook wb=new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet sheet=wb.getSheetAt(0);
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            if(null != sheet.getRow(i)){
                sheet.removeRow(sheet.getRow(i));
            }
        }
        for (int i = 0; i < 8; i++) {
            sheet.setColumnWidth(i, 13 * 256); // 单独设置每列的宽
        }
        //创建表头单元格样式 字体加粗，灰色框（和新建表格样式一致）
        HSSFCellStyle titleStyle = wb.createCellStyle();
        titleStyle.setWrapText(true);// 设置自动换行
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式
        titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        titleStyle.setFillBackgroundColor(HSSFColor.WHITE.index);//设置背景色
        titleStyle.setFillForegroundColor(HSSFColor.WHITE.index);//设置前景色
        titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
        titleStyle.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
        titleStyle.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
        titleStyle.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
        HSSFFont headerFont = (HSSFFont) wb.createFont(); // 创建字体样式
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 设置字体加粗
        headerFont.setFontName("宋体"); // 设置字体类型
        headerFont.setFontHeightInPoints((short) 12); // 设置字体大小
        headerFont.setColor(HSSFColor.BLACK.index);  //设置字体颜色
        titleStyle.setFont(headerFont); // 为标题样式设置字体样式

        //创建内容单元格样式 正常字体 灰色框
        HSSFCellStyle style = wb.createCellStyle();
        style.setWrapText(true);// 设置自动换行
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setFillBackgroundColor(HSSFColor.WHITE.index);//设置背景色
        style.setFillForegroundColor(HSSFColor.WHITE.index);//设置前景色
        style.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);
        style.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);
        style.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);
        style.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        HSSFFont font = (HSSFFont) wb.createFont(); // 创建字体样式
        font.setFontName("宋体"); // 设置字体类型
        font.setFontHeightInPoints((short) 12); // 设置字体大小
        font.setColor(HSSFColor.BLACK.index);  //设置字体颜色
        style.setFont(font); // 为标题样式设置字体样式

        //合并单元格 广告名称和编号
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,7));
        //创建第一行（标题）
        HSSFRow titlerow=sheet.createRow(0);
        titlerow.setHeightInPoints(25);
        Cell titlecell=titlerow.createCell(0);
        titlecell.setCellValue(advert.getAdvertId()+' '+advert.getAdvertName());
        titlecell.setCellStyle(titleStyle);
        setRegionStyle(sheet,1,8,titleStyle);
        //创建第二行
        HSSFRow typeRow=sheet.createRow(1);
        Cell typeTitle=typeRow.createCell(0);
        typeTitle.setCellValue("投放类别");
        typeTitle.setCellStyle(titleStyle);
        Cell typeValue=typeRow.createCell(1);
        if(Integer.parseInt(advert.getPutInKind())==1){
            typeValue.setCellValue("购买");
        }else if(Integer.parseInt(advert.getPutInKind())==2){
            typeValue.setCellValue("赠送");
        }
        typeValue.setCellStyle(style);
        //创建第三行
        HSSFRow playDayRow=sheet.createRow(2);
        Cell playDayCell=playDayRow.createCell(0);
        playDayCell.setCellValue("投放周期");
        playDayCell.setCellStyle(titleStyle);
        Cell total=playDayRow.createCell(1);
        total.setCellValue("全部天数（"+mapDays.get("totalDates").toString()+")");
        total.setCellStyle(style);
        //获取播放日期数据
        if(1<=playDaysArray.length){//当日期都在同一个月  前面已经转化为map了
            HSSFRow dayrow=sheet.getRow(2);
            if (dayrow==null){
                dayrow=sheet.createRow(2);
            }
            Cell monthcell=dayrow.createCell(2);
            String year = mapDays.get("year").toString();
            String month = mapDays.get("month").toString();
            monthcell.setCellValue(mapDays.get("year").toString()+"年"+mapDays.get("month").toString()+"月"+"("+mapDays.get("totalDates").toString()+")");
            monthcell.setCellStyle(style);
            Cell daycell=dayrow.createCell(3);
            daycell.setCellValue(mapDays.get("days").toString());
            daycell.setCellStyle(style);
        }else{//当日期不在同一个月
            for (int i = 0; i < playDaysArray.length; i++) {
                Map<String,Object> day = JSON.parseObject(playDaysArray[i].toString(),Map.class);
                HSSFRow dayrow=sheet.getRow(i+2);
                if (dayrow==null){
                    dayrow=sheet.createRow(i+2);
                }
                Cell monthcell=dayrow.getCell(2);
                monthcell.setCellValue(day.get("year").toString()+"年"+day.get("month").toString()+"月"+"("+day.get("days").toString().split(",").length+")");
                monthcell.setCellStyle(style);
                Cell daycell=dayrow.getCell(3);
                daycell.setCellValue(day.get("days").toString());
                daycell.setCellStyle(style);
            }
        }
        //获取广告策略数据
        HSSFRow advertrow=sheet.createRow(2+playDaysArray.length);
        Cell playtypecell=advertrow.createCell(0);
        playtypecell.setCellValue("播放形式");
        playtypecell.setCellStyle(titleStyle);
        Cell playtypeValue=advertrow.createCell(1);
        if (advert.getPlayAlone()==1) {
            playtypeValue.setCellValue("轮播");
        }else if(advert.getPlayAlone()==2){
            playtypeValue.setCellValue("独播");
        }else{
            playtypeValue.setCellValue("");
        }
        playtypeValue.setCellStyle(style);
        Cell policycell=advertrow.createCell(2);
        policycell.setCellValue("播放策略");
        policycell.setCellStyle(titleStyle);
        Cell policyvalue=advertrow.createCell(3);
        policyvalue.setCellValue(advert.getPolicyName());
        policyvalue.setCellStyle(style);
        Cell screencell=advertrow.createCell(4);
        screencell.setCellValue("屏幕模式");  //播放屏幕
        screencell.setCellStyle(titleStyle);
        Cell screenvalue=advertrow.createCell(5);
        screenvalue.setCellValue(advert.getScreen());
        screenvalue.setCellStyle(style);
        Cell positioncell=advertrow.createCell(6);
        positioncell.setCellValue("位置");  //播放屏幕
        positioncell.setCellStyle(titleStyle);
        Cell positionvalue=advertrow.createCell(7);
        if ("up".equals(advert.getScreenPosition())){
            positionvalue.setCellValue("上屏");
        }else if ("mid".equals(advert.getScreenPosition())){
            positionvalue.setCellValue("中屏");
        }else if("down".equals(advert.getScreenPosition())){
            positionvalue.setCellValue("下屏");
        }else{
            positionvalue.setCellValue("");
        }
        positionvalue.setCellStyle(style);
        //生成关联广告表头
        HSSFRow linkadvert=sheet.createRow(3+playDaysArray.length);
        Cell titlelink=linkadvert.createCell(0);
        titlelink.setCellValue("关联广告");
        titlelink.setCellStyle(titleStyle);

        //创建表头单元格样式 以及表头的字体样式 加边框
        HSSFCellStyle titleTableStyle = wb.createCellStyle();
        titleTableStyle.setWrapText(true);// 设置自动换行
        titleTableStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titleTableStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式
        titleTableStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        titleTableStyle.setFillBackgroundColor(HSSFColor.WHITE.index);//设置背景色
        titleTableStyle.setFillForegroundColor(HSSFColor.WHITE.index);//设置前景色
        titleTableStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        titleTableStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        titleTableStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        titleTableStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        titleTableStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        HSSFFont titleTableFont = (HSSFFont) wb.createFont(); // 创建字体样式
        titleTableFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 设置字体加粗
        titleTableFont.setFontName("宋体"); // 设置字体类型
        titleTableFont.setFontHeightInPoints((short) 12); // 设置字体大小
        titleTableFont.setColor(HSSFColor.BLACK.index);  //设置字体颜色
        titleTableStyle.setFont(titleTableFont); // 为标题样式设置字体样式
        HSSFRow linkadverttitlerow=sheet.createRow(4+playDaysArray.length);
        String linkAdvertTitle[]={"广告编号","广告策略","广告屏幕模式","广告位置"};
        for (int i = 0; i < linkAdvertTitle.length; i++) {
            Cell title=linkadverttitlerow.createCell(i);
            title.setCellValue(linkAdvertTitle[i].toString());
            title.setCellStyle(titleTableStyle);
        }
        //创建内容单元格样式 黑色框，12号字体，不加粗
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setWrapText(true);// 设置自动换行
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 创建一个居中格式
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        cellStyle.setFillBackgroundColor(HSSFColor.WHITE.index);//设置背景色
        cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);//设置前景色
        cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        HSSFFont cellFont = (HSSFFont) wb.createFont(); // 创建字体样式
        cellFont.setFontName("宋体"); // 设置字体类型
        cellFont.setFontHeightInPoints((short) 12); // 设置字体大小
        cellFont.setColor(HSSFColor.BLACK.index);  //设置字体颜色
        style.setFont(cellFont); // 为标题样式设置字体样式
        //获取关联广告
        for (int i = 0; i < linkAdvertList.size(); i++) {
            HSSFRow linkadvertrow=sheet.createRow(5+playDaysArray.length+i);
            Cell codecell=linkadvertrow.createCell(0);            //  广告编号
            codecell.setCellValue(linkAdvertList.get(i).getAdvertId());
            codecell.setCellStyle(cellStyle);
            Cell policyNameCell=linkadvertrow.createCell(1);            //  广告策略
            policyNameCell.setCellValue(linkAdvertList.get(i).getPolicyName());
            policyNameCell.setCellStyle(cellStyle);
            Cell screenCell=linkadvertrow.createCell(2);            //  屏幕模式
            screenCell.setCellValue(linkAdvertList.get(i).getScreen());
            screenCell.setCellStyle(cellStyle);
            Cell screenPositionCell=linkadvertrow.createCell(3);            //  屏幕模式
            screenPositionCell.setCellValue("up".equals(linkAdvertList.get(i).getScreenPosition())?"上屏":("mid".equals(linkAdvertList.get(i).getScreenPosition())?"中屏":("down".equals(linkAdvertList.get(i).getScreenPosition())?"下屏":""))); // 位置
            screenPositionCell.setCellStyle(cellStyle);
        }

        //投放设备X台
        HSSFRow deviceRowTitle=sheet.createRow(5+playDaysArray.length+linkAdvertList.size());
        Cell devicetitle=deviceRowTitle.createCell(0);
        devicetitle.setCellValue("投放设备（"+deviceList.size()+"）");
        devicetitle.setCellStyle(titleStyle);

        HSSFRow deviceRow=sheet.createRow(6+playDaysArray.length+linkAdvertList.size());
        String deviceTitle[]={"序号","设备编号","场所编号","场所名称","场所地址"};
        for (int i = 0; i < deviceTitle.length; i++) {
            Cell title=deviceRow.createCell(i);
            title.setCellValue(deviceTitle[i].toString());
            title.setCellStyle(titleTableStyle);
        }
        if ("advertCount".equals(inputType)){
            Cell stateCell=deviceRow.createCell(deviceTitle.length);
            stateCell.setCellStyle(titleTableStyle);
            stateCell.setCellValue("广告状态");
            Cell countCell=deviceRow.createCell(deviceTitle.length+1);
            countCell.setCellStyle(titleTableStyle);
            countCell.setCellValue("播放频次");
        }
        //获取投放设备信息
        for(int i=0;i<deviceList.size();i++){
            HSSFRow devicerow=sheet.createRow(7+playDaysArray.length+linkAdvertList.size()+i);
            Cell nocell = devicerow.createCell(0);
            nocell.setCellValue(i+1);//序号
            nocell.setCellStyle(style);
            Cell deviceIdcell = devicerow.createCell(1);
            deviceIdcell.setCellStyle(style);
            deviceIdcell.setCellValue(deviceList.get(i).getDeviceId());//设备编号
            Cell enterpriseIdcell = devicerow.createCell(2);
            enterpriseIdcell.setCellValue(deviceList.get(i).getEnterpriseId());//场所编号
            enterpriseIdcell.setCellStyle(style);
            Cell enterpriseNamecell = devicerow.createCell(3);
            enterpriseNamecell.setCellValue(deviceList.get(i).getEnterpriseName());//场所名称
            enterpriseNamecell.setCellStyle(style);
            Cell addresscell = devicerow.createCell(4);
            addresscell.setCellValue(deviceList.get(i).getAddress());//场所地址
            addresscell.setCellStyle(style);
            if("advertCount".equals(inputType)){
                Cell stateIdcell = devicerow.createCell(5);
                stateIdcell.setCellValue(deviceList.get(i).getStateId());//广告状态
                stateIdcell.setCellStyle(style);
                Cell playConts = devicerow.createCell(6);
                playConts.setCellValue(deviceList.get(i).getPlayCounts());//播放频次
                playConts.setCellStyle(style);
            }
        }

        //合并单元格
        if (playDaysArray.length>1){
            sheet.addMergedRegion(new CellRangeAddress(2, playDaysArray.length+1, 1, 1));
        }

        os=new FileOutputStream(file);
        wb.write(os);
        os.flush();
        os.close();

    }

    //广告播放点位生成表
    public void writePutinQueryExcel(List<Map<String, Object>> dataList){
        List<String> title=new ArrayList<String>();
        //获取表头数据
        title.add("设备编号");
        title.add("场所名称");
        title.add("播放形式");
        title.add("播放策略");
        title.add("策略详情");
        title.add("屏幕模式");
        title.add("位置");
        if(dataList.get(0).get("d1")!=null){ title.add("d1"); }; if(dataList.get(0).get("d2")!=null){ title.add("d2"); }
        if(dataList.get(0).get("d3")!=null){ title.add("d3"); }; if(dataList.get(0).get("d4")!=null){ title.add("d4"); }
        if(dataList.get(0).get("d5")!=null){ title.add("d5"); }; if(dataList.get(0).get("d6")!=null){ title.add("d6"); }
        if(dataList.get(0).get("d7")!=null){ title.add("d7"); }; if(dataList.get(0).get("d8")!=null){ title.add("d8"); }
        if(dataList.get(0).get("d9")!=null){ title.add("d9"); }; if(dataList.get(0).get("d10")!=null){ title.add("d10"); }
        if(dataList.get(0).get("d11")!=null){ title.add("d11"); }; if(dataList.get(0).get("d12")!=null){ title.add("d12"); }
        if(dataList.get(0).get("d13")!=null){ title.add("d13"); }; if(dataList.get(0).get("d14")!=null){ title.add("d14"); }
        if(dataList.get(0).get("d15")!=null){ title.add("d15"); }; if(dataList.get(0).get("d16")!=null){ title.add("d16"); }
        if(dataList.get(0).get("d17")!=null){ title.add("d17"); }; if(dataList.get(0).get("d18")!=null){ title.add("d18"); }
        if(dataList.get(0).get("d19")!=null){ title.add("d19"); }; if(dataList.get(0).get("d20")!=null){ title.add("d20"); }
        if(dataList.get(0).get("d21")!=null){ title.add("d21"); }; if(dataList.get(0).get("d22")!=null){ title.add("d22"); }
        if(dataList.get(0).get("d23")!=null){ title.add("d23"); }; if(dataList.get(0).get("d24")!=null){ title.add("d24"); }
        if(dataList.get(0).get("d25")!=null){ title.add("d25"); }; if(dataList.get(0).get("d26")!=null){ title.add("d26"); }
        if(dataList.get(0).get("d27")!=null){ title.add("d27"); }; if(dataList.get(0).get("d28")!=null){ title.add("d28"); }
        if(dataList.get(0).get("d29")!=null){ title.add("d29"); }; if(dataList.get(0).get("d30")!=null){ title.add("d30"); }
        if(dataList.get(0).get("d31")!=null){ title.add("d31"); };
        //获取写入的文件
        Resource resource=new ClassPathResource("static/excels/putinPoint.xls");
        File file= null;
        FileOutputStream os=null;
        try {
            file = resource.getFile();

            HSSFWorkbook wb=new HSSFWorkbook(new FileInputStream(file));

            //将表数据清空，确保里面没有值
            HSSFSheet sheet=wb.getSheetAt(0);
            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                if (sheet.getRow(i)!=null){
                    sheet.removeRow(sheet.getRow(i));
                }
            }
            //生成表数据

            //第一行 表头
            HSSFRow titleRow=sheet.createRow(0);
            for (int i = 0; i < title.size(); i++) {
                Cell titleCell=titleRow.createCell(i);
                titleCell.setCellValue(title.get(i).toString());
                titleCell.setCellStyle(getTitleCellStyle(wb));
            }
            //创建内容单元格样式 以及字体样式
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
            HSSFFont font = (HSSFFont) wb.createFont(); // 创建字体样式
            font.setFontName("宋体"); // 设置字体类型
            font.setFontHeightInPoints((short) 12); // 设置字体大小
            font.setColor(HSSFColor.BLACK.index);  //设置字体颜色
            style.setFont(font); // 为标题样式设置字体样式
            // 第2行播放点位设备信息
            for (int i = 0; i <dataList.size() ; i++) {
                HSSFRow row=sheet.createRow(i+1);
                Cell deviceIdCell=row.createCell(0);
                deviceIdCell.setCellValue(dataList.get(i).get("deviceId").toString()); // 设备编号
                deviceIdCell.setCellStyle(style);

                Cell placeNameCell=row.createCell(1);
                placeNameCell.setCellValue(dataList.get(i).get("enterpriseName").toString()); // 场所名称
                placeNameCell.setCellStyle(style);

                Cell playTypeCell=row.createCell(2);
                if (Integer.parseInt(dataList.get(i).get("playAlone").toString())==1){
                    playTypeCell.setCellValue("轮播");
                }else if(Integer.parseInt(dataList.get(i).get("playAlone").toString())==2){
                    playTypeCell.setCellValue("独播");
                }else{
                    playTypeCell.setCellValue("");
                }
                playTypeCell.setCellStyle(style);// 播放形式

                Cell playPolicyCell=row.createCell(3);
                playPolicyCell.setCellValue(dataList.get(i).get("screenPolicyName").toString()); // 播放策略
                playPolicyCell.setCellStyle(style);

                Cell policyDetailCell=row.createCell(4);
                policyDetailCell.setCellValue(dataList.get(i).get("detail").toString()); // 策略详情
                policyDetailCell.setCellStyle(style);

                Cell screenCell=row.createCell(5);
                screenCell.setCellValue(dataList.get(i).get("screenCutName").toString()); // 屏幕模式
                screenCell.setCellStyle(style);

                Cell positionCell=row.createCell(6);
                if ("up".equals(dataList.get(i).get("position").toString())) {
                    positionCell.setCellValue("上屏");
                }else if ("mid".equals(dataList.get(i).get("position").toString())) {
                    positionCell.setCellValue("中屏");
                }else if("down".equals(dataList.get(i).get("position").toString())){
                    positionCell.setCellValue("下屏");
                }else{
                    positionCell.setCellValue("");
                }
                positionCell.setCellStyle(style);// 屏幕位置

                for (int j = 7; j < title.size(); j++) {
                    Cell cell=row.createCell(j);
                    cell.setCellValue(Integer.parseInt(dataList.get(i).get(title.get(j)).toString()));
                    cell.setCellStyle(style);
                }
                os=new FileOutputStream(file);
                wb.write(os);
                os.flush();
                os.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //生成广告统计数据
    public void writeAdvertCountQueryExcel(List<AdvertPutIn> list,String[] title) {
        CutFileSize cfs=new CutFileSize();
        Resource resource=new ClassPathResource("static/excels/advert.xls");
        try {
            FileOutputStream os=null;
            File file=resource.getFile();
            HSSFWorkbook wb=new HSSFWorkbook(new FileInputStream(file));
            HSSFSheet sheet=wb.getSheetAt(0);
            System.out.println(sheet.getLastRowNum());
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                sheet.removeRow(sheet.getRow(i));
            }

            //生成表头数据
            HSSFRow titleRow=sheet.createRow(0);
            for (int i = 0; i < title.length; i++) {
                Cell titleCell=titleRow.createCell(i);
                titleCell.setCellValue(title[i].toString());
                titleCell.setCellStyle(getTitleCellStyle(wb));
            }
            //创建内容单元格样式 以及字体样式
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
            HSSFFont font = (HSSFFont) wb.createFont(); // 创建字体样式
            font.setFontName("宋体"); // 设置字体类型
            font.setFontHeightInPoints((short) 12); // 设置字体大小
            font.setColor(HSSFColor.BLACK.index);  //设置字体颜色
            style.setFont(font); // 为标题样式设置字体样式

            int count=1;
            for (AdvertPutIn advert:list) {
                HSSFRow row=sheet.createRow(count);

                Cell advertIdCell=row.createCell(0);
                advertIdCell.setCellValue(advert.getAdvertId()); // 广告编号
                advertIdCell.setCellStyle(style);

                Cell advertNameCell=row.createCell(1);
                advertNameCell.setCellValue(advert.getAdvertName()); // 广告名称
                advertNameCell.setCellStyle(style);

                Cell corpNameCell=row.createCell(2);
                corpNameCell.setCellValue(advert.getAdCorpName()); // 广告公司名称
                corpNameCell.setCellStyle(style);

                Cell blankCell=row.createCell(3);
                blankCell.setCellValue(advert.getBlankName()); // 品牌名称
                blankCell.setCellStyle(style);

                Cell tradeCell=row.createCell(4);
                tradeCell.setCellValue(advert.getTradeName()); // 行业名称
                tradeCell.setCellStyle(style);

                Cell modeCell=row.createCell(5);
                modeCell.setCellValue(advert.getFileName().split("\\.")[advert.getFileName().split("\\.").length-1].toString()); // 文件格式
                modeCell.setCellStyle(style);

                Cell durationCell=row.createCell(6);
                durationCell.setCellValue(advert.getDuration()); // 广告时长
                durationCell.setCellStyle(style);

                Cell fileSizeCell=row.createCell(7);
                fileSizeCell.setCellValue(cfs.cutFileSize(advert.getFileSize())); // 广告文件大小
                fileSizeCell.setCellStyle(style);

                Cell checkDayCell=row.createCell(8);
                checkDayCell.setCellValue(advert.getCheckDay()); // 广告审核时间
                checkDayCell.setCellStyle(style);

                Cell makerCell=row.createCell(9);
                makerCell.setCellValue(advert.getMaker()); // 广告审核人
                makerCell.setCellStyle(style);

                Cell stateCell=row.createCell(10);
                switch (advert.getNowState()){
                    case 1:
                        stateCell.setCellValue("待审核"); // 广告状态
                        break;
                    case 2:
                        stateCell.setCellValue("审核中");
                        break;
                    case 3:
                        stateCell.setCellValue("审核通过");
                        break;
                    case 4:
                        stateCell.setCellValue("审核不通过");
                        break;
                    case 5:
                        stateCell.setCellValue("设置策略");
                        break;
                    case 6:
                        stateCell.setCellValue("待下发");
                        break;
                    case 7:
                        stateCell.setCellValue("已下发");
                        break;
                    case 8:
                        stateCell.setCellValue("替换");
                        break;
                    case 9:
                        stateCell.setCellValue("被替换");
                        break;
                    case 10:
                        stateCell.setCellValue("自动下刊");
                        break;
                    case 11:
                        stateCell.setCellValue("手动下刊");
                        break;
                    default:
                        stateCell.setCellValue("");
                }
                stateCell.setCellStyle(style);
                count++;
            }
            os=new FileOutputStream(file);
            wb.write(os);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //生成广告受众信息表
    public void writeAudienceExcel(List<LinkedHashMap<String, Object>> list,String title[]){
        Resource resource=new ClassPathResource("static/excels/advertAudience.xls");
        try {
            HSSFWorkbook wb=new HSSFWorkbook(new FileInputStream(resource.getFile()));
            HSSFSheet sheet=wb.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                sheet.removeRow(sheet.getRow(i));
            }
            //第一行 表头
            HSSFRow titleRow=sheet.createRow(0);
            //表头写入
            for (int i = 0; i < title.length; i++) {
                Cell titleCell=titleRow.createCell(i);
                titleCell.setCellStyle(getTitleCellStyle(wb));
                titleCell.setCellValue(title[i].toString());
            }
            //创建内容单元格样式 以及字体样式
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
            HSSFFont font = (HSSFFont) wb.createFont(); // 创建字体样式
            font.setFontName("宋体"); // 设置字体类型
            font.setFontHeightInPoints((short) 12); // 设置字体大小
            font.setColor(HSSFColor.BLACK.index);  //设置字体颜色
            style.setFont(font); // 为标题样式设置字体样式

            int count=1;
            //第二行  数据
            for (int i = 0; i < list.size(); i++) {
                LinkedHashMap<String,Object> map=list.get(i);
                HSSFRow row=sheet.createRow(count);

                Cell deviceIdCell=row.createCell(0);
                deviceIdCell.setCellValue(map.get("deviceId").toString()); // 设备编号
                deviceIdCell.setCellStyle(style);

                Cell sexCell=row.createCell(1);
                sexCell.setCellStyle(style);
                sexCell.setCellValue(map.get("sex").toString());// 受众性别

                Cell ageCell=row.createCell(2);
                ageCell.setCellValue(Integer.parseInt(map.get("age").toString()));// 受众年龄
                ageCell.setCellStyle(style);

                Cell nationCell=row.createCell(3);
                nationCell.setCellStyle(style);
                nationCell.setCellValue(map.get("nationName").toString()); // 籍贯名称

                Cell timeCell=row.createCell(4);
                timeCell.setCellValue(map.get("putinTime").toString()); // 投放时间
                timeCell.setCellStyle(style);
                count++;
            }
            FileOutputStream os =new FileOutputStream(resource.getFile());
            wb.write(os);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //将订单信息写入到excel表里面
    public void writeOrderExcel(List<OrderQueryList> list) {
        Resource resource=new ClassPathResource("static/excels/order.xls");
        try {
            HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(resource.getFile()));
            //创建内容单元格样式 以及字体样式
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
            HSSFFont font = (HSSFFont) wb.createFont(); // 创建字体样式
            font.setFontName("宋体"); // 设置字体类型
            font.setFontHeightInPoints((short) 12); // 设置字体大小
            font.setColor(HSSFColor.BLACK.index);  //设置字体颜色
            style.setFont(font); // 为标题样式设置字体样式

            HSSFSheet sheet=wb.getSheetAt(0);
            int a=sheet.getLastRowNum();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                try {
                    sheet.removeRow(sheet.getRow(i));
                }catch (NullPointerException e){
                    System.out.println("不存在本行");
                }
            }

            for (int i = 0; i < list.size(); i++) {
                HSSFRow row=sheet.createRow(i+1);
                HSSFCell orderIdCell=row.createCell(0);
                orderIdCell.setCellValue(list.get(i).getOrderId());       //订单id
                orderIdCell.setCellStyle(style);
                HSSFCell orderMemoCell=row.createCell(1);
                orderMemoCell.setCellValue(list.get(i).getOrderMemo());   //订单备注
                orderMemoCell.setCellStyle(style);
                HSSFCell tradeCell=row.createCell(2);
                tradeCell.setCellValue(list.get(i).getTradeName());       //行业名称
                tradeCell.setCellStyle(style);
                HSSFCell corpCell=row.createCell(3);
                corpCell.setCellValue(list.get(i).getAdCorpName());       //预定公司名称
                corpCell.setCellStyle(style);
                HSSFCell blankCell=row.createCell(4);
                blankCell.setCellValue(list.get(i).getBlankName());       //预定品牌名称
                blankCell.setCellStyle(style);
                //开始播放时日
                String beginTime=list.get(i).getPlayDates().split("\\,")[0].toString();
                //结束播放时日
                String endTime=list.get(i).getPlayDates().split("\\,")[list.get(i).getPlayDates().split("\\,").length-1].toString();
                HSSFCell beginTimeCell=row.createCell(5);
                beginTimeCell.setCellValue(beginTime);
                beginTimeCell.setCellStyle(style);
                HSSFCell endTimeCell=row.createCell(6);
                endTimeCell.setCellValue(endTime);
                endTimeCell.setCellStyle(style);
                HSSFCell advertCountsCell=row.createCell(7);
                advertCountsCell.setCellValue(list.get(i).getAdvertCounts());   //广告数量
                advertCountsCell.setCellStyle(style);
                HSSFCell devicesCountsCell=row.createCell(8);
                devicesCountsCell.setCellValue(list.get(i).getDevices().split("\\,").length);  //设备数量
                devicesCountsCell.setCellStyle(style);

                HSSFCell makerCell=row.createCell(9);
                makerCell.setCellValue(list.get(i).getMaker());  //业务员
                makerCell.setCellStyle(style);
                HSSFCell makeTimeCell=row.createCell(10);
                makeTimeCell.setCellValue(list.get(i).getMakeTime());  //录入日期
                makeTimeCell.setCellStyle(style);
                HSSFCell effectCell=row.createCell(11);
                effectCell.setCellValue(list.get(i).getEffectTime());  //失效日期
                effectCell.setCellStyle(style);
                HSSFCell payMoneyCell=row.createCell(13);
                payMoneyCell.setCellValue(list.get(i).getPayMoney());  //付款金额
                payMoneyCell.setCellStyle(style);
                HSSFCell orderStateCell=row.createCell(12);
                if ("1".equals(list.get(i).getOrderStateId())){
                    orderStateCell.setCellValue("待确定");  //订单状态
                }else if ("2".equals(list.get(i).getOrderStateId())){
                    orderStateCell.setCellValue("待分发");  //订单状态
                }else if ("3".equals(list.get(i).getOrderStateId())){
                    orderStateCell.setCellValue("已完成");  //订单状态
                }else{
                    orderStateCell.setCellValue("已失效");  //订单状态
                }
                orderStateCell.setCellStyle(style);
            }
            FileOutputStream os =new FileOutputStream(resource.getFile());
            wb.write(os);
            os.flush();
            os.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void writeDeviceExcel(List<Map<String, Object>> list) {
        Resource resource=new ClassPathResource("static/excels/devices.xls");
        try {
            HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(resource.getFile()));
            //创建内容单元格样式 以及字体样式
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
            HSSFFont font = (HSSFFont) wb.createFont(); // 创建字体样式
            font.setFontName("宋体"); // 设置字体类型
            font.setFontHeightInPoints((short) 12); // 设置字体大小
            font.setColor(HSSFColor.BLACK.index);  //设置字体颜色
            style.setFont(font); // 为标题样式设置字体样式

            HSSFSheet sheet=wb.getSheetAt(0);
            int a=sheet.getLastRowNum();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                try {
                    sheet.removeRow(sheet.getRow(i));
                }catch (NullPointerException e){
                    System.out.println("不存在本行");
                }
            }

            for (int i = 0; i < list.size(); i++) {
                HSSFRow row=sheet.createRow(i+1);
                HSSFCell deviceIdCell=row.createCell(0);
                deviceIdCell.setCellValue(list.get(i).get("deviceId").toString()); // 设备id
                deviceIdCell.setCellStyle(style);
                HSSFCell enterpriseIdCell = row.createCell(1);
                enterpriseIdCell.setCellValue(list.get(i).get("enterpriseId").toString()); // 场所id
                enterpriseIdCell.setCellStyle(style);
                HSSFCell enterpriseNameCell=row.createCell(2);
                enterpriseNameCell.setCellValue(list.get(i).get("enterpriseName").toString()); //场所名称
                enterpriseNameCell.setCellStyle(style);
                HSSFCell addCell=row.createCell(3);
                addCell.setCellValue(list.get(i).get("address").toString()); // 场所地址
                addCell.setCellStyle(style);
            }

            FileOutputStream os =new FileOutputStream(resource.getFile());
            wb.write(os);
            os.flush();
            os.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
