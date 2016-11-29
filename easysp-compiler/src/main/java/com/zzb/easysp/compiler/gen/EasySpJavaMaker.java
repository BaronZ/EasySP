package com.zzb.easysp.compiler.gen;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.zzb.easysp.EasySp;
import com.zzb.easysp.compiler.common.Const;
import java.util.List;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
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
        TypeSpec.Builder clazzBuilder = TypeSpec.classBuilder(Const.LIBRARY_PREFIX + element.getSimpleName().toString())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        for (VariableElement field : fields) {
            //clazzBuilder.addMethod(getter(field));
            //clazzBuilder.addMethod(setter(field));
        }
        TypeSpec clazz = clazzBuilder.build();
        JavaMaker.brewJava(clazz, processingEnv);
    }

    private MethodSpec getter(VariableElement field) {

        //TypeMirror fieldType = field.asType();
        //String fullTypeClassName = fieldType.toString();
        return null;
    }

    private MethodSpec setter(VariableElement field) {
        return null;
    }
}
