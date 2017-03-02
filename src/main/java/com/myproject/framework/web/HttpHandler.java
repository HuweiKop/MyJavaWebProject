package com.myproject.framework.web;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created by Wei Hu (J) on 2017/3/1.
 */
public class HttpHandler implements Runnable {

    private HttpRequest request;
    private HttpResponse response;
    private SelectionKey key;

    public HttpHandler(String request, SelectionKey key) {
        this.request = new HttpRequest(request);
        this.response = new HttpResponse();
        this.key = key;
    }

    @Override
    public void run() {
        System.out.println("服务端开始处理请求。。。。。");

        DispatcherServlet.getDispatcherServlet().doDispatch(request, response);

        SocketChannel channel = (SocketChannel) this.key.channel();
        Selector selector = key.selector();
        try {
            System.out.println("http handler 结果:" + response.getResult());
            channel.register(selector,SelectionKey.OP_WRITE);
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            if (this.response.getResult() != null) {
                buffer.put(this.response.getResult().toString().getBytes());
            }else{
                buffer.put("default response".getBytes());
            }
            buffer.flip();
            channel.write(buffer);
            key.cancel();
            channel.shutdownInput();
            channel.close();
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
