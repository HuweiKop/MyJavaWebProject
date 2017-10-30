package com.myproject.cache;

import com.myproject.annotation.Cache;
import com.myproject.annotation.CacheDelete;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 12:02 2017/10/30
 * @Modified By
 */
public interface ICache {

    Object getResult(Cache cache, ProceedingJoinPoint jp) throws Throwable;

    boolean deleteCache(CacheDelete cacheDelete, ProceedingJoinPoint jp) throws Throwable;

    Object getCacheData(Cache cache, String key);

    Object saveCacheData(Cache cache, String key, Object data);
}
