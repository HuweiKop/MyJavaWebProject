package com.hw.springboot.service;

import com.hw.springboot.TargetDataSource;
import com.hw.springboot.dao.IUserDao;
import com.hw.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by huwei on 2017/6/28.
 */
@Service("service1")
@TargetDataSource
public class Service1 {

    @Autowired
    private IUserDao userDao;
    @Autowired
    private Service2 service2;
    @Autowired
    private Service3 service3;

    @Transactional
    public User getUser(int id){
        User user = userDao.getUser(id);
        System.out.println("service1======="+user.getUsername());
        service2.getUser(id);
        service3.getUser(id);
        return user;
    }
}
