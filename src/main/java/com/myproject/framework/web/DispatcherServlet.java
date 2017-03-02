package com.myproject.framework.web;

import com.myproject.framework.web.handler.mapping.ControllerHandlerMapping;
import com.myproject.framework.web.handler.mapping.HandlerMapping;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wei Hu (J) on 2017/3/2.
 */
public class DispatcherServlet {

    private static DispatcherServlet dispatcherServlet = new DispatcherServlet();

    private List<HandlerMapping> handlerMappings;
    private List<ControllerHandlerMapping> controllerHandlerMappings;

    public static DispatcherServlet getDispatcherServlet() {
        return dispatcherServlet;
    }

    private DispatcherServlet() {
        init();
    }

    private void init() {
        initControllerMappings();
    }

    private void initControllerMappings() {
        //动态配置controller扫描包
        this.controllerHandlerMappings = new ArrayList<>();
        String controllerPackage = "com.myproject.controller";
        scanBasePackage(controllerPackage);
    }

    private void scanBasePackage(String basePackage) {
        URL url = this.getClass().getResource("/" + basePackage.replaceAll("\\.", "/"));
        String pathFile = url.getFile();
        File file = new File(pathFile);
        System.out.println(pathFile);
        String[] files = file.list();
        for (String path :
                files) {
            File eachFile = new File(pathFile +"/"+ path);
            if (eachFile.isDirectory()) {
                scanBasePackage(basePackage + "." + eachFile.getName());
            } else {
                System.out.println(basePackage + "." + eachFile.getName());
                controllerHandlerMappings.add(new ControllerHandlerMapping(basePackage + "." + eachFile.getName()));
            }
        }
    }

    public void doDispatch(HttpRequest request, HttpResponse response) {

        try {
            for (ControllerHandlerMapping controller :
                    this.controllerHandlerMappings) {
                if (controller.isMatch(request.getUrl())) {
                    Object result = controller.execute(request.getUrl());
                    response.setResult(result);
                }
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
