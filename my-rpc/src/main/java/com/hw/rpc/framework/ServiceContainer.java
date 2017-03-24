package com.hw.rpc.framework;

import com.hw.rpc.annotation.RpcService;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huwei on 2017/3/22.
 */
public class ServiceContainer {

    private List<String> classNames = new ArrayList<>();

    private Map<String, Object> serviceHandlerMapping = new HashMap<String, Object>();
    private Map<Class, String> serviceNameMapping = new HashMap<>();

    public void init(){
        try {
            scanPackage("com.hw");
            initServiceHandlerMapping();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initServiceHandlerMapping() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        if(this.classNames.size()<=0)
            return ;

        for(String className:this.classNames){
            Class service = Class.forName(className);
            if(service.isAnnotationPresent(RpcService.class)){
                RpcService rpcService = (RpcService) service.getAnnotation(RpcService.class);
                String serviceName = rpcService.value();
                System.out.println("rpc service=============="+serviceName);
                this.serviceHandlerMapping.put(serviceName,service.newInstance());
                this.serviceNameMapping.put(service.getInterfaces()[0],serviceName);
                System.out.println(service.getInterfaces()[0]+"======");
            }
        }
    }

    private void scanPackage(String basePackage){
        URL url = this.getClass().getResource("/" + basePackage.replaceAll("\\.", "/"));
        String fileStr = url.getFile();
        File file = new File(fileStr);
        String[] files = file.list();
        for(String path:files){
            File eachFile = new File(fileStr+"\\"+path);
            if(eachFile.isDirectory()){
                scanPackage(basePackage+"."+path);
            }
            else{
                classNames.add(basePackage + "." + eachFile.getName().replace(".class", ""));
                System.out.println(basePackage + "." + eachFile.getName().replace(".class", ""));
            }
        }
    }

    public Object getService(String serviceName){
        return this.serviceHandlerMapping.get(serviceName);
    }

    public String getServiceName(Class c){
        return this.serviceNameMapping.get(c);
    }
}
