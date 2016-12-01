package com.zzb.easysp.compiler.common;

import com.zzb.easysp.DefaultValue;
import java.lang.reflect.Type;
import java.util.Locale;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

/**
 * Created by ZZB on 2016/11/29.
 */

public class Utils {

    public static String getSetterMethodName(String fieldName) {
        return "set" + upperCaseFirst(fieldName);
    }

    public static String getGetterMethodName(String fieldName) {
        return "get" + upperCaseFirst(fieldName);
    }

    public static boolean isSupportedFieldType(TypeMirror fieldType) {
        TypeKind typeKind = fieldType.getKind();
        String typeClassName = fieldType.toString();
        if (typeKind == TypeKind.BOOLEAN || Boolean.class.getName().equals(typeClassName)) {
            return true;
        } else if (typeKind == TypeKind.INT || Integer.class.getName().equals(typeClassName)) {
            return true;
        } else if (typeKind == TypeKind.LONG || Long.class.getName().equals(typeClassName)) {
            return true;
        } else if (typeKind == TypeKind.FLOAT || Float.class.getName().equals(typeClassName)) {
            return true;
        } else if (String.class.getName().equals(typeClassName)) {
            return true;
        } else {
            return false;
        }
    }

    public static String getPackageName(Element element) {
        String fullName = element.asType().toString();
        return fullName.substring(0, fullName.lastIndexOf("."));
    }

    public static String getClassName(Element element) {
        return element.getSimpleName().toString();
    }

    public static Type getType(TypeMirror fieldType) {
        TypeKind typeKind = fieldType.getKind();
        String typeClassName = fieldType.toString();
        if (typeKind == TypeKind.BOOLEAN || Boolean.class.getName().equals(typeClassName)) {
            return typeKind == TypeKind.BOOLEAN ? boolean.class : Boolean.class;
        } else if (typeKind == TypeKind.INT || Integer.class.getName().equals(typeClassName)) {
            return typeKind == TypeKind.INT ? int.class : Integer.class;
        } else if (typeKind == TypeKind.LONG || Long.class.getName().equals(typeClassName)) {
            return typeKind == TypeKind.LONG ? long.class : Long.class;
        } else if (typeKind == TypeKind.FLOAT || Float.class.getName().equals(typeClassName)) {
            return typeKind == TypeKind.FLOAT ? float.class : Float.class;
        } else if (String.class.getName().equals(typeClassName)) {
            return String.class;
        } else {
            return Object.class;
        }
    }

    public static String getTypeName(TypeMirror fieldType) {
        TypeKind typeKind = fieldType.getKind();
        String typeClassName = fieldType.toString();
        if (typeKind == TypeKind.BOOLEAN || Boolean.class.getName().equals(typeClassName)) {
            return "Boolean";
        } else if (typeKind == TypeKind.INT || Integer.class.getName().equals(typeClassName)) {
            return "Int";
        } else if (typeKind == TypeKind.LONG || Long.class.getName().equals(typeClassName)) {
            return "Long";
        } else if (typeKind == TypeKind.FLOAT || Float.class.getName().equals(typeClassName)) {
            return "Float";
        } else if (String.class.getName().equals(typeClassName)) {
            return "String";
        } else {
            return "";
        }
    }

    private static String getDefaultValue(TypeMirror fieldType, DefaultValue defaultValue) {
        TypeKind typeKind = fieldType.getKind();
        String typeClassName = fieldType.toString();
        boolean hasDefaultValue = defaultValue != null;
        if (hasDefaultValue && defaultValue.value().length() > 0) {
            if (String.class.getName().equals(typeClassName)) {
                return "\"" + defaultValue.value() + "\"";
            } else {
                return defaultValue.value();
            }
        }
        if (typeKind == TypeKind.BOOLEAN || Boolean.class.getName().equals(typeClassName)) {
            return "false";
        } else if (typeKind == TypeKind.INT || Integer.class.getName().equals(typeClassName)) {
            return "0";
        } else if (typeKind == TypeKind.LONG || Long.class.getName().equals(typeClassName)) {
            return "0";
        } else if (typeKind == TypeKind.FLOAT || Float.class.getName().equals(typeClassName)) {
            return "0";
        } else if (String.class.getName().equals(typeClassName)) {
            return "\"\"";
        } else {
            return "";
        }
    }

    public static String getSpSetterStatement(TypeMirror typeMirror, String fieldName, String parameter) {
        String format = "spHelper.set%s(\"%s\", %s)";
        return String.format(Locale.US, format, getTypeName(typeMirror), fieldName, parameter);
    }

    public static String getSpGetterStatement(TypeMirror typeMirror, String fieldName, DefaultValue defaultValue) {
        String format = "return spHelper.get%s(\"%s\", %s)";
        return String.format(Locale.US, format, getTypeName(typeMirror), fieldName,
                getDefaultValue(typeMirror, defaultValue));
    }

    public static String upperCaseFirst(String value) {
        char[] array = value.toCharArray();
        array[0] = Character.toUpperCase(array[0]);
        return new String(array);
    }
}
