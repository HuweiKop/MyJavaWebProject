package com.myproject;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 16:10 2017/12/19
 * @Modified By
 */
public class Client {

    public static void main(String[] args) {
        try {
            EventLoopGroup worker = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(worker)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
//                            ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes());
//                            socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,buf))
//                                    .addLast(new StringDecoder()).addLast(new ClientHandler());

                            socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder())
                                    .addLast(MarshallingCodeCFactory.buildMarshallingDecoder())
                                    .addLast(new ClientHandler());
                        }
                    });

            ChannelFuture future = bootstrap.connect("127.0.0.1", 9999).sync();

            for(long i=1;i<=10;i++){
                Request request = new Request();
                request.setId(i);
                request.setName("requestName"+i);
                request.setMsg("requestMsg"+i);
                future.channel().writeAndFlush(request);
            }
//            future.channel().writeAndFlush(Unpooled.copiedBuffer("777$_".getBytes()));
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
