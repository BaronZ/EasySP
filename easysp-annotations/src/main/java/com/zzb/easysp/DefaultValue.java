package com.zzb.easysp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ZZB on 2016/11/30.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface DefaultValue {
    String defStr() default "";
    int defInt() default 0;
    long defLong() default 0;
    float defFloat() default 0f;
    boolean defBool() default false;
}
