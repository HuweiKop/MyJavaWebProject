package com.myproject.service;

import com.myproject.framework.annotation.Service;
import com.myproject.model.User;

/**
 * Created by Wei Hu (J) on 2017/3/3.
 */
@Service("UserServiceImpl")
public class UserServiceImpl implements IUserService {
    @Override
    public User getUserById(Integer id) {
        User user = new User();
        user.setName("xxx");
        user.setAge(23);
        return user;
    }
}
