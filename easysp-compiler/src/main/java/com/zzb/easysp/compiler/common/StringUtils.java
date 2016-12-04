package com.zzb.easysp.compiler.common;

/**
 * Created by ZZB on 2016/12/4.
 */
public class StringUtils {
    public static String upperCaseFirst(String value) {
        char[] array = value.toCharArray();
        array[0] = Character.toUpperCase(array[0]);
        return new String(array);
    }
}
