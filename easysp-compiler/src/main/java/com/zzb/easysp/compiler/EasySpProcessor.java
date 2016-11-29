package com.zzb.easysp.compiler;

import com.google.auto.service.AutoService;
import com.zzb.easysp.EasySp;
import com.zzb.easysp.compiler.gen.SPHelperJavaMaker;
import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

/**
 * Created by ZZB on 2016/11/28.
 */
@AutoService(Processor.class)
public class EasySpProcessor extends AbstractProcessor {

    private Messager mMessager;//The messager is how we send messages back to the user. We can't use System.out
    private Filer mFiler;// You use the filer to write your new files.

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mMessager = processingEnv.getMessager();
        mFiler = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //genEasySP(null);
        genSPHelper(null);
        parseEasySpAndGen(null);
        return true;
    }

    private void parseEasySpAndGen(TypeElement element) {

    }

    private void genSPHelper(TypeElement element) {
        new SPHelperJavaMaker().brewJava(processingEnv, element);
    }

    //private void genEasySP(TypeElement element) {
    //
    //}

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
