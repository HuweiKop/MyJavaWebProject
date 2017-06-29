package com.hw.springboot.service;

import com.hw.springboot.TargetDataSource;
import com.hw.springboot.dao.IUserDao;
import com.hw.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by huwei on 2017/6/28.
 */
@Service("service1")
@TargetDataSource
public class Service1 {

    @Autowired
    private IUserDao userDao;

    @Transactional
    public User getUser(int id){
        User user = userDao.getUser(id);
        System.out.println("service1======="+user.getUsername());
        return user;
    }
}
