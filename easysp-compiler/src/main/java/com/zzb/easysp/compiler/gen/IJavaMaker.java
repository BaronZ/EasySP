package com.zzb.easysp.compiler.gen;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

/**
 * Created by ZZB on 2016/11/28.
 */

public interface IJavaMaker {

    void brewJava(ProcessingEnvironment processingEnv, TypeElement elementType);
}
