package com.en.adback.controller;

import com.en.adback.entity.Logs;
import com.en.adback.entity.advertmgr.Advert;
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

public class AdverrtExcelCtrl {

    //将数据写入表格里   日志表
    public void writeLogExcel(List<Advert> logsList) throws IOException {
        Resource resource=new ClassPathResource("/static/excels/advertupload.xls");
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
        for (Advert log:logsList){
            HSSFRow row=sheet.createRow(rowCount);

            Cell cellAdvertName = row.createCell(0); // 创建指定单元格对象。如本身有数据会替换掉
            cellAdvertName.setCellValue(log.getAdvertName()); // 广告名称
            cellAdvertName.setCellStyle(style);

            Cell cellAdCorpName = row.createCell(1);
            cellAdCorpName.setCellValue(log.getAdCorpName()); // 公司全称
            cellAdCorpName.setCellStyle(style);

            Cell cellBlankName = row.createCell(2);
            cellBlankName.setCellValue(log.getBlankName()); // 所属品牌
            cellBlankName.setCellStyle(style);

            Cell cellTradeName = row.createCell(3);
            cellTradeName.setCellValue(log.getTradeName());  // 行业
            cellTradeName.setCellStyle(style);

            Cell cellFileName = row.createCell(4);
            cellFileName.setCellValue(log.getFileName().substring(log.getFileName().indexOf(".")+1,log.getFileName().length()));// 广告格式
            cellFileName.setCellStyle(style);

            Cell cellDuration = row.createCell(5);
            cellDuration.setCellValue(log.getDuration());  // 广告时长
            cellDuration.setCellStyle(style);

            Cell cellFileSize = row.createCell(6);
            cellFileSize.setCellValue(log.getFileSize());// 文件大小
            cellFileSize.setCellStyle(style);

            Cell cellMaker = row.createCell(7);
            cellMaker.setCellValue(log.getMaker()); // 上传账号
            cellMaker.setCellStyle(style);

            Cell cellUploadTime = row.createCell(8);
            cellUploadTime.setCellValue(log.getUploadTime()); // 上传日期
            cellUploadTime.setCellStyle(style);

            Cell cellNowState = row.createCell(9);
            cellNowState.setCellValue(log.getNowState() == 1?"待审核":(log.getNowState() == 2?"审核中":(log.getNowState() == 3?"审核通过":(log.getNowState() == 4?"审核不通过":(log.getNowState() == 5?"设置策略":(log.getNowState() == 6?"待下发" :(log.getNowState() == 7?"已下发":(log.getNowState() == 8?"替换":(log.getNowState() == 9?"被替换":(log.getNowState() == 10?"自动下刊":(log.getNowState() == 11?"手动下刊":""))))))))))); // 广告状态
            cellNowState.setCellStyle(style);


            rowCount++;
        }
        FileOutputStream os=new FileOutputStream(file);
        wb.write(os);
    }
}
