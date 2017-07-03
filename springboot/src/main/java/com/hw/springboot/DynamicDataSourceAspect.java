package com.hw.springboot;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by huwei on 2017/6/29.
 */
@Aspect
@Order(-1)// 保证该AOP在@Transactional之前执行
@Component
public class DynamicDataSourceAspect {
    @Before("execution(* com.hw.springboot.service..*.*(..))")
    public void changeDataSource(JoinPoint point) throws Throwable {
        TargetDataSource ds = point.getTarget().getClass().getAnnotation(TargetDataSource.class);
        if(ds==null){
            MethodSignature methodSignature = (MethodSignature) point.getSignature();
            Method method = methodSignature.getMethod();
            ds = method.getAnnotation(TargetDataSource.class);
        }
        if(ds==null){
            return;
        }
        int dsType = ds.type();
        String dsId = "defaultDataSource";
        if(dsType==DataSourceTypeConst.SlaverDataSource)
        {
            dsId = DataSourceMap.getDataSourceKeyRandom();
        }
//        String dsId = ds.value();
        System.out.println(dsId);
        if (!DynamicDataSourceContextHolder.containsDataSource(dsId)) {
//            logger.error("数据源[{}]不存在，使用默认数据源 > {}", ds.name(), point.getSignature());
        } else {
//            logger.debug("Use DataSource : {} > {}", ds.name(), point.getSignature());
            DynamicDataSourceContextHolder.setDataSourceType(dsId);
        }
    }

    @After("execution(* com.hw.springboot.service..*.*(..))")
    public void restoreDataSource(JoinPoint point) {
        TargetDataSource ds = point.getTarget().getClass().getAnnotation(TargetDataSource.class);
        if(ds==null){
            return;
        }
//        logger.debug("Revert DataSource : {} > {}", ds.name(), point.getSignature());
        System.out.println("restoreDataSource=====");
        DynamicDataSourceContextHolder.clearDataSourceType();
    }

}
