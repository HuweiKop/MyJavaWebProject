package com.hw.springboot.service;

import com.hw.springboot.DataSourceTypeConst;
import com.hw.springboot.TargetDataSource;
import com.hw.springboot.dao.IUserDao;
import com.hw.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by huwei on 2017/6/28.
 */
@Service("service2")
@TargetDataSource(value = "testDataSource1",type = DataSourceTypeConst.SlaverDataSource)
public class Service2 {

    @Autowired
    private IUserDao userDao;

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public User getUser(int id){
        User user = userDao.getUser(id);
        System.out.println("service2======="+user.getUsername());
        return user;
    }
}
