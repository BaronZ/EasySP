package com.zzb.easysp.compiler.gen;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.zzb.easysp.EasySp;
import com.zzb.easysp.compiler.common.Const;
import com.zzb.easysp.compiler.common.TypeNameEx;
import com.zzb.easysp.compiler.common.Utils;
import java.lang.reflect.Type;
import java.util.List;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;

/**
 * Created by ZZB on 2016/11/29.
 */

public class EasySpJavaMaker {
    private ProcessingEnvironment processingEnv;
    private RoundEnvironment roundEnv;
    private Elements elementUtils;

    public EasySpJavaMaker(ProcessingEnvironment processingEnv, RoundEnvironment roundEnv) {
        this.processingEnv = processingEnv;
        this.roundEnv = roundEnv;
        elementUtils = processingEnv.getElementUtils();
    }

    public void brewJava() {
        genEasySp();
    }

    private void genEasySp() {
        for (Element classElement : roundEnv.getElementsAnnotatedWith(EasySp.class)) {
            parseEasySpAndGen(classElement);
        }
    }

    private void parseEasySpAndGen(Element element) {
        List<VariableElement> fields = ElementFilter.fieldsIn(element.getEnclosedElements());
        String superClazz = element.getSimpleName().toString();
        String className = Const.LIBRARY_PREFIX + superClazz;
        TypeSpec.Builder clazzBuilder = TypeSpec.classBuilder(className)
                .superclass(ClassName.get(Utils.getPackageName(element), Utils.getClassName(element)))
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addField(TypeNameEx.STRING, "customFileName")
                .addField(TypeNameEx.CONTEXT, "context")
                .addField(TypeNameEx.SP_HELPER, "spHelper")
                .addMethod(privateConstructor())
                .addMethod(createWithCustomName(className))
                .addMethod(create(className));

        for (VariableElement field : fields) {
            if(Utils.isSupportedFieldType(field.asType())){
                clazzBuilder.addMethod(getter(field));
                clazzBuilder.addMethod(setter(field));
            }else{
                //clazzBuilder.addMethod(notSupport(field));
                //// TODO: 2016/11/29 有不支持的类型给提示 warning
            }
        }
        TypeSpec clazz = clazzBuilder.build();
        JavaMaker.brewJava(clazz, processingEnv);
    }
    private MethodSpec privateConstructor(){
        MethodSpec method = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .build();
        return method;
    }
    private MethodSpec create(String className){
        ClassName returnType = ClassName.get(Const.PACKAGE_NAME, className);
        MethodSpec method = MethodSpec.methodBuilder("create")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(TypeNameEx.CONTEXT, "context")
                .addStatement("return create(context, \"\")")
                .returns(returnType)
                .build();
        return method;
    }
    private MethodSpec createWithCustomName(String className){
        ClassName returnType = ClassName.get(Const.PACKAGE_NAME, className);
        MethodSpec method = MethodSpec.methodBuilder("create")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(TypeNameEx.CONTEXT, "context")
                .addParameter(TypeNameEx.STRING, "customFileName")
                .addStatement("$T pref = new $T()", returnType, returnType)
                .addStatement("pref.context = context")
                .beginControlFlow("if (customFileName == null || customFileName.length() == 0) ")
                .addStatement("customFileName = pref.getClass().getCanonicalName()")
                .endControlFlow()
                .addStatement("pref.spHelper = SPHelper.newInstance(context, customFileName)")
                .addStatement("return pref")
                .returns(returnType)
                .build();
        return method;
    }
    private MethodSpec getter(VariableElement field) {
        String fieldName = field.getSimpleName().toString();
        TypeMirror typeMirror = field.asType();
        Type type = Utils.getType(typeMirror);
        String parameter = "defaultValue";
        MethodSpec method = MethodSpec.methodBuilder(Utils.getGetterMethodName(fieldName))
                .addModifiers(Modifier.PUBLIC)
                .addStatement(Utils.getSpGetterStatement(typeMirror, fieldName, parameter))
                .returns(type)
                .build();
        return method;
    }

    private MethodSpec setter(VariableElement field) {
        String fieldName = field.getSimpleName().toString();
        TypeMirror typeMirror = field.asType();
        Type type = Utils.getType(typeMirror);
        String parameter = "value";
        MethodSpec method = MethodSpec.methodBuilder(Utils.getSetterMethodName(fieldName))
                .addModifiers(Modifier.PUBLIC)
                .addParameter(type, parameter)
                .addStatement(Utils.getSpSetterStatement(typeMirror, fieldName, parameter))
                .returns(void.class)
                .build();
        return method;
    }
    private MethodSpec notSupport(VariableElement field) {
        String fieldName = field.getSimpleName().toString();
        TypeMirror typeMirror = field.asType();
        Type type = Utils.getType(typeMirror);
        String parameter = "value";
        MethodSpec method = MethodSpec.methodBuilder("notSupport" + fieldName)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(type, parameter)
                .addStatement(Utils.getSpSetterStatement(typeMirror, fieldName, parameter))
                .returns(void.class)
                .build();
        return method;
    }
}
