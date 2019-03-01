package com.en.adback.controller.advertmgr;

import com.en.adback.common.CutFileSize;
import com.en.adback.entity.advertmgr.Advert;
import com.en.adback.entity.advertmgr.AdvertPolicys;
import com.en.adback.entity.advertmgr.AdvertPutIn;
import com.en.adback.entity.advertmgr.PlayPolicyScreen;
import com.en.adback.service.advertmgr.IAdvertPolicyService;
import com.en.adback.serviceimp.advertmgr.advertPolicyServiceImp;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2019/1/17.
 */
public class AdvertCheckExcelCtrl {

   // @Autowired private advertPolicyServiceImp svr;

    private String getPolicyName(String screenPolicyId){
        advertPolicyServiceImp svr = new advertPolicyServiceImp();
        List<PlayPolicyScreen> pps = svr.allPlayPolicyScreen();
        String policyName=null;
        for (int i = 0; i < pps.size(); i++) {
            if(pps.get(i).getScreenpolicyId().equals(screenPolicyId)){
                policyName=pps.get(i).getPolicyName().toString();
            }
        }
        return policyName;
    }

    //广告审核
    public void writeAdvertCheckExcel(List<Advert> advertList) throws IOException {
        CutFileSize cfs = new CutFileSize();
        Resource resource=new ClassPathResource("/static/excels/advert.xls");
        File file=resource.getFile();
        HSSFWorkbook wb=new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet sheet=wb.getSheetAt(0);
        sheet.setDefaultColumnWidth(15);
        int rowCount=1;
        int v = sheet.getLastRowNum();
        for (int i = 0; i <=v; i++) {
            if(sheet.getRow(i)!=null){
            sheet.removeRow(sheet.getRow(i));
            }
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

        HSSFRow rowFist = sheet.createRow(0);
        Cell cell= rowFist.createCell(0); // 创建指定单元格对象。如本身有数据会替换掉
        cell .setCellValue("广告名称"); // 广告名称
        cell.setCellStyle(style);
        Cell cell1= rowFist.createCell(1); // 创建指定单元格对象。如本身有数据会替换掉
        cell1 .setCellValue("广告公司名称");
        cell1.setCellStyle(style);
        Cell cell2= rowFist.createCell(2); // 创建指定单元格对象。如本身有数据会替换掉
        cell2 .setCellValue("所属品牌");
        cell2.setCellStyle(style);
        Cell cell3= rowFist.createCell(3); // 创建指定单元格对象。如本身有数据会替换掉
        cell3 .setCellValue("行业");
        cell3.setCellStyle(style);
        Cell cell4= rowFist.createCell(4); // 创建指定单元格对象。如本身有数据会替换掉
        cell4 .setCellValue("广告格式");
        cell4.setCellStyle(style);
        Cell cell5= rowFist.createCell(5);
        cell5 .setCellValue("广告时长");
        cell5.setCellStyle(style);
        Cell cell6= rowFist.createCell(6);
        cell6 .setCellValue("文件大小");
        cell6.setCellStyle(style);
        Cell cell7= rowFist.createCell(7);
        cell7 .setCellValue("上传账号");
        cell7.setCellStyle(style);
        Cell cell8= rowFist.createCell(8);
        cell8 .setCellValue("上传日期");
        cell8.setCellStyle(style);
        Cell cell9= rowFist.createCell(9);
        cell9 .setCellValue("当前状态");
        cell9.setCellStyle(style);

        for(Advert advert:advertList){
            HSSFRow row=sheet.createRow(rowCount);

            Cell cellAdvertName = row.createCell(0); // 创建指定单元格对象。如本身有数据会替换掉
            cellAdvertName.setCellValue(advert.getAdvertName()); // 广告名称
            cellAdvertName.setCellStyle(style);

            Cell cellAdCorpName = row.createCell(1); // 创建指定单元格对象。如本身有数据会替换掉
            cellAdCorpName.setCellValue(advert.getAdCorpName()); // 广告公司名称
            cellAdCorpName.setCellStyle(style);

            Cell cellBlankName = row.createCell(2);
            cellBlankName.setCellValue(advert.getBlankName()); // 所属品牌
            cellBlankName.setCellStyle(style);

            Cell cellTradeName = row.createCell(3);
            cellTradeName.setCellValue(advert.getTradeName()); // 行业
            cellTradeName.setCellStyle(style);

            Cell cellFileName = row.createCell(4);
            cellFileName.setCellValue(advert.getFileName().split("\\.")[advert.getFileName().split("\\.").length-1]); // 广告格式
            cellFileName.setCellStyle(style);

            Cell cellDuration = row.createCell(5);
            cellDuration.setCellValue(advert.getDuration()); // 广告时长
            cellDuration.setCellStyle(style);

            Cell cellFileSize = row.createCell(6);
            cellFileSize.setCellValue(cfs.cutFileSize(advert.getFileSize())); // 文件大小
            cellFileSize.setCellStyle(style);

            Cell cellMaker = row.createCell(7);
            cellMaker.setCellValue(advert.getMaker()); // 上传账号
            cellMaker.setCellStyle(style);

            Cell cellUploadTime = row.createCell(8);
            cellUploadTime.setCellValue(advert.getUploadTime()); // 上传日期
            cellUploadTime.setCellStyle(style);

            Cell cellState = row.createCell(9);
            cellState.setCellValue(advert.getState()); // 当前状态
            cellState.setCellStyle(style);
            rowCount++;
        }
        FileOutputStream os=new FileOutputStream(file);
        wb.write(os);
    }

    //广告分发
    public void writeAdvertPutinExcel(List<AdvertPutIn> advertList) throws IOException {
        CutFileSize cfs = new CutFileSize();
        Resource resource=new ClassPathResource("/static/excels/advert.xls");
        File file=resource.getFile();
        HSSFWorkbook wb=new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet sheet=wb.getSheetAt(0);
        sheet.setDefaultColumnWidth(15);
        int rowCount=1;
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            if(null != sheet.getRow(i)){
                sheet.removeRow(sheet.getRow(i));
            }
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

        //生成表头
        HSSFRow rowFist=sheet.createRow(0);
        String advertTitle[] = {"广告编号","广告名称","格式","时长","大小","投放类别","播放形式","播放策略","屏幕模式","位置","发布天数","审核日期","状态"};
        for(int i =0;i<advertTitle.length;i++){
            Cell title = rowFist.createCell(i);
            title.setCellValue(advertTitle[i].toString());
            title.setCellStyle(style);
        }

        for(AdvertPutIn advertPutIn:advertList){
            HSSFRow row=sheet.createRow(rowCount);

            Cell cellAdvertId = row.createCell(0); // 创建指定单元格对象。如本身有数据会替换掉
            cellAdvertId.setCellValue(advertPutIn.getAdvertId()); // 广告编号
            cellAdvertId.setCellStyle(style);

            Cell cellAdvertName = row.createCell(1); // 创建指定单元格对象。如本身有数据会替换掉
            cellAdvertName.setCellValue(advertPutIn.getAdvertName()); // 广告名称
            cellAdvertName.setCellStyle(style);

            Cell cellFileName = row.createCell(2);
            cellFileName.setCellValue(advertPutIn.getFileName().split("\\.")[advertPutIn.getFileName().split("\\.").length-1]); // 格式
            cellFileName.setCellStyle(style);

            Cell cellDuration = row.createCell(3);
            cellDuration.setCellValue(advertPutIn.getDuration()); // 时长
            cellDuration.setCellStyle(style);

            Cell cellFileSize = row.createCell(4);
            cellFileSize.setCellValue(cfs.cutFileSize(advertPutIn.getFileSize())); // 大小
            cellFileSize.setCellStyle(style);

            Cell cellPutInKind = row.createCell(5);
            cellPutInKind.setCellValue(advertPutIn.getPutInKind()); //投放类别
            cellPutInKind.setCellStyle(style);

            Cell cellPlayAloneString = row.createCell(6);
            cellPlayAloneString.setCellValue(advertPutIn.getPlayAloneString()); // 播放形式
            cellPlayAloneString.setCellStyle(style);

            Cell cellPolicyName = row.createCell(7);
            cellPolicyName.setCellValue(getPolicyName(advertPutIn.getScreenPolicyId())); //播放策略
            cellPolicyName.setCellStyle(style);

            Cell cellScreen = row.createCell(8);
            cellScreen.setCellValue(advertPutIn.getScreen()); // 屏幕模式
            cellScreen.setCellStyle(style);

            Cell cellScreenPosition = row.createCell(9);
            cellScreenPosition.setCellValue("up".equals(advertPutIn.getScreenPosition())?"上屏":("mid".equals(advertPutIn.getScreenPosition())?"中屏":("down".equals(advertPutIn.getScreenPosition())?"下屏":""))); // 位置
            cellScreenPosition.setCellStyle(style);

            Cell cellPlayDays = row.createCell(10);
            cellPlayDays.setCellValue(advertPutIn.getPlayDays()); // 发布天数
            cellPlayDays.setCellStyle(style);

            Cell cellCheckDay = row.createCell(11);
            cellCheckDay.setCellValue(advertPutIn.getCheckDay()); // 审核日期
            cellCheckDay.setCellStyle(style);

            Cell cellState = row.createCell(12);
            cellState.setCellValue(advertPutIn.getState()); // 当前状态
            cellState.setCellStyle(style);

            rowCount++;
        }
        FileOutputStream os=new FileOutputStream(file);
        wb.write(os);
    }

    //分发管理
    public void writeAdvertSendMsgExcel(List<AdvertPutIn> advertSendList) throws IOException {
        CutFileSize cfs = new CutFileSize();
        Resource resource=new ClassPathResource("/static/excels/advert.xls");
        File file=resource.getFile();
        HSSFWorkbook wb=new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet sheet=wb.getSheetAt(0);
        sheet.setDefaultColumnWidth(15);
        int rowCount=0;
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            if(null != sheet.getRow(i)){
                sheet.removeRow(sheet.getRow(i));
            }
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

        //生成表头
        HSSFRow rowFist=sheet.createRow(rowCount);
        String advertSendMsgTitle[] = {"广告编号","广告名称","格式","时长","大小","投放类别","播放形式","播放策略","屏幕模式","位置","发布起始日期","发布结束日期","状态"};
        for(int i =0;i<advertSendMsgTitle.length;i++){
            Cell title = rowFist.createCell(i);
            title.setCellValue(advertSendMsgTitle[i].toString());
            title.setCellStyle(style);
        }
        rowCount+=1;

        for(AdvertPutIn advertSend:advertSendList){
            HSSFRow row=sheet.createRow(rowCount);

            Cell cellAdvertId = row.createCell(0); // 创建指定单元格对象。如本身有数据会替换掉
            cellAdvertId.setCellValue(advertSend.getAdvertId()); // 广告编号
            cellAdvertId.setCellStyle(style);

            Cell cellAdvertName = row.createCell(1); // 创建指定单元格对象。如本身有数据会替换掉
            cellAdvertName.setCellValue(advertSend.getAdvertName()); // 广告名称
            cellAdvertName.setCellStyle(style);

            Cell cellFileName = row.createCell(2);
            cellFileName.setCellValue(advertSend.getFileName().split("\\.")[advertSend.getFileName().split("\\.").length-1]); // 格式
            cellFileName.setCellStyle(style);

            Cell cellDuration = row.createCell(3);
            cellDuration.setCellValue(advertSend.getDuration()); // 时长
            cellDuration.setCellStyle(style);

            Cell cellFileSize = row.createCell(4);
            cellFileSize.setCellValue(cfs.cutFileSize(advertSend.getFileSize())); // 大小
            cellFileSize.setCellStyle(style);

            Cell cellPutInKind = row.createCell(5);
            if("1".equals(advertSend.getPutInKind())){
                cellPutInKind.setCellValue("购买"); //投放类别
            }else if("2".equals(advertSend.getPutInKind())){
                cellPutInKind.setCellValue("赠送");
            }
            cellPutInKind.setCellStyle(style);

            Cell cellPlayAloneString = row.createCell(6);
            if(1==advertSend.getPlayAlone()){
                cellPlayAloneString.setCellValue("独播"); // 播放形式
            }else if(2==advertSend.getPlayAlone()){
                cellPlayAloneString.setCellValue("轮播"); // 播放形式
            }
            cellPlayAloneString.setCellStyle(style);

            Cell cellPolicyName = row.createCell(7);
            cellPolicyName.setCellValue(getPolicyName(advertSend.getScreenPolicyId())); //播放策略
            cellPolicyName.setCellStyle(style);

            Cell cellScreen = row.createCell(8);
            cellScreen.setCellValue(advertSend.getScreen()); // 屏幕模式
            cellScreen.setCellStyle(style);

            Cell cellScreenPosition = row.createCell(9);
            cellScreenPosition.setCellValue("up".equals(advertSend.getScreenPosition())?"上屏":("mid".equals(advertSend.getScreenPosition())?"中屏":("down".equals(advertSend.getScreenPosition())?"下屏":""))); // 位置
            cellScreenPosition.setCellStyle(style);

            Cell cellPlayBeginTime = row.createCell(10);
            cellPlayBeginTime.setCellValue("".equals(advertSend.getPlayDays())||null == advertSend.getPlayDays()?"":advertSend.getPlayDays().split("\\,")[0]); // 发布起始时间
            cellPlayBeginTime.setCellStyle(style);

            Cell cellPlayEndTime = row.createCell(11);
            cellPlayEndTime.setCellValue("".equals(advertSend.getPlayDays())||null == advertSend.getPlayDays()?"":advertSend.getPlayDays().split("\\,")[advertSend.getPlayDays().split("\\,").length-1]); // 发布截止时间
            cellPlayEndTime.setCellStyle(style);

            Cell cellState = row.createCell(12);
            if(1==advertSend.getNowState()){
                cellState.setCellValue("加入未提交审核"); // 当前状态
            }else if(2==advertSend.getNowState()){
                cellState.setCellValue("审核中");
            }else if(3==advertSend.getNowState()){
                cellState.setCellValue("审核通过");
            }else if(4==advertSend.getNowState()){
                cellState.setCellValue("审核不通过");
            }else if(5==advertSend.getNowState()){
                cellState.setCellValue("设置策略");
            }else if(6==advertSend.getNowState()){
                cellState.setCellValue("待分发");
            }else if(7==advertSend.getNowState()){
                cellState.setCellValue("已下发到设备");
            }else if(8==advertSend.getNowState()){
                cellState.setCellValue("替换");
            }else if(9==advertSend.getNowState()){
                cellState.setCellValue("被替换");
            }else if(10==advertSend.getNowState()){
                cellState.setCellValue("自动下刊");
            }else if(11==advertSend.getNowState()){
                cellState.setCellValue("手动下刊");
            }

            cellState.setCellStyle(style);

            rowCount++;
        }
        FileOutputStream os=new FileOutputStream(file);
        wb.write(os);
    }
}
