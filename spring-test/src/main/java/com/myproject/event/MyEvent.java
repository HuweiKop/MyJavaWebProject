package com.myproject.event;

import jdk.nashorn.internal.objects.annotations.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 17:55 2017/11/7
 * @Modified By
 */
public class MyEvent extends ApplicationEvent {

    private String msg;

    public MyEvent(Object source, String msg) {
        super(source);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
