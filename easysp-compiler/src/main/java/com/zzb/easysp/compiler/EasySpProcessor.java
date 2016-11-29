package com.zzb.easysp.compiler;

import com.google.auto.service.AutoService;
import com.zzb.easysp.EasySp;
import com.zzb.easysp.compiler.gen.EasySpJavaMaker;
import com.zzb.easysp.compiler.gen.SPHelperJavaMaker;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;

/**
 * Created by ZZB on 2016/11/28.
 */
@AutoService(Processor.class)
public class EasySpProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        genSPHelper();
        genEasySp(roundEnv);

        return true;
    }



    private void genSPHelper() {
        new SPHelperJavaMaker().brewJava(processingEnv);
    }

    private void genEasySp(RoundEnvironment roundEnv) {
        new EasySpJavaMaker(processingEnv, roundEnv).brewJava();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        for (Class<? extends Annotation> annotation : getSupportedAnnotations()) {
            types.add(annotation.getCanonicalName());
        }
        return types;
    }

    private Set<Class<? extends Annotation>> getSupportedAnnotations() {
        Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        annotations.add(EasySp.class);
        return annotations;
    }
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
