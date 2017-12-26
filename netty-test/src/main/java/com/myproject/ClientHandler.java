package com.myproject;

import com.sun.deploy.nativesandbox.comm.Request;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 15:35 2017/12/19
 * @Modified By
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        try {
//            ByteBuf buf = (ByteBuf) msg;
//            byte[] data = new byte[buf.readableBytes()];
//            buf.readBytes(data);
//            System.out.println("Client：" + msg);

            Response response = (Response) msg;
            System.out.println("客户端接收到服务器返回消息：id="+response.getId()+" name="+response.getName()+" msg="+response.getMsg());
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
