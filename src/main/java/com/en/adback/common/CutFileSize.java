package com.en.adback.common;

import java.text.DecimalFormat;
import java.util.Optional;

public class CutFileSize {

    public String cutFileSize(String fileSize){
        String newFileSize=null;

        if (fileSize.length()>0){
            Double size=Double.parseDouble(fileSize.substring(0,fileSize.length()-1));
            DecimalFormat df = new DecimalFormat("#.00");
            newFileSize=df.format(size).toString()+fileSize.substring(fileSize.length()-1,fileSize.length());
        }else{
            newFileSize=fileSize;
        }
        return newFileSize;
    }
}
