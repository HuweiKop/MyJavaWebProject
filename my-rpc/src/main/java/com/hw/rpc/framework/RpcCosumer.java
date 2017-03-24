package com.hw.rpc.framework;

import com.hw.rpc.service.ITestService;

import java.io.IOException;

/**
 * Created by huwei on 2017/3/23.
 */
public class RpcCosumer {
    public static void main(String[] args){
        RpcClient framework = new RpcClient("127.0.0.1",8888);
        ITestService service = framework.call(ITestService.class);
        String result = service.test("xxxx");
        System.out.println(result);
    }
}
