package com.myproject.cache;

import com.alibaba.fastjson.JSON;
import com.myproject.annotation.Cache;
import com.myproject.annotation.CacheDelete;
import com.myproject.utils.JedisHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.util.StringUtils;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 12:04 2017/10/30
 * @Modified By
 */
public class RedisCache extends AbstractCache {

    @Override
    public boolean deleteCache(CacheDelete cacheDelete, ProceedingJoinPoint jp) throws Throwable {
        String key = cacheDelete.key();
        if(cacheDelete.isCacheKey()){
            key = getCacheKey(cacheDelete.key(), jp);
        }
        JedisHelper.getInstance().del(key);
        return true;
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
}
