package com.en.adback.controller.sys;

import com.en.adback.entity.sys.User;
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

/**
 * Created by Administrator on 2019/1/16.
 */
public class UserMakeExcelCtrl {

    //admin导出所有用户信息Excel
    public void writeAllUserExcel(List<User> userList) throws IOException {
        Resource resource=new ClassPathResource("/static/excels/userexcel.xls");
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

        for(User user:userList){
            HSSFRow row=sheet.createRow(rowCount);

            Cell cellUserId = row.createCell(0); // 创建指定单元格对象。如本身有数据会替换掉
            cellUserId.setCellValue(user.getUserId()); // 人员编号
            cellUserId.setCellStyle(style);

            Cell cellGroupRoleName = row.createCell(1);
            cellGroupRoleName.setCellValue(user.getGroupRoleName()); // 账户所属分组名称
            cellGroupRoleName.setCellStyle(style);

            Cell cellRoleName = row.createCell(2);
            cellRoleName.setCellValue(user.getRoleName()); // 角色
            cellRoleName.setCellStyle(style);

            Cell cellUserName = row.createCell(3);
            cellUserName.setCellValue(user.getUserName()); // 联系人
            cellUserName.setCellStyle(style);

            Cell cellTel = row.createCell(4);
            cellTel.setCellValue(user.getTel()); // 联系电话
            cellTel.setCellStyle(style);

            Cell cellGroupManager = row.createCell(5);
            cellGroupManager.setCellValue(user.getGroupManager()); // 是否为组管理员
            cellGroupManager.setCellStyle(style);

            rowCount++;
        }
        FileOutputStream os=new FileOutputStream(file);
        wb.write(os);
    }


    //组管理员导出本组用户信息Excel
    public void writeGroupUserExcel(List<User> userList) throws IOException {
        Resource resource=new ClassPathResource("/static/excels/user/userexcel.xls");
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

        for(User user:userList){
            HSSFRow row=sheet.createRow(rowCount);

            Cell cellUserId = row.createCell(0); // 创建指定单元格对象。如本身有数据会替换掉
            cellUserId.setCellValue(user.getUserId()); // 人员编号
            cellUserId.setCellStyle(style);

            Cell cellRoleName = row.createCell(1);
            cellRoleName.setCellValue(user.getRoleName()); // 角色
            cellRoleName.setCellStyle(style);

            Cell cellUserName = row.createCell(2);
            cellUserName.setCellValue(user.getUserName()); // 联系人
            cellUserName.setCellStyle(style);

            Cell cellTel = row.createCell(3);
            cellTel.setCellValue(user.getTel()); // 联系电话
            cellTel.setCellStyle(style);

            rowCount++;
        }
        FileOutputStream os=new FileOutputStream(file);
        wb.write(os);
    }
}
