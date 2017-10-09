package com.hw;


import com.hw.mybatis.Application;
import com.hw.mybatis.test.dao.IUserDao;
import com.hw.mybatis.test.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by huwei on 2017/6/28.
 */
@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@SpringApplicationConfiguration(classes = Application.class) // 指定我们SpringBoot工程的Application启动类
public class AppTest {

    @Autowired
    IUserDao userDao;

    @Test
    public  void test(){

//        DataSourceContextHolder.setDataSourceType("testDataSource1");
//        service1.getUser(11);
//        DataSourceContextHolder.setDataSourceType("testDataSource2");
//        service2.getUser(11);
        User user = new User();
//        user.setId(909);
        user.setUsername("sss");
        System.out.println(userDao.updateUser(user));
    }
}
