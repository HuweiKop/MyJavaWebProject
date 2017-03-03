package com.myproject.framework.web.handler.mapping;

import com.myproject.framework.annotation.Controller;
import com.myproject.framework.annotation.Param;
import com.myproject.framework.annotation.RequestMapping;
import com.myproject.framework.web.DispatcherServlet;
import com.myproject.framework.web.HttpRequest;
import com.myproject.util.StringHelper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Wei Hu (J) on 2017/3/2.
 */
public class ControllerHandlerMapping extends HandlerMapping {
    private String url;
    private Object controller;
    private List<MethodHandlerMapping> methodHandlerList;

    public ControllerHandlerMapping(String className) {
        try {
            Class c = Class.forName(className.substring(0, className.lastIndexOf(".class")));
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
        Method[] methods = this.controller.getClass().getDeclaredMethods();
        for (Method method :
                methods) {
            RequestMapping rm = method.getAnnotation(RequestMapping.class);
            if (rm == null)
                continue;
            MethodHandlerMapping handler = new MethodHandlerMapping();
            handler.method = method;
            handler.url = rm.value();
            handler.parameters = method.getParameters();
            this.methodHandlerList.add(handler);
        }
    }

    public Object execute(HttpRequest request) throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, InstantiationException, NoSuchMethodException {
        String methodUrl = request.getUrl().replaceFirst(this.url, "");
        System.out.println("method url:" + methodUrl);
        for (MethodHandlerMapping handler :
                this.methodHandlerList) {
            if (handler.url.equals(methodUrl)) {
                Parameter[] parameters = handler.parameters;
                Object[] args = new Object[parameters.length];
                for (int i = 0; i < parameters.length; i++) {
                    Param param = parameters[i].getAnnotation(Param.class);
                    if (param != null) {
                        System.out.println("para===============");
                        Object p = request.getParameters().get(param.value());
                        if (parameters[i].getType() == Integer.class) {
                            args[i] = Integer.valueOf(p.toString());
                        } else if (parameters[i].getType() == String.class) {
                            args[i] = p;
                        }
                    } else if (parameters[i].getType() == HttpRequest.class) {
                        args[i] = request;
                    } else {
                        System.out.println("else=======================");
                        Object p = parameters[i].getType().newInstance();
                        Method[] methods = p.getClass().getMethods();
                        for(Map.Entry m:request.getParameters().entrySet()){
                            String setMethodName = "set"+ StringHelper.firstToUpper((String) m.getKey());
                            for(int j=0;j<methods.length;j++){
                                if(methods[j].getName().equals(setMethodName)){
                                    Class[] pts = methods[j].getParameterTypes();
                                    if(pts.length==1){
                                        if(pts[0]==Integer.class){
                                            methods[j].invoke(p,Integer.valueOf(m.getValue().toString()));
                                        }else {
                                            methods[j].invoke(p,m.getValue());
                                        }
                                    }
                                }
                            }
                        }
//                        Field[] fields = p.getClass().getFields();
//                        for (int j = 0; j < fields.length; j++) {
//                            if (fields[j].getType() == Integer.class) {
//                                fields[j].set(p, Integer.valueOf((String) request.getParameters().get(fields[j].getName())));
//                            } else {
//                                fields[j].set(p, request.getParameters().get(fields[j].getName()));
//                            }
//                        }
                        args[i] = p;
                    }
                }
                Object result = handler.method.invoke(this.controller, args);
                System.out.println("处理结果为：" + result);
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
        private Parameter[] parameters;
    }
}
