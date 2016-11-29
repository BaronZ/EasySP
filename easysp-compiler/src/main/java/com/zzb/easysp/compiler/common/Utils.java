package com.zzb.easysp.compiler.common;

import javax.lang.model.type.TypeMirror;

/**
 * Created by ZZB on 2016/11/29.
 */

public class Utils {
    public static String getSetterMethodName(String fieldName){
        return "set" + upperCaseFirst(fieldName);
    }
    public static String getGetterMethodName(String fieldName){
        return "get" + upperCaseFirst(fieldName);
    }
    public static boolean isSupportedFieldType(TypeMirror fieldType) {
        boolean isPrimitive = fieldType.getKind().isPrimitive();
        if (isPrimitive) {
            return true;
        }
        String fullTypeClassName = fieldType.toString();
        boolean isString = String.class.getName().equals(fullTypeClassName);
        if (isString) {
            return true;
        }
        return false;
    }
    public static String upperCaseFirst(String value) {

        // Convert String to char array.
        char[] array = value.toCharArray();
        // Modify first element in array.
        array[0] = Character.toUpperCase(array[0]);
        // Return string.
        return new String(array);
    }
}
