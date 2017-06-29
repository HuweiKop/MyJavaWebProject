package com.hw.mutil.datasource;

import com.hw.mutil.datasource.service.Service1;
import com.hw.mutil.datasource.service.Service2;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by huwei on 2017/6/28.
 */
@RunWith(SpringJUnit4ClassRunner.class) // SpringJUnit支持，由此引入Spring-Test框架支持！
@ContextConfiguration({"classpath:applicationContext.xml"})
public class Main {

    @Autowired
    Service1 service1;
    @Autowired
    Service2 service2;

    public static  void main(String[] args){

    }
}
