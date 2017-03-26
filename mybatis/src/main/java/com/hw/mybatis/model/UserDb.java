package com.hw.mybatis.model;

import com.hw.mybatis.annotation.FieldName;
import com.hw.mybatis.annotation.IdName;
import com.hw.mybatis.annotation.TableName;

/**
 * Created by Wei Hu (J) on 2017/1/12.
 */
@TableName("t_user")
@IdName()
public class UserDb {
    @FieldName("id")
    private int id;
    private String name;
    @FieldName("age")
    private Integer age1;
    @FieldName("isActive")
    private Boolean active;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge1() {
        return age1;
    }

    public void setAge1(Integer age1) {
        this.age1 = age1;
    }

    public Boolean getIsActive() {
        return active;
    }

    public void setIsActive(Boolean active) {
        this.active = active;
    }
}
