package com.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ToolUtils {

    public  static  String toString(Object obj){
        if(obj == null){
            return "";
        }
        return  obj.toString();
    }

    public static String getPrimaryId(String formWhere) {
        SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmssSSS");//设置日期格式
        //取5位随机数十六进制数据，范围为4096-65535，十六进制1000-FFFF;
        //防止0开头，直接取2-4位
        //用随机数，防止并发
        String uuid = UUID.randomUUID().toString();
        Long rand = Long.valueOf(("1" + uuid.replace("-", "").substring(13, 28)), 16);// 13, 28
        return df.format(new Date()).toString() + rand.toString().substring(5, 13) + formWhere;
    }

    /**
     * 转换短日期格式的字符串为日期.
     */
    public static Date fromShortFormat(final String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = sdf.parse(s);
            return d;
        } catch (ParseException e) {
            return null;
        }
    }
}
