package com.zzb.easysp.compiler.gen;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.zzb.easysp.compiler.common.Const;
import com.zzb.easysp.compiler.common.TypeNameEx;
import java.io.IOException;
import java.lang.annotation.ElementType;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Created by ZZB on 2016/11/28.
 */

public class SPHelperJavaMaker implements IJavaMaker {

    @Override
    public void brewJava(ProcessingEnvironment processingEnv, TypeElement elementType) {
        MethodSpec methodNewInstance = MethodSpec.methodBuilder("newInstance")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(TypeNameEx.SP_HELPER)
                .addParameter(TypeNameEx.CONTEXT, "context")
                .addParameter(String.class, "fileName")
                .addStatement("SPHelper spHelper = new SPHelper()")
                .addStatement("spHelper.mContext = context")
                .addStatement("spHelper.mFileName = fileName")
                .addStatement("return spHelper")
                .build();
        TypeSpec clazz = TypeSpec.classBuilder("SPHelper")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addField(TypeNameEx.CONTEXT, "mContext", Modifier.PRIVATE)
                .addField(String.class, "mFileName", Modifier.PRIVATE)
                .addMethod(methodNewInstance)
                .build();

        JavaFile javaFile = JavaFile.builder(Const.PACKAGE_NAME, clazz)
                .build();
        JavaMakerHelper.brewJava(javaFile, processingEnv, elementType);

    }
}
