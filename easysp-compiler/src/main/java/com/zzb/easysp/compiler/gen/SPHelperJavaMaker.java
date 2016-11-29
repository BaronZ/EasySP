package com.zzb.easysp.compiler.gen;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.zzb.easysp.compiler.common.TypeNameEx;
import java.lang.reflect.Type;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;

/**
 * Created by ZZB on 2016/11/28.
 */

public class SPHelperJavaMaker {

    public void brewJava(ProcessingEnvironment processingEnv) {

        TypeSpec clazz = TypeSpec.classBuilder("SPHelper")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addField(TypeNameEx.CONTEXT, "mContext", Modifier.PRIVATE)
                .addField(String.class, "mFileName", Modifier.PRIVATE)
                .addField(TypeNameEx.SHARED_PREFERENCES, "mSharedPreferences", Modifier.PRIVATE)
                .addField(TypeNameEx.SP_EDITOR, "mEditor", Modifier.PRIVATE)
                .addMethod(newInstance())
                .addMethod(setString())
                .addMethod(getString())
                .addMethod(setInt())
                .addMethod(getInt())
                .addMethod(setFloat())
                .addMethod(getFloat())
                .addMethod(setBoolean())
                .addMethod(getBoolean())
                .addMethod(setLong())
                .addMethod(getLong())
                .build();

        JavaMaker.brewJava(clazz, processingEnv);
    }

    private MethodSpec setString() {
        return spSetter(String.class, "String");
    }

    private MethodSpec getString() {
        return spGetter(String.class, "String");
    }
    private MethodSpec setInt() {
        return spSetter(int.class, "Int");
    }
    private MethodSpec getInt() {
        return spGetter(int.class, "Int");
    }

    private MethodSpec setFloat() {
        return spSetter(float.class, "Float");
    }
    private MethodSpec getFloat() {
        return spGetter(float.class, "Float");
    }

    private MethodSpec setBoolean() {
        return spSetter(boolean.class, "Boolean");
    }
    private MethodSpec getBoolean() {
        return spGetter(boolean.class, "Boolean");
    }

    private MethodSpec setLong() {
        return spSetter(long.class, "Long");
    }
    private MethodSpec getLong() {
        return spGetter(long.class, "Long");
    }



    private MethodSpec spSetter(Type type, String typeName) {
        MethodSpec method = MethodSpec.methodBuilder("set" + typeName)
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(String.class, "key")
                .addParameter(type, "value")
                .addStatement("mEditor.put" + typeName + "(key, value).apply()")
                .build();
        return method;
    }

    private MethodSpec spGetter(Type type, String typeName) {
        MethodSpec method = MethodSpec.methodBuilder("get" + typeName)
                .addModifiers(Modifier.PUBLIC)
                .returns(type)
                .addParameter(String.class, "key")
                .addParameter(type, "defValue")
                .addStatement("return mSharedPreferences.get" + typeName + "(key, defValue)")
                .build();
        return method;
    }

    private MethodSpec newInstance() {
        MethodSpec method = MethodSpec.methodBuilder("newInstance")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(TypeNameEx.SP_HELPER)
                .addParameter(TypeNameEx.CONTEXT, "context")
                .addParameter(String.class, "fileName")
                .addStatement("SPHelper spHelper = new SPHelper()")
                .addStatement("spHelper.mContext = context")
                .addStatement("spHelper.mFileName = fileName")
                .addStatement("spHelper.mSharedPreferences = context.getSharedPreferences(fileName, $T.MODE_PRIVATE)",
                        TypeNameEx.ACTIVITY)
                .addStatement("spHelper.mEditor = spHelper.mSharedPreferences.edit()")
                .addStatement("return spHelper")
                .build();
        return method;
    }
}
