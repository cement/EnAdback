package com.en.adback.controller;


import com.en.adback.entity.devicemgr.DeviceGroup;
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

public class DeviceMgrExcelCtrl {


    public void writeLogExcel(List<DeviceGroup> logsList) throws IOException {
        Resource resource=new ClassPathResource("/static/excels/devicegroupexcel.xls");
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
        for (DeviceGroup log:logsList){
            HSSFRow row=sheet.createRow(rowCount);


            Cell cellAdvertId = row.createCell(0); // 创建指定单元格对象。如本身有数据会替换掉
            cellAdvertId.setCellValue(log.getDevGroupName()); // 设备分组名称
            cellAdvertId.setCellStyle(style);

            Cell cellPutInKind = row.createCell(1); // 创建指定单元格对象。如本身有数据会替换掉
            cellPutInKind.setCellValue(log.getDeviceIds()==null?0:log.getDeviceIds().split(",").length); // 设备数量
            cellPutInKind.setCellStyle(style);

            Cell cellDate = row.createCell(2); // 创建指定单元格对象。如本身有数据会替换掉
            cellDate.setCellValue(log.getAddUser()); //录入账号
            cellDate.setCellStyle(style);

            Cell cellDeviceIds = row.createCell(3); // 创建指定单元格对象。如本身有数据会替换掉
            cellDeviceIds.setCellValue(log.getAddTime()); // 录入时间
            cellDeviceIds.setCellStyle(style);

            rowCount++;
        }
        FileOutputStream os=new FileOutputStream(file);
        wb.write(os);
    }

}
