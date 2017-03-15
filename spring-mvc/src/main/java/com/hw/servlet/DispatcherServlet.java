package com.hw.servlet;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wei Hu (J) on 2017/3/10.
 */
public class DispatcherServlet {

    List<String> className = new ArrayList<>();

    public void init(ServletConfig config){
        scanPackage("com.hw");
    }

    private void scanPackage(String basePackage){
        URL url = this.getClass().getClassLoader().getResource("/"+basePackage.replaceAll("\\.","/"));
        String fileStr = url.getFile();
        File file = new File(fileStr);
        String[] files = file.list();
        for(String path:files){
            File filePath = new File(fileStr+path);
            if(filePath.isDirectory()){
                scanPackage(basePackage+"."+path);
            }else{
                className.add(basePackage+"."+filePath.getName());
            }
        }
    }
}
