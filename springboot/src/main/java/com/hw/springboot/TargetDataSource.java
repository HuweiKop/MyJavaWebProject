package com.hw.springboot;

import java.lang.annotation.*;

/**
 * Created by huwei on 2017/6/29.
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
    String value() default DataSourceKeyConst.defaultDataSource;

    int type() default DataSourceTypeConst.MasterDataSource;
}
