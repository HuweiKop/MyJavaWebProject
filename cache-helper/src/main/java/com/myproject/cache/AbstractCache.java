package com.myproject.cache;

import com.myproject.annotation.Cache;
import com.myproject.annotation.CacheKey;
import com.myproject.constant.SymbolConst;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 10:45 2017/12/21
 * @Modified By
 */
public abstract class AbstractCache implements ICache {

    private Map<String, Parameter[]> parameterMap = new ConcurrentHashMap<>();

    @Override
    public Object getResult(Cache cache, ProceedingJoinPoint jp) throws Throwable {
        String key = cache.key();
        if (cache.isCacheKey()) {
            key = getCacheKey(key, jp);
        }

        Object result = getCacheData(cache, key);
        if (result == null) {
            result = jp.proceed();
            saveCacheData(cache, key, result);
        }
        return result;
    }

    protected String getCacheKey(String key, ProceedingJoinPoint jp) {
        Parameter[] params = parameterMap.get(key);
        if (params == null) {
            MethodSignature methodSignature = (MethodSignature) jp.getSignature();
            Method method = methodSignature.getMethod();
            params = method.getParameters();
            parameterMap.put(key, params);
        }
        Object[] args = jp.getArgs();

        StringBuilder sb = new StringBuilder(key);

        for (int i = 0; i < params.length; i++) {
            if (params[i].isAnnotationPresent(CacheKey.class)) {
                sb.append(SymbolConst.REDIS_INTERVAL_SYMBOL + args[i]);
            }
        }
        return sb.toString();
    }
}
