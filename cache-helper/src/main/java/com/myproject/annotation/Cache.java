package com.myproject.annotation;

import java.lang.annotation.*;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 10:17 2017/10/30
 * @Modified By
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
@Documented
public @interface Cache {
    String key() default "";

    int expireTime() default 0;

    boolean isCacheKey() default false;
}
