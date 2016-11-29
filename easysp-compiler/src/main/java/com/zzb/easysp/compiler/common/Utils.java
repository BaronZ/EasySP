package com.zzb.easysp.compiler.common;

import java.lang.reflect.Type;
import java.util.Locale;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

/**
 * Created by ZZB on 2016/11/29.
 */

public class Utils {
    public static String getFileName(Class clz, String customFileName) {
        if (customFileName != null && customFileName.length() > 0) {
            return customFileName;
        } else {
            return clz.getCanonicalName();
        }
    }

    public static String getSetterMethodName(String fieldName) {
        return "set" + upperCaseFirst(fieldName);
    }

    public static String getGetterMethodName(String fieldName) {
        return "get" + upperCaseFirst(fieldName);
    }

    public static boolean isSupportedFieldType(TypeMirror fieldType) {
        TypeKind typeKind = fieldType.getKind();
        String typeClassName = fieldType.toString();
        if(typeKind == TypeKind.BOOLEAN || Boolean.class.getName().equals(typeClassName)){
            return true;
        }else if(typeKind == TypeKind.INT || Integer.class.getName().equals(typeClassName)){
            return true;
        }else if(typeKind == TypeKind.LONG || Long.class.getName().equals(typeClassName)){
            return true;
        }else if(typeKind == TypeKind.FLOAT || Float.class.getName().equals(typeClassName)){
            return true;
        }else if(String.class.getName().equals(typeClassName)){
            return true;
        }else {
            return false;
        }
    }

    public static Type getType(TypeMirror fieldType) {
        TypeKind typeKind = fieldType.getKind();
        String typeClassName = fieldType.toString();
        if(typeKind == TypeKind.BOOLEAN || Boolean.class.getName().equals(typeClassName)){
            return boolean.class;
        }else if(typeKind == TypeKind.INT || Integer.class.getName().equals(typeClassName)){
            return int.class;
        }else if(typeKind == TypeKind.LONG || Long.class.getName().equals(typeClassName)){
            return long.class;
        }else if(typeKind == TypeKind.FLOAT || Float.class.getName().equals(typeClassName)){
            return float.class;
        }else if(String.class.getName().equals(typeClassName)){
            return String.class;
        }else {
            return Object.class;
        }
    }

    public static String getTypeName(TypeMirror fieldType) {
        TypeKind typeKind = fieldType.getKind();
        String typeClassName = fieldType.toString();
        if(typeKind == TypeKind.BOOLEAN || Boolean.class.getName().equals(typeClassName)){
            return "Boolean";
        }else if(typeKind == TypeKind.INT || Integer.class.getName().equals(typeClassName)){
            return "Int";
        }else if(typeKind == TypeKind.LONG || Long.class.getName().equals(typeClassName)){
            return "Long";
        }else if(typeKind == TypeKind.FLOAT || Float.class.getName().equals(typeClassName)){
            return "Float";
        }else if(String.class.getName().equals(typeClassName)){
            return "String";
        }else {
            return "";
        }
    }
    private static String getDefaultValue(TypeMirror fieldType){
        //TODO hard code here, will support later
        TypeKind typeKind = fieldType.getKind();
        String typeClassName = fieldType.toString();
        if(typeKind == TypeKind.BOOLEAN || Boolean.class.getName().equals(typeClassName)){
            return "false";
        }else if(typeKind == TypeKind.INT || Integer.class.getName().equals(typeClassName)){
            return "0";
        }else if(typeKind == TypeKind.LONG || Long.class.getName().equals(typeClassName)){
            return "0";
        }else if(typeKind == TypeKind.FLOAT || Float.class.getName().equals(typeClassName)){
            return "0";
        }else if(String.class.getName().equals(typeClassName)){
            return "\"\"";
        }else {
            return "";
        }
    }
    public static String getSpSetterStatement(TypeMirror typeMirror, String fieldName, String parameter) {
        String format = "spHelper.set%s(\"%s\", %s)";
        return String.format(Locale.US, format, getTypeName(typeMirror), fieldName, parameter);
    }
    public static String getSpGetterStatement(TypeMirror typeMirror, String fieldName, String parameter) {
        String format = "return spHelper.get%s(\"%s\", %s)";
        return String.format(Locale.US, format, getTypeName(typeMirror), fieldName, getDefaultValue(typeMirror));
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
