package com.myproject.cache;

import com.alibaba.fastjson.JSON;
import com.myproject.annotation.Cache;
import com.myproject.annotation.CacheKey;
import com.myproject.constant.SymbolConst;
import com.myproject.utils.JedisHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 12:04 2017/10/30
 * @Modified By
 */
public class RedisCache implements ICache {

    @Override
    public Object getResult(Cache cache, ProceedingJoinPoint jp) throws Throwable {
        String key = getCacheKey(cache, jp);

        Object result = getCacheData(cache, key);
        if (result == null) {
            result = jp.proceed();
            saveCacheData(cache, key, result);
        }
        return result;
    }

    @Override
    public Object getCacheData(Cache cache, String key) {
        int expireTime = cache.expireTime();
        String jsonStr = JedisHelper.getInstance().get(key);
        Object result;
        if(StringUtils.isEmpty(jsonStr)){
            return null;
        }else {
            result = JSON.parse(jsonStr);
            JedisHelper.getInstance().expire(key,expireTime);
        }
        return result;
    }

    @Override
    public Object saveCacheData(Cache cache, String key, Object data) {
        int expireTime = cache.expireTime();
        String resultStr = JSON.toJSONString(data);
        JedisHelper.getInstance().set(key,resultStr,expireTime);
        return data;
    }

    @Override
    public String getCacheKey(Cache cache, ProceedingJoinPoint jp) {
        if(!cache.isCacheKey()){
            return cache.key();
        }
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Method method = methodSignature.getMethod();
        Parameter[] params = method.getParameters();
        Object[] args = jp.getArgs();

        StringBuilder sb = new StringBuilder(cache.key());

        for(int i=0;i<params.length;i++){
            if(params[i].isAnnotationPresent(CacheKey.class)){
                sb.append(SymbolConst.REDIS_INTERVAL_SYMBOL+args[i]);
            }
        }
        return sb.toString();
    }
}
