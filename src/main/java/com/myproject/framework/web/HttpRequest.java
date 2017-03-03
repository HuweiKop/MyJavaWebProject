package com.myproject.framework.web;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wei Hu (J) on 2017/3/1.
 */
public class HttpRequest {

    private Map<String, String> headMap = new HashMap<>();
    private String httpMethod;
    private String url;
    private String protocol;
    private Map<String, Object> parameters = new HashMap<>();

    public HttpRequest(String request){
        String[] arrStr = request.split("\n");
        for(int i=1;i<arrStr.length;i++){
            String[] map = arrStr[i].split(": ");
            if(map.length==2){
                headMap.put(map[0],map[1]);
            }
        }
        String first = arrStr[0];
        this.httpMethod = first.substring(0,first.indexOf(" /"));
        this.url = first.substring(first.indexOf(" /")+1,first.lastIndexOf(" "));
        this.protocol = first.substring(first.lastIndexOf(" ")+1);

        String[] paraArr = this.url.split("\\?");
        if(paraArr.length>1){
            this.url = paraArr[0];
            String[] paras = paraArr[1].split("&");
            for(int i=0;i<paras.length;i++){
                String[] p = paras[i].split("=");
                if(p.length==2){
                    this.parameters.put(p[0],p[1]);
                }
            }
        }

        System.out.println(httpMethod);
        System.out.println(url);
        System.out.println(this.protocol);
    }

    public Map<String, String> getHeadMap() {
        return headMap;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getUrl() {
        return url;
    }

    public String getProtocol() {
        return protocol;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }
}
