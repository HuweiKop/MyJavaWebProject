package com.hw.springboot.api;

import com.hw.springboot.dao.IUserDao;
import com.hw.springboot.model.User;
import com.hw.springboot.service.Service1;
import com.hw.springboot.service.Service2;
import com.hw.springboot.service.Service3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by huwei on 2017/6/29.
 */
@Component
public class UserApi {


    @Autowired
    Service1 service1;
    @Autowired
    Service2 service2;
    @Autowired
    Service3 service3;
    @Autowired
    IUserDao userDao;

    public void getUser(int id){
        service1.getUser(id);
        service2.getUser(11);
        service3.getUser(11);
        User user = userDao.getUser(11);
        System.out.println(user.getUsername());
    }
}
