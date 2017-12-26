package com.myproject.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 17:59 2017/11/7
 * @Modified By
 */
@Component
public class MyListener implements ApplicationListener<MyEvent> {
    @Override
    public void onApplicationEvent(MyEvent myEvent) {
        System.out.println("listent :"+ myEvent.getMsg());
    }
}
