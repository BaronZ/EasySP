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
        TypeKind kind = fieldType.getKind();
        boolean isPrimitive = kind.isPrimitive();
        if (isPrimitive) {
            switch (kind) {
                case BOOLEAN:
                case INT:
                case LONG:
                case FLOAT:
                    return true;
                default:
                    return false;
            }
        }
        String fullTypeClassName = fieldType.toString();
        boolean isString = String.class.getName().equals(fullTypeClassName);
        if (isString) {
            return true;
        }
        return false;
    }

    public static Type getType(TypeMirror fieldType) {
        switch (fieldType.getKind()) {
            case BOOLEAN:
                return boolean.class;
            case INT:
                return int.class;
            case LONG:
                return long.class;
            case FLOAT:
                return float.class;
            default:
                String fullTypeClassName = fieldType.toString();
                boolean isString = String.class.getName().equals(fullTypeClassName);
                if (isString) {
                    return String.class;
                } else {
                    return Object.class;
                }
        }
    }

    public static String getTypeName(TypeMirror fieldType) {
        switch (fieldType.getKind()) {
            case BOOLEAN:
                return "Boolean";
            case INT:
                return "Int";
            case LONG:
                return "Long";
            case FLOAT:
                return "Float";
            default:
                String fullTypeClassName = fieldType.toString();
                boolean isString = String.class.getName().equals(fullTypeClassName);
                if (isString) {
                    return "String";
                } else {
                    return "";
                }
        }
    }

    public static String getSpSetterStatement(TypeMirror typeMirror, String fieldName, String parameter) {
        String format = "spHelper.set%s(\"%s\", %s)";
        return String.format(Locale.US, format, getTypeName(typeMirror), fieldName, parameter);
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
