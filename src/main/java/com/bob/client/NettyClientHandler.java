package com.bob.client;


import com.bob.Entity.Transaction;
import com.bob.Entity.TransactionManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 客户端自定义处理类
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<String> {
    // 创建事物
//    TransactionManager transactionManager;
//    Transaction transaction;
//
//    public Transaction getTransaction() {
//        return transaction;
//    }

    /**
     * 通道读取事件——读取服务端发送的消息
     */
//    NettyClientHandler(TransactionManager transactionManager){
//        this.transactionManager = transactionManager;
//    }
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object s) throws Exception {
//        System.out.println("客户端接收的消息："+s);
//        transaction.commitPass();
    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, String s) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("你好，我是Netty客户端");
//        transaction = transactionManager.createTransaction();
    }

}
