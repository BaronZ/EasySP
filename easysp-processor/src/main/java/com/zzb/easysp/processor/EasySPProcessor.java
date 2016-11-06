package com.zzb.easysp.processor;

import com.zzb.easysp.processor.annotations.CustomKey;
import com.zzb.easysp.processor.annotations.DefaultValue;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

public class EasySPProcessor extends AbstractProcessor {
    private Messager mMessager;//The messager is how we send messages back to the user. We can't use System.out
    private Filer mFiler;// You use the filer to write your new files.

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        return true;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mMessager = processingEnv.getMessager();
        mFiler = processingEnv.getFiler();
    }

    private void createJavaFile() {

    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(CustomKey.class.getCanonicalName());
        set.add(DefaultValue.class.getCanonicalName());
        return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

}
