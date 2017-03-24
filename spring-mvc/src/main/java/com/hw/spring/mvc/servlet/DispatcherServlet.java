package com.hw.spring.mvc.servlet;

import com.hw.spring.mvc.annotation.Controller;
import com.hw.spring.mvc.annotation.Qualifier;
import com.hw.spring.mvc.annotation.RequestMapping;
import com.hw.spring.mvc.annotation.Service;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * Created by Wei Hu (J) on 2017/3/10.
 */
@WebServlet("/DispatcherServlet")
public class DispatcherServlet extends HttpServlet {

    private List<String> classNames = new ArrayList<>();

    private Map<String, Object> instanceMaps = new HashMap<>();

    private Map<String, HandlerMapping> handlerMaps = new HashMap<>();

    public void init(ServletConfig config) {
        try {
            scanPackage("com.hw");
            filterAndInstance();
            springIoc();
            handlerMaps();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {        System.out.println();
        String uri = req.getRequestURI();
        System.out.println("uri======================="+uri);
        String projectName = req.getContextPath();
        String path = uri.replace(projectName,"");
        System.out.println("path======================="+path);
        HandlerMapping mapping = handlerMaps.get(path);
        PrintWriter outPrintWriter = resp.getWriter();
        if(mapping==null){
            outPrintWriter.write("资源路径找不到，请检查访问地址");
            return;
        }

        try {
            mapping.method.setAccessible(true);
            mapping.method.invoke(mapping.controller,new Object[]{req,resp,null});
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void scanPackage(String basePackage) {
        URL url = this.getClass().getClassLoader().getResource("/" + basePackage.replaceAll("\\.", "/"));
        String fileStr = url.getFile();
        File file = new File(fileStr);
        String[] files = file.list();
        for (String path : files) {
            File filePath = new File(fileStr +"\\" + path);
            if (filePath.isDirectory()) {
                scanPackage(basePackage + "." + path);
            } else {
                classNames.add(basePackage + "." + filePath.getName());
                System.out.println("classNmae add:"+basePackage + "." + filePath.getName());
            }
        }
    }

    private void filterAndInstance() throws Exception {
        if (classNames.size() <= 0) {
            return;
        }
        for (String className : classNames) {
            Class cNameClass = Class.forName(className.replace(".class", ""));
            if (cNameClass.isAnnotationPresent(Controller.class)) {
                Object instance = cNameClass.newInstance();
                Controller controllerAnnotation = (Controller) cNameClass.getAnnotation(Controller.class);
                String controllerName = controllerAnnotation.value();
                instanceMaps.put(controllerName, instance);
            } else if (cNameClass.isAnnotationPresent(Service.class)) {
                Object instance = cNameClass.newInstance();
                Service controllerAnnotation = (Service) cNameClass.getAnnotation(Service.class);
                String serviceName = controllerAnnotation.value();
                instanceMaps.put(serviceName, instance);
            }
        }
    }

    private void springIoc() throws IllegalAccessException {
        if(instanceMaps.size()<=0)
            return;

        for(Map.Entry<String, Object> entry:instanceMaps.entrySet()){
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            for(Field field:fields){
                Qualifier qualifier = field.getAnnotation(Qualifier.class);
                if(qualifier!=null){
                    field.setAccessible(true);
                    field.set(entry.getValue(),instanceMaps.get(qualifier.value()));
                }
            }
        }
    }

    private void handlerMaps() throws Exception {
        if(instanceMaps.size()<=0)
            return;

        for(Map.Entry<String, Object> entry:instanceMaps.entrySet()){
            if(entry.getValue().getClass().isAnnotationPresent(Controller.class)){
                String baseUrl = "";
                RequestMapping requestMapping = entry.getValue().getClass().getAnnotation(RequestMapping.class);
                if(requestMapping!=null){
                    baseUrl+=requestMapping.value();
                }
                Method[] methods = entry.getValue().getClass().getMethods();
                for(Method method : methods){
                    if(method.isAnnotationPresent(RequestMapping.class)) {
                        RequestMapping methodRequestMapping = method.getAnnotation(RequestMapping.class);
                        String url = baseUrl+methodRequestMapping.value();
                        HandlerMapping mapping = new HandlerMapping();
                        mapping.controller = entry.getValue();
                        mapping.url = url;
                        mapping.method = method;
                        handlerMaps.put(url, mapping);
                    }
                }
            }
        }
    }

    private class HandlerMapping{
        String url;
        Object controller;
        Method method;
    }
}
