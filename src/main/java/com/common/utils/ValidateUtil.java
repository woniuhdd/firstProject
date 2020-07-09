package com.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;

public class ValidateUtil {

    /**
     *  验证是否为正整数
     * @param count
     * @return
     */
    public static boolean checkCount(String count) {
        String regex = "^[1-9][0-9]*$";
        return match(regex, count);
    }

    //验证时间格式是否正确
    public static boolean checkDate(String date){
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            format.setLenient(false);
            Date tempDate=format.parse(date);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    private static boolean match(String regex, String str) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
