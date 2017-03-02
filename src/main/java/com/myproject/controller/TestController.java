package com.myproject.controller;

import com.myproject.framework.annotation.Controller;
import com.myproject.framework.annotation.RequestMapping;

/**
 * Created by Wei Hu (J) on 2017/3/2.
 */
@Controller
@RequestMapping("/web/test")
public class TestController {

    @RequestMapping("/test/connect")
    public String testConnect(){
        return "测试 连接";
    }
}
