package com.ztth.api.path.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)//注解会在class中存在，运行时可通过反射获取
@Target(ElementType.METHOD)//目标是方法
public @interface LogAnnotation {

    String action() default "";
    String targetType() default "";
    String remark() default "";

}
