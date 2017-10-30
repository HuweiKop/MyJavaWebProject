package com.myproject.cache;

import com.myproject.annotation.Cache;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 12:02 2017/10/30
 * @Modified By
 */
public interface ICache {

    public Object getCacheData(Cache cache);

    Object saveCacheData(Cache cache, Object data);
}
