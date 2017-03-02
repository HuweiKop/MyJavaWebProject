package com.myproject.framework.annotation;

import java.lang.annotation.*;

/**
 * Created by Wei Hu (J) on 2017/3/2.
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    String value() default "/";
}
