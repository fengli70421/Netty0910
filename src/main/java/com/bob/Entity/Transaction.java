package com.bob.Entity;

import java.util.Date;

//import java.text.SimpleDateFormat;
//   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//System.out.println(df.format(new Date()));// new Date()为获取当前系统时间


public class Transaction {

    private long start;
    private String name;
    private float duration;
//    private String reason;
    /*
     *  undone = -1，fail = 1；pass = 0
     */
    private int endStatus;
//    Event<Object> transactionIsOver;
//    Event<Object> transactionIsTimeout;
    //超时时间是120秒
//    private final static int expireTime = UserManager.transTimeout;
    // friendly in packet
    Transaction(String name)
    {
        start = System.currentTimeMillis();//获得到了当前的系统时间
//        transactionIsOver = new Event<Object>();
//        transactionIsTimeout = new Event<Object>();
        this.name = name;
        endStatus = -1;
//        reason = "pass";
    }

    public Transaction(String name, float duration, boolean isPassed)
    {
        start = System.currentTimeMillis();
        this.name = name;
        this.duration = duration;
        this.endStatus = isPassed? 0:1;
//        reason = isPassed?"pass":"failed";
    }

    Boolean isUnCommitted(){
        return endStatus==-1;
    }
    Boolean isPassed(){
        return endStatus==0;
    }
    public int getEndStatus(){
        return endStatus;
    }
    public void commitPass()
    {
//        if(getEndStatus() != -1){
//            UserManager.getTransManager().CreateTransaction(this.name + "_CommitDuplicate", 0, true);
//            return;
//        }
        completeTransaction(true);
    }
    public void commitFail()
    {
//        if (getEndStatus() != -1) {
//            UserManager.getTransManager().CreateTransaction(this.name + "_CommitDuplicate", 0, true);
//            return;
//        }
//        this.reason = reason;
//        UserManager.getTransManager().CreateTransaction(this.name+"_"+ reason, 0, false);
        completeTransaction(false);
    }

    private void completeTransaction(boolean isPassed)
    {
        this.endStatus = isPassed?0:1;
        long miniSeconds=new Date().getTime()-start;
        this.duration = (float)miniSeconds/1000;
//        transactionIsOver.invoke(this,null);

    }
    public String getName()
    {
        return name;
    }
//    @Override
//    public 	String toString(){
//        return String.format("name:%s,duration:%s,status:%s,reason:%s", name,duration,endStatus,reason);
//    }
    public long getStartTime(){
        return start;
    }

    public float getDuration(){
        return duration;
    }

//    public String getReason() {
//        if(null == reason){
//            throw new RuntimeException("do not set the null value to reason . TranName:" + this.getName());
//        }
//        if (reason.length() > 50)
//            return reason.substring(0, 40) + "too long.";
//        else
//            return reason;
//    }
//    public static int getExpireTime() {
//        return expireTime;
//    }

}