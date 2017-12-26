package com.myproject.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 18:01 2017/11/7
 * @Modified By
 */
@Component
public class MyPublisher {
    @Autowired
    private ApplicationContext context;

    public void publish(String msg){
        context.publishEvent(new MyEvent(this,msg));
    }
}
