package com.myproject.cache;

import com.alibaba.fastjson.JSON;
import com.myproject.annotation.Cache;
import com.myproject.utils.JedisHelper;
import org.springframework.util.StringUtils;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 12:04 2017/10/30
 * @Modified By
 */
public class RedisCache implements ICache {

    @Override
    public Object getCacheData(Cache cache) {
        String key = cache.key();
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
    public Object saveCacheData(Cache cache, Object data) {
        String key = cache.key();
        int expireTime = cache.expireTime();
        String resultStr = JSON.toJSONString(data);
        JedisHelper.getInstance().set(key,resultStr,expireTime);
        return data;
    }
}
