package com.spring.cloud;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 16:30 2018/1/5
 * @Modified By
 */
@Component
public class SchedualServiceHiHystric implements SchedualServiceHi {
    @Override
    public String sayHiFromClientOne(@RequestParam(value = "name") String name) {
        return "error "+name;
    }
}
