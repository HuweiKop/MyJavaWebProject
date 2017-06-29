package com.hw.mutil.datasource.service;

import com.hw.mutil.datasource.DataSource;
import com.hw.mutil.datasource.dao.IUserDao;
import com.hw.mutil.datasource.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by huwei on 2017/6/28.
 */
@Service("service2")
public class Service2 {

    @Autowired
    private IUserDao userDao;

    @Transactional
    @DataSource("testDataSource2")
    public User getUser(int id){
        User user = userDao.getUser(id);
        System.out.println("service2======="+user.getUsername());
        return user;
    }
}
