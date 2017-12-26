package com.myproject;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 15:16 2017/12/19
 * @Modified By
 */
public class Server {

    private int port;

    public Server(int port){
        this.port = port;
    }

    public void run(){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
//                            ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes());
//                            socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,buf))
//                                    .addLast(new StringDecoder())
//                                    .addLast(new ServerHandler());

                            socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder())
                                    .addLast(MarshallingCodeCFactory.buildMarshallingEncoder())
                                    .addLast(new ServerHandler());
                        }
                    })
                    /**
                     * 对于ChannelOption.SO_BACKLOG的解释：
                     * 服务器端TCP内核维护有两个队列，我们称之为A、B队列。客户端向服务器端connect时，会发送带有SYN标志的包（第一次握手），服务器端
                     * 接收到客户端发送的SYN时，向客户端发送SYN ACK确认（第二次握手），此时TCP内核模块把客户端连接加入到A队列中，然后服务器接收到
                     * 客户端发送的ACK时（第三次握手），TCP内核模块把客户端连接从A队列移动到B队列，连接完成，应用程序的accept会返回。也就是说accept
                     * 从B队列中取出完成了三次握手的连接。
                     * A队列和B队列的长度之和就是backlog。当A、B队列的长度之和大于ChannelOption.SO_BACKLOG时，新的连接将会被TCP内核拒绝。
                     * 所以，如果backlog过小，可能会出现accept速度跟不上，A、B队列满了，导致新的客户端无法连接。要注意的是，backlog对程序支持的
                     * 连接数并无影响，backlog影响的只是还没有被accept取出的连接
                     */
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.SO_SNDBUF, 32*1024)
                    .option(ChannelOption.SO_RCVBUF, 32*1024)
                    .childOption(ChannelOption.SO_KEEPALIVE,true);

            ChannelFuture future = bootstrap.bind(this.port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
