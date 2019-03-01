package com.en.adback.controller;

import com.en.adback.entity.Logs;
import com.en.adback.entity.PlayLog;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PlayLlogExcelCtrl {

    //将数据写入表格里   播出日志表
    public void writeLogExcel(List<PlayLog> logsList, Map<String,Object> map) throws IOException {
        Resource resource=new ClassPathResource("/static/excels/playlog.xls");
        File file=resource.getFile();
        HSSFWorkbook wb=new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet sheet=wb.getSheetAt(0);
        sheet.setDefaultColumnWidth(15);
        int rowCount=1;
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            sheet.removeRow(sheet.getRow(i));
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

        //将信息写入表中
        for (PlayLog log:logsList){
            HSSFRow row=sheet.createRow(rowCount);


            Cell cellAdvertId = row.createCell(0); // 创建指定单元格对象。如本身有数据会替换掉
            cellAdvertId.setCellValue(map.get("advertId").toString()+"," + map.get("advertName").toString()); // 广告
            cellAdvertId.setCellStyle(style);

            Cell cellPutInKind = row.createCell(1); // 创建指定单元格对象。如本身有数据会替换掉
            cellPutInKind.setCellValue("1".equals(map.get("putInKind").toString()) ? "购买": "赠送"); // 投放类别
            cellPutInKind.setCellStyle(style);

            Cell cellDate = row.createCell(2); // 创建指定单元格对象。如本身有数据会替换掉
            cellDate.setCellValue(map.get("beginDate").toString()+"至"+map.get("endDate").toString()); // 统计日期
            cellDate.setCellStyle(style);

            Cell cellDeviceIds = row.createCell(3); // 创建指定单元格对象。如本身有数据会替换掉
            cellDeviceIds.setCellValue(map.get("deviceIds").toString()); // 设备
            cellDeviceIds.setCellStyle(style);

            Cell cellPlayAlone = row.createCell(4); // 创建指定单元格对象。如本身有数据会替换掉
            cellPlayAlone.setCellValue(map.get("playAlone").toString()); // 播放形式
            cellPlayAlone.setCellStyle(style);

            Cell cellTime = row.createCell(5); // 创建指定单元格对象。如本身有数据会替换掉
            if(null == map.get("beginPlayTime") || "".equals(map.get("beginPlayTime")) || null == map.get("endPlayTime")
                    || "".equals(map.get("endPlayTime"))){
                cellTime.setCellValue(""); // 统计时间
            }else{
                cellTime.setCellValue(map.get("beginPlayTime").toString()+"至"+map.get("endPlayTime").toString()); // 统计时间
            }
            cellTime.setCellStyle(style);


            Cell cellBroadcastDate = row.createCell(6); // 创建指定单元格对象。如本身有数据会替换掉
            cellBroadcastDate.setCellValue(log.getBroadcastDate()); // 日期
            cellBroadcastDate.setCellStyle(style);

            Cell cellAirTime = row.createCell(7);
            cellAirTime.setCellValue(log.getAirTime()); // 时间
            cellAirTime.setCellStyle(style);

            Cell cellDuration = row.createCell(8);
            cellDuration.setCellValue(log.getDuration()); // 时长
            cellDuration.setCellStyle(style);

            Cell cellPolicyName = row.createCell(9);
            cellPolicyName.setCellValue(log.getPolicyName()); // 播放策略
            cellPolicyName.setCellStyle(style);

            Cell cellDetail = row.createCell(10);
            cellDetail.setCellValue(log.getDetail()); // 播放详情
            cellDetail.setCellStyle(style);

            Cell cellScreen = row.createCell(11);
            cellScreen.setCellValue(log.getScreen()); // 屏幕模式
            cellScreen.setCellStyle(style);

            Cell cellScreenPosition = row.createCell(12);
            cellScreenPosition.setCellValue("up".equals(log.getScreenPosition()) ? "上" : ("down".equals(log.getScreenPosition()) ? "下" : "中")); // 屏幕模式
            cellScreenPosition.setCellStyle(style);

            Cell cellFormerAdvertising = row.createCell(13);
            cellFormerAdvertising.setCellValue(log.getFormerAdvertising()); // 前广告
            cellFormerAdvertising.setCellStyle(style);

            Cell cellAfterTheAdvertising = row.createCell(14);
            cellAfterTheAdvertising.setCellValue(log.getAfterTheAdvertising()); // 后广告
            cellAfterTheAdvertising.setCellStyle(style);

            rowCount++;
        }
        FileOutputStream os=new FileOutputStream(file);
        wb.write(os);
    }
}
