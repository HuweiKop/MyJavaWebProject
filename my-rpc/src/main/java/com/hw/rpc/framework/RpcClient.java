package com.hw.rpc.framework;

import com.hw.rpc.model.ServiceModel;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by huwei on 2017/3/23.
 */
public class RpcClient {

    private String address;
    private int port;
    private ServiceContainer serviceContainer = new ServiceContainer();

    public RpcClient(String address, int port){
        this.address = address;
        this.port = port;
        this.serviceContainer.init();
    }

    public <T> T call(Class<T> tClass) {
        System.out.println(serviceContainer.getServiceName(tClass));
        return (T) Proxy.newProxyInstance(tClass.getClassLoader(), new Class[]{tClass}, new CallHandler(address, port, serviceContainer.getServiceName(tClass)));
    }

    class CallHandler implements InvocationHandler {
        private String host;
        private int port;
        private String serviceName;

        private SocketChannel client;
        private Selector selector;

        public CallHandler(String host, int port, String serviceName) {
            this.port = port;
            this.host = host;
            this.serviceName = serviceName;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            client = SocketChannel.open();
            selector = Selector.open();
            client.configureBlocking(false);
            client.connect(new InetSocketAddress(this.host,this.port));
            client.register(selector, SelectionKey.OP_CONNECT);
            System.out.println("客户端连接成功......");

            while (true) {
                int count = selector.select();
                if(count<=0){
                    System.out.println("未找到select 连接");
//                    throw new Exception("未找到select 连接");
                }
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    Object result = process(key, method, args);
                    iterator.remove();
                    if(result!=null)
                        return result;
                }
            }
        }

        private Object process(SelectionKey key, Method method, Object[] args) throws IOException, ClassNotFoundException {
            if(key.isValid()&&key.isConnectable()) {
                if (client.finishConnect()) {
                    System.out.println("完成连接。。。。");
                    client.register(selector, SelectionKey.OP_READ);
                    ServiceModel serviceModel = new ServiceModel();
                    serviceModel.setServiceName(this.serviceName);
                    serviceModel.setMethodName(method.getName());
                    serviceModel.setParameterTypes(method.getParameterTypes());
                    serviceModel.setParameters(args);
                    ByteArrayOutputStream bOut = new ByteArrayOutputStream();
                    ObjectOutputStream out = new ObjectOutputStream(bOut);
                    out.writeObject(serviceModel);
                    out.flush();
                    client.write(ByteBuffer.wrap(bOut.toByteArray()));
                }
                else{
                    System.out.println("未连接。。。。");
                }
            }else if(key.isValid()&&key.isReadable()){
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int len = client.read(buffer);
                if (len > 0) {
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(buffer.array());
                    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                    client.close();
                    selector.close();
                    Object result =  objectInputStream.readObject();
                    inputStream.close();
                    objectInputStream.close();
                    return result;
                }
            }
            return null;
        }
    }
}
