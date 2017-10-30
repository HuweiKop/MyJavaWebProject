package com.myproject.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Set;

/**
 * Created by huwei on 2017/6/2.
 */
public class JedisHelper {

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            Jedis j = JedisHelper.getInstance().getJedis();
            System.out.println(i);
            System.out.println(j);
            j.close();
        }
    }

    private static JedisPool pool;

    private static JedisHelper instance;

    private JedisHelper() {
        JedisPoolConfig config = new JedisPoolConfig();
        //最大连接数, 应用自己评估，不要超过AliCloudDB for Redis每个实例最大的连接数
        config.setMaxTotal(5);
        pool = new JedisPool("localhost");
    }

    public static JedisHelper getInstance() {
        return JedisHelperBuilder.jedisHelper;
    }

    static class JedisHelperBuilder {
        static JedisHelper jedisHelper = new JedisHelper();
    }

    public Jedis getJedis() {
        Jedis jedis = pool.getResource();
        return jedis;
    }

    public void returnResource(Jedis jedis){
        if(jedis!=null){
            pool.returnResource(jedis);
        }
    }

    public int del(String key){
        Jedis jedis = pool.getResource();
        jedis.del(key);
        return 0;
    }

    public int set(String key, String value){
        Jedis jedis = pool.getResource();
        jedis.set(key,value);
        jedis.close();
        return 0;
    }

    public int set(String key, String value, long seconds){
        Jedis jedis = pool.getResource();
        if(seconds==0){
            jedis.set(key,value);
        }else {
            jedis.set(key, value, "NX", "EX", seconds);
        }
        jedis.close();
        return 0;
    }

    public String get(String key){
        Jedis jedis = pool.getResource();
        String result = jedis.get(key);
        jedis.close();
        return result;
    }

    public int lpush(String key, String value){
        Jedis jedis = pool.getResource();
        jedis.lpush(key,value);
        jedis.close();
        return 0;
    }

    public String rpop(String key){
        Jedis jedis = pool.getResource();
        String result = jedis.rpop(key);
        jedis.close();
        return result;
    }

    public List<String> getList(String key){
        Jedis jedis = pool.getResource();
        return jedis.lrange(key,0,-1);
    }

    public boolean setList(String key, List<String> value){
        Jedis jedis = pool.getResource();
        jedis.del(key);
        for(String v:value){
            jedis.lpush(key,v);
        }
        return true;
    }

    public Set<String> getKeys(String key){
        Jedis jedis = pool.getResource();
        Set<String> result = jedis.keys(key+"*");
        jedis.close();
        return result;
    }

    public static Long expire(String key, int seconds) {
        if(seconds==0){
            return 0L;
        }
        Jedis jedis = pool.getResource();
        Long expire;
        try {
            expire = jedis.expire(key, seconds);
        } catch (Throwable thr) {
            throw thr;
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }

        return expire;
    }

    public boolean isRunning() {
        Jedis jedis = pool.getResource();
        return "PONG".equals(jedis.ping());
    }
}
