package com.hw.mybatis.annotation;

import java.lang.annotation.*;

/**
 * Created by Wei Hu (J) on 2017/1/12.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TableName {
    String value() default "";
}
