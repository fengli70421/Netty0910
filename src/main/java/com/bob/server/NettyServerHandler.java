package com.bob.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 服务端业务处理类
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        System.out.println("服务端收到消息："+msg);
    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, String s) throws Exception {

    }

   /**
     * 通道读取完毕事件——给客户端响应
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception{
        ctx.writeAndFlush("你好，我是Netty服务端");
    }


}
