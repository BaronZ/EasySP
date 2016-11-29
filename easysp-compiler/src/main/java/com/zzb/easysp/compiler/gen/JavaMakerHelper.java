package com.zzb.easysp.compiler.gen;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.io.Writer;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * Created by ZZB on 2016/11/29.
 */

public class JavaMakerHelper {

    public static void brewJava(JavaFile javaFile, ProcessingEnvironment processingEnv, Element typeElement){
        Filer filer = processingEnv.getFiler();
        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
            //error(processingEnv, typeElement, "brew java failed for type %s: %s", typeElement, e.getMessage());
        }
    }
    private static void error(ProcessingEnvironment processingEnv, Element element, String message, Object... args) {
        printMessage(processingEnv, Diagnostic.Kind.ERROR, element, message, args);
    }
    private static void printMessage(ProcessingEnvironment processingEnv, Diagnostic.Kind kind, Element element, String message, Object[] args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }

        processingEnv.getMessager().printMessage(kind, message);
    }
}
