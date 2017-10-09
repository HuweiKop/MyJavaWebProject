package com.hw.springboot.service;

import com.hw.springboot.model.User;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by huwei on 2017/7/19.
 */
public abstract class BaseService {

    @Transactional
    public void test(String username){
        insertUser(username);
    }

//    @Transactional
    public abstract User insertUser(String username);
}
