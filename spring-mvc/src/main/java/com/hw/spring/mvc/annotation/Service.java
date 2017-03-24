package com.hw.spring.mvc.annotation;

import java.lang.annotation.*;

/**
 * Created by Wei Hu (J) on 2017/3/10.
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Service {
    String value() default "";
}
