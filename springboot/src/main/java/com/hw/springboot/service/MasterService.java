package com.hw.springboot.service;

import com.hw.springboot.TargetDataSource;
import com.hw.springboot.dao.IUserDao;
import com.hw.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by huwei on 2017/7/3.
 */
@Service("masterService")
public class MasterService {

    @Autowired
    private IUserDao userDao;

    @TargetDataSource
    @Transactional
    public User insertUser(String username){
        User user = new User();
        user.setUsername(username);
        userDao.insertUser(user);

        User newUser = userDao.getUser(user.getId());
        System.out.println(newUser.getId());
        System.out.println(newUser.getUsername());
        return newUser;
    }
}
