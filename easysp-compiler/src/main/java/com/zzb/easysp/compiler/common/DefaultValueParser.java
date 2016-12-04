package com.zzb.easysp.compiler.common;

import com.zzb.easysp.DefaultValue;

import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

/**
 * Created by ZZB on 2016/12/4.
 */
public class DefaultValueParser {

    public static String getDefaultValue(TypeMirror fieldType, DefaultValue defaultValue) {
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
        } else if (Utils.isStringSet(fieldType)) {
            return "null";
        } else {
            return "";
        }
    }
}
