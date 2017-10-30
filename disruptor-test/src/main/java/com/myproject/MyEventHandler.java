package com.myproject;


import com.lmax.disruptor.EventHandler;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 19:16 2017/10/23
 * @Modified By
 */
public class MyEventHandler implements EventHandler<MyEvent> {
    @Override
    public void onEvent(MyEvent myEvent, long l, boolean b) throws Exception {
        System.out.println("my event:"+myEvent.getMsg());
        System.out.println("sequence:"+l);
        System.out.println("endOfBatch:"+b);
    }
}
