package com.bob.client;

import com.bob.Entity.Transaction;
import com.bob.Entity.TransactionManager;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.concurrent.TimeUnit;

public class NettyClient {
    private Bootstrap b = new Bootstrap();
    private EventLoopGroup group;

    public NettyClient() {
        group = new NioEventLoopGroup();
        b.group(group).channel(NioSocketChannel.class);

    }

    public void connectRequest(String host, int port) {
//        NettyClientHandler nettyClientHandler = new NettyClientHandler();
        b.option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new StringEncoder());
                        socketChannel.pipeline().addLast(new StringDecoder());
                        socketChannel.pipeline().addLast(new NettyClientHandler());
                    }
                });
        b.connect(host, port);

//        return nettyClientHandler.getTransaction();
        return;
    }
}