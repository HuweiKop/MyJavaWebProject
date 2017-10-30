package com.myproject;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        EventFactory<MyEvent> factory = new MyEventFactory();
        ExecutorService service = Executors.newCachedThreadPool();
        int ringBufferSize = 1024*1024;

        Disruptor<MyEvent> disruptor = new Disruptor<MyEvent>(factory,ringBufferSize,service,
                ProducerType.SINGLE,new YieldingWaitStrategy());

        EventHandler<MyEvent> handler = new MyEventHandler();
        disruptor.handleEventsWith(handler);
        EventHandler<MyEvent> handler2 = new MyEventHandler2();
        disruptor.handleEventsWith(handler2);
        disruptor.start();

        RingBuffer<MyEvent> ringBuffer = disruptor.getRingBuffer();
        long sequence = ringBuffer.next();

        try {
            MyEvent myEvent = ringBuffer.get(sequence);
            myEvent.setMsg("xxxxx");
        } finally {
            ringBuffer.publish(sequence);
        }
        sequence = ringBuffer.next();

        try {
            MyEvent myEvent = ringBuffer.get(sequence);
            myEvent.setMsg("dddd");
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}
