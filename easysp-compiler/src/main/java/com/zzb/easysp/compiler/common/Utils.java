package com.zzb.easysp.compiler.common;

import javax.lang.model.type.TypeMirror;

/**
 * Created by ZZB on 2016/11/29.
 */

public class Utils {

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
}
