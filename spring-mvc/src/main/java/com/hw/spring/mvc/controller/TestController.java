package com.hw.spring.mvc.controller;

import com.hw.spring.mvc.annotation.Controller;
import com.hw.spring.mvc.annotation.Qualifier;
import com.hw.spring.mvc.annotation.RequestMapping;
import com.hw.spring.mvc.service.ITestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Wei Hu (J) on 2017/3/10.
 */
@Controller("testController")
@RequestMapping("/test")
public class TestController {

    @Qualifier("testService")
    private ITestService testService;

    @RequestMapping("/insert")
    public String insert (HttpServletRequest request, HttpServletResponse response, String param){
        return testService.insert(param);
    }
}
