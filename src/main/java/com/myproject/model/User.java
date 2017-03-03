package com.myproject.model;

import com.myproject.framework.annotation.Param;

/**
 * Created by Wei Hu (J) on 2017/3/3.
 */
public class User {
    @Param("name")
    private String name;
    @Param("age")
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
