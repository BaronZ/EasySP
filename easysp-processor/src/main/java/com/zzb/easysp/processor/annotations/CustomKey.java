package com.zzb.easysp.processor.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ZZB on 2016/11/6.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)//@Retention annotation that tells the compiler how long it needs to keep these annotations around. In our case, we’re using a source RetentionPolicy
public @interface CustomKey {

}
