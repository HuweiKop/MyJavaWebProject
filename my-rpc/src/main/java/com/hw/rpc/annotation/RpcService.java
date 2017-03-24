package com.hw.rpc.annotation;

import java.lang.annotation.*;

/**
 * Created by huwei on 2017/3/22.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RpcService {
    String value() default "";
}
