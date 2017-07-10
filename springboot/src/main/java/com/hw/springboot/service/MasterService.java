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

//    @Transactional
    public User getUser(int id){
        User user = userDao.getUser(id);
        System.out.println("service master======="+user.getUsername());
        User user2 = userDao.getUser(id);
        System.out.println("service master======="+user2.getUsername());
        return user;
    }
}
