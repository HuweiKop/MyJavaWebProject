package com.myproject.controller;

import com.myproject.framework.annotation.Controller;
import com.myproject.framework.annotation.Param;
import com.myproject.framework.annotation.RequestMapping;
import com.myproject.framework.web.HttpRequest;
import com.myproject.model.User;

/**
 * Created by Wei Hu (J) on 2017/3/2.
 */
@Controller
@RequestMapping("/web/test")
public class TestController {

    @RequestMapping("/test/connect")
    public String testConnect(@Param("name") String name, @Param("age")Integer age){
        return "测试 连接 name:"+name+"   age:"+age;
    }

    @RequestMapping("/test/connect2")
    public String testConnect2(User user){
        return "测试 连接 name:"+user.getName()+"   age:"+user.getAge();
    }

    @RequestMapping("/test/connect3")
    public String testConnect3(String user){
        return "测试 连接 name:"+user+"   age:";
    }
}
