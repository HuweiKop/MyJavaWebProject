package com.myproject.redlock;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 11:16 2018/1/10
 * @Modified By
 */
public class RedissonConnector {

    private static RedissonClient redisson = Redisson.create();

    public static RedissonClient getClient(){
        return redisson;
    }
}
