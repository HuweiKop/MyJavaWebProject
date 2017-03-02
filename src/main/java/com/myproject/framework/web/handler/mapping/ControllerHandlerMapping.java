package com.myproject.framework.web.handler.mapping;

import com.myproject.framework.annotation.Controller;
import com.myproject.framework.annotation.RequestMapping;
import com.myproject.framework.web.DispatcherServlet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wei Hu (J) on 2017/3/2.
 */
public class ControllerHandlerMapping extends HandlerMapping {
    private String url;
    private Object controller;
    private List<MethodHandlerMapping> methodHandlerList;

    public ControllerHandlerMapping(String className) {
        try {
            Class c = Class.forName(className.substring(0,className.lastIndexOf(".class")));
            Controller ctl = (Controller) c.getAnnotation(Controller.class);
            if (ctl == null)
                System.out.println("该类不是 controller");
            RequestMapping rm = (RequestMapping) c.getAnnotation(RequestMapping.class);
            if (rm != null) {
                this.url = rm.value();
            } else {
                System.out.println("request mapping 不能为空");
            }
            this.controller = c.newInstance();
            initMethod();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void initMethod() {
        this.methodHandlerList = new ArrayList<>();
        Method[] methods = this.controller.getClass().getMethods();
        for (Method method :
                methods) {
            RequestMapping rm = method.getAnnotation(RequestMapping.class);
            if(rm==null)
                continue;
            MethodHandlerMapping handler = new MethodHandlerMapping();
            handler.method = method;
            handler.url = rm.value();
            this.methodHandlerList.add(handler);
        }
    }

    public Object execute(String url) throws InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        String methodUrl = url.replaceFirst(this.url, "");
        System.out.println("method url:" + methodUrl);
        for (MethodHandlerMapping handler :
                this.methodHandlerList) {
            if(handler.url.equals(methodUrl)){
                Object result = handler.method.invoke(this.controller);
                System.out.println("处理结果为："+result);
                return result;
            }
        }
        System.out.println("未找到该方法。。。。");
        throw new ClassNotFoundException("未找到该方法。。。。");
    }

    public boolean isMatch(String url) {
        return url.startsWith(this.url);
    }

    public class MethodHandlerMapping {
        private String url;
        private Method method;
    }
}
