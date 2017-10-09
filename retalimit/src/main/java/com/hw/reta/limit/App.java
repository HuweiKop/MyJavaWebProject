package com.hw.reta.limit;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Jedis jedis = new Jedis("127.0.0.1",6379);
//        jedis.connect();
        String script = "local ip = KEYS[1] --获取ip\n" +
                "local expire = ARGV[1] --获取过期时间\n" +
                "local limit = ARGV[2] --获取限制次数\n" +
                "\n" +
                "print(ip)\n" +
                "print(expire)\n" +
                "print(limit)\n" +
                "\n" +
                "local times = redis.call('incr',ip)\n" +
                "\n" +
                "if times == 1 then\n" +
                "\tredis.call('expire',ip,expire)\n" +
                "end\n" +
                "\n" +
                "if times > tonumber(limit) then\n" +
                "\treturn 0\n" +
                "end\n" +
                "\n" +
                "return 1\n";
        List<String> keys = new ArrayList<>(1);
        keys.add("127.0.0.1");
        List<String> argvs = new ArrayList<>(2);
        argvs.add("10");
        argvs.add("5");
        for(int i=0;i<10;i++) {
            Object result = jedis.eval(script, keys, argvs);
            System.out.println(result);
        }
        jedis.close();
    }
}
