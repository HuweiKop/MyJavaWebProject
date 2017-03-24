package com.hw.rpc.model;

import java.io.Serializable;

/**
 * Created by huwei on 2017/3/22.
 */
public class ServiceModel implements Serializable {

    private static final long serialVersionUID = -8239387209021655728L;
    private String serviceName;
    private String methodName;
    private Object[] parameters;
    private Class<?>[] parameterTypes;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }
}
