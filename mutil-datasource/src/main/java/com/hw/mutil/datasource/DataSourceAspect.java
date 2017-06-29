package com.hw.mutil.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by huwei on 2017/6/28.
 */
@Component
@Aspect
@Order(0)
public class DataSourceAspect {
    @AfterReturning("within(@org.springframework.stereotype.Service *) && @annotation(ant)")
    public void afterReturning(DataSource ant) throws Throwable {
        DataSourceContextHolder.clearDataSourceType();
    }

    @Before("within(@org.springframework.stereotype.Service *) && @annotation(ant)")
    public void before(JoinPoint jp, DataSource ant) throws Throwable {
        String dataSource = ant.value();
        DataSourceContextHolder.setDataSourceType(dataSource);
    }
}
