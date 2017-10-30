package com.myproject;

import com.myproject.cache.CacheFactory;
import com.myproject.cache.RedisCache;
import com.myproject.example.TestService;
import com.myproject.example.UserDO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by huwei on 2017/6/14.
 */
public class AppTest extends BaseTest {

    @Autowired
    TestService service1;

    @Test
    public void test() {
        List<UserDO> result = service1.getUser("xxx");
        CacheFactory.registerCache(new RedisCache());
        result = service1.getUser("xxx");
    }
}
