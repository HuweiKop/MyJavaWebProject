package com.hw.rpc.service;

import com.hw.rpc.annotation.RpcService;

/**
 * Created by huwei on 2017/3/22.
 */
@RpcService("testService")
public class TestServiceImpl implements ITestService {
    @Override
    public String test(String name) {
        System.out.println("service test: ======"+name);
        return "return client test: ======"+name;
    }
}
