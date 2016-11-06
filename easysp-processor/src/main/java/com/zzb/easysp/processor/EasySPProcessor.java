package com.zzb.easysp.processor;

import com.zzb.easysp.processor.annotations.CustomKey;
import com.zzb.easysp.processor.annotations.DefaultValue;
import com.zzb.easysp.processor.annotations.EasySP;

import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

public class EasySPProcessor extends AbstractProcessor {
    public static final String PACKAGE_NAME = "com.zzb.easysp.processor.generated";
    private Messager mMessager;//The messager is how we send messages back to the user. We can't use System.out
    private Filer mFiler;// You use the filer to write your new files.

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        generateJavaFile(annotations, roundEnv);
        return true;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mMessager = processingEnv.getMessager();
        mFiler = processingEnv.getFiler();
    }

    private void generateJavaFile(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        generageJavaFile(roundEnv);
    }

    private void generageJavaFile(RoundEnvironment roundEnv) {
        StringBuilder builder = new StringBuilder();
        builder.append("package ").append(PACKAGE_NAME).append(";\n\n")
                .append("public class GeneratedClass {\n\n")
                .append("\tpublic String getMessage(){\n")
                .append("\t\treturn \"");
//         for each javax.lang.model.element.Element annotated with the CustomAnnotation
//        element can be filed, class, interface
        for (Element element : roundEnv.getElementsAnnotatedWith(EasySP.class)) {
            String objectType = element.getSimpleName().toString();
            // this is appending to the return statement
            builder.append(objectType).append(" says hello!\\n");
        }
        builder.append("\";\n") // end return
                .append("\t}\n") // close method
                .append("}\n"); // close class
        try { // write the file
            Filer filer = processingEnv.getFiler();
            JavaFileObject source = filer.createSourceFile(PACKAGE_NAME + ".GeneratedClass");
            Writer writer = source.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            // Note: calling e.printStackTrace() will print IO errors
            // that occur from the file already existing after its first run, this is normal
        }
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(CustomKey.class.getCanonicalName());
        set.add(DefaultValue.class.getCanonicalName());
        set.add(EasySP.class.getCanonicalName());
        return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

}
