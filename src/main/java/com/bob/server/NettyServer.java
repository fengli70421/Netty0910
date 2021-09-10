package com.bob.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Netty服务端
 */

public class NettyServer {
//    public static void main(String[] args) throws InterruptedException {
    public static void run() throws InterruptedException {
        // 1 创建bossGroup线程组：处理网络事件——连接事件
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // 2 创建workerGroup线程组：处理网络事件——读写事件
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        // 3 创建服务端启动助手
        ServerBootstrap bootstrap = new ServerBootstrap();
        // 4 设置bossGroup线程组和workerGroup线程组
        bootstrap.group(bossGroup,workerGroup)
                // 设置服务端通道实现为Nio
                .channel(NioServerSocketChannel.class)
                // 创建通道初始化对象
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        // 7 向pipeline中添加自定义业务处理handler
                        socketChannel.pipeline().addLast(new StringEncoder());
                        socketChannel.pipeline().addLast(new StringDecoder());
                        socketChannel.pipeline().addLast(new NettyServerHandler());
                    }
                });
        // 8 启动服务端并绑定端口，同时将异步改为同步
        ChannelFuture channelFuture = bootstrap.bind(8090).sync();
        System.out.println("==============服务端启动成功==============");
        // 9 关闭通道和关闭连接池
        channelFuture.channel().closeFuture().sync();
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
