package com.zyyydqpi.dpc;

/**
 * Created by strom on 2017/6/29.
 */

public class StringUtils {
    public static String join(String[] values, String separator){
        StringBuilder buffer = new StringBuilder();
        for(String value : values){
            buffer.append(separator);
            buffer.append(value);
        }
        return buffer.toString().replaceFirst(separator, "");
    }
}
