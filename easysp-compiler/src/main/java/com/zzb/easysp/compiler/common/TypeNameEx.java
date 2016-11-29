package com.zzb.easysp.compiler.common;

import com.squareup.javapoet.ClassName;

/**
 * Created by ZZB on 2016/11/29.
 */

public class TypeNameEx {
    public static final ClassName CONTEXT = ClassName.get("android.content", "Context");
    public static final ClassName SHARED_PREFERENCES = ClassName.get("android.content", "SharedPreferences");
    public static final ClassName SP_HELPER = ClassName.get(Const.PACKAGE_NAME, Const.ClassName.SPHelper);
    public static final ClassName SP_EDITOR = ClassName.get("android.content.SharedPreferences", "Editor");
    public static final ClassName ACTIVITY = ClassName.get("android.app", "Activity");
    public static final ClassName STRING = ClassName.get("java.lang", "String");
}
