package com.myproject;


import com.lmax.disruptor.EventHandler;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 13:35 2017/10/25
 * @Modified By
 */
public class MyEventHandler2 implements EventHandler<MyEvent> {
    @Override
    public void onEvent(MyEvent myEvent, long l, boolean b) throws Exception {
        System.out.println("my event2:"+myEvent.getMsg());
        System.out.println("sequence2:"+l);
        System.out.println("endOfBatch2:"+b);
    }
}
