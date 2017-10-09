package com.hw.springboot.datasource;

import com.hw.springboot.DataSourceTypeConst;
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
public class DynamicDataSourceDaoAspect {
    /**
     * 对dao层 进行切面数据源 配置
     * 通过判断dao层方法名称中命名格式（select get 开头为读库），切换数据源
     * 可以省略通过注解方式的配置
     * 缺点：在事务中无法强制切换数据源（主从切换时，一般不需要在事务中切换数据源）
     * @param point
     * @throws Throwable
     */
    @Before("execution(* com.hw.springboot.dao..*.*(..))")
    public void changeDataSource(JoinPoint point) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        String methodName = method.getName();
        if(methodName.startsWith("select")||methodName.startsWith("get")){
            DataSourceTypeThreadLocal.setCurrentDataSourceType(DataSourceTypeConst.SlaverDataSource);
        }
        else{
            DataSourceTypeThreadLocal.setCurrentDataSourceType(DataSourceTypeConst.MasterDataSource);
        }
    }

    @After("execution(* com.hw.springboot.dao..*.*(..))")
    public void restoreDataSource(JoinPoint point) {
    }

}
