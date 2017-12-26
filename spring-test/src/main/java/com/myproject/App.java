package com.myproject;

import com.myproject.event.EventConfig;
import com.myproject.event.MyPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(EventConfig.class);

        MyPublisher publisher = context.getBean(MyPublisher.class);
        publisher.publish("xxxx");
        context.close();
    }
}
