package com.myproject.cache;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 12:13 2017/10/30
 * @Modified By
 */
public class CacheFactory {

    private volatile static ICache cache;

    public static ICache getCache() {
        if (cache == null) {
            synchronized (CacheFactory.class) {
                if (cache == null) {
                    cache = new RedisCache();
                }
            }
        }
        return cache;
    }

    public static void registerCache(ICache c) {
        cache = c;
    }
}
