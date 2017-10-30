package com.myproject;

import com.lmax.disruptor.EventFactory;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 19:15 2017/10/23
 * @Modified By
 */
public class MyEventFactory implements EventFactory<MyEvent> {
    @Override
    public MyEvent newInstance() {
        return new MyEvent();
    }
}
