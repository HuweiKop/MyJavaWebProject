package com.myproject;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 15:35 2017/12/19
 * @Modified By
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{

//        String data = (String)msg;
//        System.out.println("Server: " + data);
//        //写给客户端
//        String response = "我是反馈的信息";
//        ctx.writeAndFlush(Unpooled.copiedBuffer("服务器响应$_".getBytes())).addListener(ChannelFutureListener.CLOSE);

        Request request = (Request)msg;

        System.out.println("服务器接受消息：id="+request.getId()+" name="+request.getName()+" msg="+request.getMsg());

        Response response = new Response();
        response.setId(request.getId());
        response.setName("response:"+request.getName());
        response.setMsg("服务器响应:"+request.getMsg());

        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
