package com.hw.service.impl;

import com.hw.service.ITestService;

/**
 * Created by Wei Hu (J) on 2017/3/10.
 */
public class TestServiceImpl implements ITestService {
    @Override
    public String query(int id) {
        return "query:"+id;
    }

    @Override
    public String insert(String param) {
        return "insert:"+param;
    }

    @Override
    public String update(String param) {
        return "update:"+param;
    }
}
