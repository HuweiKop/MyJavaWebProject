package com.hw.springboot.service;

import com.hw.springboot.DataSourceTypeConst;
import com.hw.springboot.DynamicDataSourceContextHolder;
import com.hw.springboot.TargetDataSource;
import com.hw.springboot.dao.IUserDao;
import com.hw.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.activation.DataSource;

/**
 * Created by huwei on 2017/6/29.
 */
@Service("service3")
//@TargetDataSource(value = "testDataSource2",type = DataSourceTypeConst.SlaverDataSource)
public class Service3 {

    @Autowired
    private IUserDao userDao;
    @Autowired
    private Service2 service2;

//    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public User getUser(int id){
        User user = userDao.getUser(id);
        System.out.println("service3======="+user.getUsername());
        return user;
    }
}
