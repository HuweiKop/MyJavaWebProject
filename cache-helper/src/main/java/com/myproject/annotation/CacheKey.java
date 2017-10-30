package com.myproject.annotation;

import java.lang.annotation.*;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 13:58 2017/10/30
 * @Modified By
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Inherited
@Documented
public @interface CacheKey {
}
