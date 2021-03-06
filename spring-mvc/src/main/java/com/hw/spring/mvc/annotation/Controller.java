package com.hw.spring.mvc.annotation;

import java.lang.annotation.*;

/**
 * Created by Wei Hu (J) on 2017/3/10.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Controller {
    String value() default "";
}
