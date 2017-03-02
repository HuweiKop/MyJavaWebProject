package com.myproject.myserver;

import com.myproject.framework.web.HttpHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Wei Hu (J) on 2017/3/1.
 */
public class MyServer {

    private int port = 80;
    private ServerSocketChannel server;
    private Selector selector;

    ByteBuffer recive = ByteBuffer.allocate(1024);
    ByteBuffer send = ByteBuffer.allocate(1024);

    public MyServer(int port) throws IOException {
        this.port = port;
        server = ServerSocketChannel.open();
        server.socket().bind(new InetSocketAddress(this.port));
        server.configureBlocking(false);
        selector = Selector.open();
        server.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务器已启动。。。。。。。。");
    }

    public void listen() throws IOException {
        while (true) {
            int eventCount = this.selector.select();
            System.out.println("eventCount:"+eventCount);
            if (eventCount <= 0) {
                continue;
            }
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
                client = this.server.accept();
                client.configureBlocking(false);
                client.register(this.selector, SelectionKey.OP_READ);
                System.out.println("接收到客户端请求。。。。。");
            } else if (key.isValid() && key.isReadable()) {
                this.recive.clear();
                client = (SocketChannel) key.channel();
                int len = client.read(recive);
                StringBuilder request = new StringBuilder();
                while (len > 0) {
                    request.append(new String(recive.array(),0,len));
                    recive.clear();
                    len = client.read(recive);
                }
                System.out.println("服务器端接收消息："+request);
                if(request.length()>0){
                    System.out.println("服务端处理请求。。。。。");
                    new Thread(new HttpHandler(request.toString(),key)).start();
                }else{
                    System.out.println("消息为空。。。。。");
                    client.register(selector,SelectionKey.OP_WRITE);
                }
            }else if(key.isValid()&&key.isWritable()){
//                this.send.clear();
                System.out.println("有流写出");
                client = (SocketChannel) key.channel();
                client.close();
                key.cancel();
//                client = (SocketChannel) key.channel();
//                if(client.isOpen()){
//                    System.out.println("socket 未中断连接");
//                    client.shutdownInput();
//                    client.close();
//                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        }
    }

    public static void main(String[] args) {
        try {
            MyServer server = new MyServer(8088);
            server.listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
