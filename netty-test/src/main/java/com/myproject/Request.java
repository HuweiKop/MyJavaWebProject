package com.myproject;

import java.io.Serializable;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 17:42 2017/12/19
 * @Modified By
 */
public class Request implements Serializable {

    private Long id;

    private String name;

    private String msg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
