package com.hw.spring.mvc.service.impl;

import com.hw.spring.mvc.annotation.Service;
import com.hw.spring.mvc.service.ITestService;

/**
 * Created by Wei Hu (J) on 2017/3/10.
 */
@Service("testService")
public class TestServiceImpl implements ITestService {
    @Override
    public String query(int id) {
        System.out.printf("test query:"+id);
        return "query:"+id;
    }

    @Override
    public String insert(String param) {
        System.out.printf("test insert :"+param);
        return "insert:"+param;
    }

    @Override
    public String update(String param) {
        System.out.printf("test update:"+param);
        return "update:"+param;
    }
}
