package com.bob.service;

import com.bob.Entity.Processer;
import com.bob.Entity.Transaction;
import com.bob.Entity.TransactionManager;
import com.bob.client.NettyClient;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.TimerTask;
import java.util.Timer;

@Service
public class StartClientService {
    // 设置定时器
    Timer nettyTimer = new Timer();
    Timer dbTimer = new Timer();
    // 初始化Netty客户端
    NettyClient nettyClient = new NettyClient();
    // 创建事物管理器
    public TransactionManager transactionManager = new TransactionManager();
    // 调用Processor入库
    Processer processer = new Processer();
    // 间隔时间
    int intervalNettyTask = 1;
    int intervalDBTask = 1000;
    //
    boolean isNettyRunning = false;
    boolean isDBTaskRunning = false;
    // 获取参数
    String host = "localhost";//
    int port = 8090;


    public void startNettyTimer() {
        if (!isNettyRunning) {
            nettyTimer.schedule(new nettyTask(), 1000, intervalNettyTask);
            //  安排指定的任务指定的延迟后开始进行重复的固定延迟执行
            isNettyRunning = true;
        }
    }

//    public void startDBTimer() {
//        System.out.println(transactionManager.getUndoneTransactions().size());
//        if (!isDBTaskRunning) {
//            dbTimer.schedule(new dbTask(), 2000, intervalDBTask);
//        }
//    }

    public void stopNettyTimer() {
        if (isNettyRunning) {
            nettyTimer.cancel();
            isNettyRunning = false;
        }
    }

    public void stopDBTimer() {
        if (isDBTaskRunning) {
            dbTimer.cancel();
            isDBTaskRunning = false;
        }
    }


    /**
     * 请求任务
     */
    class nettyTask extends TimerTask {
        @Override
        public void run(){
            // 将任务管理器传进Netty客户端记录完成状态
            Transaction transaction = transactionManager.createTransaction();
//            Transaction transaction_ = nettyClient.connectRequest(host, port);
            nettyClient.connectRequest(host, port);
            transaction.commitPass();
            transactionManager.getUndoneTransactions().add(transaction);
            processer.insertDBPerTransaction(transaction);
//            System.out.println("00000000000000"+transaction_.getName());
        }
    }

    /**
     * 入库任务
     */
//    class dbTask extends TimerTask {
//        // 和nettyTask错开，分批入库
//        @Override
//        public void run() {
//            Set<Transaction> setTempt = transactionManager.getUndoneTransactions();
//            transactionManager.getUndoneTransactions().clear();
//            processer = new Processer(setTempt);
////            System.out.println("+++++++++++++++++++++++++++"+processer.setTransaction.size());
//            processer.calculate();
//            processer.insertDB();
//            processer.clear();
////            setTempt.clear();
//        }
//    }

}
