package com.hw.springboot;

import org.aspectj.lang.JoinPoint;
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
//@Component
public class DynamicDataSourceAspect {
    /**
     * 通过注解方式在service层 实现数据源切换。
     * 需要针对需要切换数据源的service 类 配置相对应注解
     * 可以通过配置事务传播性，在事务中强制切换数据源
     * @param point
     * @throws Throwable
     */
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
        // 判断当前使用的数据源类型是否一致
        // 如果一致则不进行 切换数据源操作
//        Integer currentDataSourceType = DataSourceTypeThreadLocal.getCurrentDataSourceType();
//        if(currentDataSourceType!=null&&currentDataSourceType==dsType){
//            return;
//        }
//        DataSourceTypeThreadLocal.setCurrentDataSourceType(dsType);
        String dsId = DataSourceKeyConst.defaultDataSource;
        if(dsType==DataSourceTypeConst.SlaverDataSource)
        {
            dsId = SlaverDataSourceMap.getDataSourceKeyRandom();
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
