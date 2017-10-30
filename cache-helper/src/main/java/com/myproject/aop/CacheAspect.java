package com.myproject.aop;

import com.alibaba.fastjson.JSON;
import com.myproject.annotation.Cache;
import com.myproject.cache.CacheFactory;
import com.myproject.utils.JedisHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 10:20 2017/10/30
 * @Modified By
 */
@Aspect
@Component
public class CacheAspect {

    //标注该方法体为后置通知，当目标方法执行成功后执行该方法体
    @Around("within(@org.springframework.stereotype.Service *) && @annotation(cache)")
    public Object recordMessage(ProceedingJoinPoint jp, Cache cache) throws Throwable {

        Object result = CacheFactory.getCache().getCacheData(cache);
        if(result==null){
            result = jp.proceed();
            CacheFactory.getCache().saveCacheData(cache,result);
        }
        return result;
    }
}
