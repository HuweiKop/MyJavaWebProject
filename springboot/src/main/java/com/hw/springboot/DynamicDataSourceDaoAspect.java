package com.hw.springboot;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by huwei on 2017/6/29.
 */
@Aspect
@Order(-1)// 保证该AOP在@Transactional之前执行
//@Component
public class DynamicDataSourceDaoAspect {
    @Before("execution(* com.hw.springboot.dao..*.*(..))")
    public void changeDataSource(JoinPoint point) throws Throwable {
        TargetDataSource ds = point.getTarget().getClass().getInterfaces()[0].getAnnotation(TargetDataSource.class);
        if(ds==null){
            return;
        }
        String dsId = ds.value();
        System.out.println(dsId);
        if (!DynamicDataSourceContextHolder.containsDataSource(dsId)) {
//            logger.error("数据源[{}]不存在，使用默认数据源 > {}", ds.name(), point.getSignature());
        } else {
//            logger.debug("Use DataSource : {} > {}", ds.name(), point.getSignature());
            DynamicDataSourceContextHolder.setDataSourceType(ds.value());
        }
    }

    @After("execution(* com.hw.springboot.dao..*.*(..))")
    public void restoreDataSource(JoinPoint point) {
        TargetDataSource ds = point.getTarget().getClass().getInterfaces()[0].getAnnotation(TargetDataSource.class);
        if(ds==null){
            return;
        }
//        logger.debug("Revert DataSource : {} > {}", ds.name(), point.getSignature());
        System.out.println("restoreDataSource=====");
        DynamicDataSourceContextHolder.clearDataSourceType();
    }

}
