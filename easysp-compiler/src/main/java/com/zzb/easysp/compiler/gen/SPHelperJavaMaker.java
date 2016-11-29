package com.zzb.easysp.compiler.gen;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.zzb.easysp.compiler.common.TypeNameEx;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

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
                .build();


        JavaMaker.brewJava(clazz, processingEnv);

    }
    private MethodSpec setString(){
        MethodSpec method = MethodSpec.methodBuilder("setString")
                .addModifiers(Modifier.PUBLIC)
                .returns(String.class)
                .addParameter(String.class, "key")
                .addParameter(String.class, "defValue")
                .addStatement("return mSharedPreferences.getString(key, defValue)")
                .build();
        return method;
    }
    private MethodSpec getString(){
        MethodSpec method = MethodSpec.methodBuilder("getString")
                .addModifiers(Modifier.PUBLIC)
                .returns(void.class)
                .addParameter(String.class, "key")
                .addParameter(String.class, "value")
                .addStatement("mEditor.putString(key, value).apply()")
                .build();
        return method;
    }
    private MethodSpec newInstance(){
        MethodSpec method = MethodSpec.methodBuilder("newInstance")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(TypeNameEx.SP_HELPER)
                .addParameter(TypeNameEx.CONTEXT, "context")
                .addParameter(String.class, "fileName")
                .addStatement("SPHelper spHelper = new SPHelper()")
                .addStatement("spHelper.mContext = context")
                .addStatement("spHelper.mFileName = fileName")
                .addStatement("spHelper.mSharedPreferences = context.getSharedPreferences(fileName, $T.MODE_PRIVATE)", TypeNameEx.ACTIVITY)
                .addStatement("spHelper.mEditor = spHelper.mSharedPreferences.edit()")
                .addStatement("return spHelper")
                .build();
        return method;
    }
}
