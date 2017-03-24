package com.hw.rpc.framework;

import com.hw.rpc.model.ServiceModel;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by huwei on 2017/3/22.
 */
public class RpcFramework {

    private final ServiceContainer serviceContainer;

    private String address;
    private int port;

    private ServerSocketChannel server;
    private Selector selector;

    private ByteBuffer rcBuffer = ByteBuffer.allocate(1024);

    private ExecutorService executorService = Executors.newCachedThreadPool();

    public RpcFramework(String address, int port) throws IOException {
        serviceContainer = new ServiceContainer();
        serviceContainer.init();

        this.address = address;
        this.port = port;
        this.server = ServerSocketChannel.open();
        this.server.socket().bind(new InetSocketAddress(address, port));
        this.server.configureBlocking(false);
        this.selector = Selector.open();
        this.server.register(this.selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务器已经启动 地址：" + address + " 端口号：" + port);
    }

    public void listener() throws IOException {
        while (true) {
            int count = this.selector.select();
            if (count <= 0)
                continue;

            Set<SelectionKey> selectionKeys = this.selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                process(key);
                iterator.remove();
            }
        }
    }

    private void process(SelectionKey key) {
        SocketChannel client = null;

        try {
            if (key.isValid() && key.isAcceptable()) {
                System.out.println("收到客户端连接。。。。");
                client = this.server.accept();
                client.configureBlocking(false);
                client.register(this.selector, SelectionKey.OP_READ);
            } else if (key.isValid() && key.isReadable()) {
                System.out.println("收到客户端 信息 开始读取。。。。");
                rcBuffer.clear();
                client = (SocketChannel) key.channel();
                int len = client.read(rcBuffer);
                if (len > 0) {
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(rcBuffer.array());
                    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                    ServiceModel serviceModel = (ServiceModel) objectInputStream.readObject();
                    inputStream.close();
                    objectInputStream.close();
//                    String msg = new String(rcBuffer.array(), 0, len);
//                    System.out.println("服务器端收到消息：" + msg);
//                    client.register(selector, SelectionKey.OP_WRITE);
                    this.executorService.execute(new ProcessHandler(key,serviceModel));
                }
            } else if (key.isValid() && key.isWritable()) {
                System.out.println("write......");
                client = (SocketChannel) key.channel();
                client.close();
                key.channel();
//                ServiceModel model = new ServiceModel();
//                sendBuffer.clear();
//                client = (SocketChannel) key.channel();
//                ByteArrayOutputStream bOut = new ByteArrayOutputStream();
//                ObjectOutputStream out = new ObjectOutputStream(bOut);
//                out.writeObject(model);
//                out.flush();
//                client.write(ByteBuffer.wrap(bOut.toByteArray()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void publish(String serviceName) {
    }

    class ProcessHandler implements Runnable{

        private SelectionKey key;
        private ServiceModel serviceModel;

        public ProcessHandler(SelectionKey key, ServiceModel serviceModel){
            this.key = key;
            this.serviceModel = serviceModel;
        }

        @Override
        public void run() {
            Object service = serviceContainer.getService(this.serviceModel.getServiceName());
            try {
                Method method = service.getClass().getMethod(this.serviceModel.getMethodName(),this.serviceModel.getParameterTypes());
                method.setAccessible(true);
                Object result = method.invoke(service,this.serviceModel.getParameters());

                SocketChannel client = (SocketChannel) key.channel();
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(byteOut);
                out.writeObject(result);
                out.flush();
                client.write(ByteBuffer.wrap(byteOut.toByteArray()));
                byteOut.close();
                out.close();
                client.close();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
