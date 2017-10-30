package com.myproject.aop;

import com.myproject.annotation.Cache;
import com.myproject.annotation.CacheDelete;
import com.myproject.cache.CacheFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 15:15 2017/10/30
 * @Modified By
 */
@Aspect
@Component
public class CacheDeleteAspect {

    //标注该方法体为后置通知，当目标方法执行成功后执行该方法体
    @Around("within(@org.springframework.stereotype.Service *) && @annotation(cache)")
    public Object recordMessage(ProceedingJoinPoint jp, CacheDelete cache) throws Throwable {

        CacheFactory.getCache().deleteCache(cache, jp);

        return jp.proceed();
    }
}
