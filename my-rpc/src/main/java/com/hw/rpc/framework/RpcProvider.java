package com.hw.rpc.framework;

import java.io.IOException;

/**
 * Created by huwei on 2017/3/22.
 */
public class RpcProvider {

    public static void main(String[] args){
        try {
            RpcFramework framework = new RpcFramework("127.0.0.1",8888);
            framework.listener();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
