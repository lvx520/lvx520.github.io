package com.ssm.lv.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * @author lv
 * @date 2020/7/10 - 17:17
 */
public class DateUtils {

    public static String formatDate(Date date, String format){

        String res="";
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        if(date!=null){
            res=sdf.format(date);
        }
        return res;
    }
}
