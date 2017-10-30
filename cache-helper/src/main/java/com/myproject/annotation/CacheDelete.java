package com.myproject.annotation;

import java.lang.annotation.*;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 15:12 2017/10/30
 * @Modified By
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
@Documented
public @interface CacheDelete {
    String key() default "";

    boolean isCacheKey() default false;
}
