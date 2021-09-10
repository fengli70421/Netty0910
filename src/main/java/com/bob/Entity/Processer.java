package com.bob.Entity;

//import com.bob.config.InfluxdbConfig;
import lombok.Data;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class Processer {
    /**
     * 入库操作
     */
    // 日志
    private static String EXP_LOG = "log";
    private static String EXP_INFLUXDB = "influxdb";
    private static String EXP_MQ = "mq";
    private String export = EXP_LOG;

    Logger log = LoggerFactory.getLogger(Processer.class);

//    public Map<String, List<Transaction>> transMap = null;

    // 转换成indexMap入库
    public Map<String, Indexes> indexMap = null;
    public Set<Transaction> setTransaction;

    // influxdb configuration
    private String influxdbUrl = "http://10.50.129.156:8086";//10.50.129.156
    private String influxdbUser = "weilujie";
    private String influxdbPwd = "123456";
    private String influxdbDb = "TRANSACTION_TEST_09090850";

//    @Autowired
//    public InfluxDB influxDB;

//    Processer(Set<Transaction> setTransaction) {
//        this.setTransaction = setTransaction;
//        indexMap = new HashMap<String, Indexes>();
//    }
//    public Processer(){
//
//    }

    public void clear() {
        // tranMap中含有多个事物列表
//        for (List l : transMap.values()) {
//            l.clear();
//        }
        indexMap.clear();
    }

    private String getHostname() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return address.getHostName();
    }

    public void calculate() {
        // 事物列表统计，平均响应时间
        // 响应时间：发起请求到收到服务器响应结果的时间
        // TPS(TransactionsPerSecond)
        // transMap转化为indexMap用于添加到数据库
        for (Transaction transaction : setTransaction) {
            // tranMap.keySet() 获取key列表
            Indexes index = new Indexes(); // index记录每组事物的信息
            System.out.println(transaction.getName());

            index.setName(transaction.getName()); //注名
            index.setStart(transaction.getStartTime());
            index.setDuration(transaction.getDuration());
            index.setEndStatus(transaction.getEndStatus());

            indexMap.put(transaction.getName(), index); // 每个list加上index信息
            setTransaction.remove(transaction);
        }
    }

    public void insertDB(){
//        String database = influxDB.g
        InfluxDB influxdb = InfluxDBFactory.connect(influxdbUrl, influxdbUser, influxdbDb);

//        influxdb.setDatabase(influxdbDb);
        /*
         * 数据贮存器的意义在于我们可以把事务收集起来，批量的发送到时序数据库。通过此结构的设计使得每一个事务的入库操作不会产生阻塞。
         * */
//        influxdb.enableBatch(1000, 100, TimeUnit.MILLISECONDS); // 每100ms注入1000个数据点

        String hostname = this.getHostname();

        for (Indexes i : indexMap.values()) {
            // 插入数据
            /**
             *
             */
            System.out.println(i.getName());

            Point point = Point.measurement("Transaction_Statistics_09082020")
                    .tag("transaction", i.getName()) // tag字段
                    .addField("start_time", i.getStart()) // 多个field字段
                    .addField("duration", i.getDuration())
                    .addField("status", i.getEndStatus())
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS) // timestamp字段
                    .build();
            influxdb.write("TRANSACTION_TEST_09082020","autogen",point);
        }
    }

    public void insertDBPerTransaction(Transaction transaction){
        InfluxDB influxdb = InfluxDBFactory.connect(influxdbUrl, influxdbUser, influxdbDb);
        Point point = Point.measurement("Transaction_Statistics_09090850")
                .tag("transaction", transaction.getName()) // tag字段
                .addField("start_time", transaction.getStartTime()) // 多个field字段
                .addField("duration", transaction.getDuration())
                .addField("status", transaction.getEndStatus())
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS) // timestamp字段
                .build();
        influxdb.write("TRANSACTION_TEST_09090850","autogen",point);
    }

   public static String getExpLog() {
        return EXP_LOG;
    }

    public static void setExpLog(String expLog) {
        EXP_LOG = expLog;
    }

    public static String getExpInfluxdb() {
        return EXP_INFLUXDB;
    }

    public static void setExpInfluxdb(String expInfluxdb) {
        EXP_INFLUXDB = expInfluxdb;
    }

    public static String getExpMq() {
        return EXP_MQ;
    }

    public static void setExpMq(String expMq) {
        EXP_MQ = expMq;
    }

    public String getExport() {
        return export;
    }

    public void setExport(String export) {
        this.export = export;
    }

    public Logger getLog() {
        return log;
    }

    public void setLog(Logger log) {
        this.log = log;
    }

    public Map<String, Indexes> getIndexMap() {
        return indexMap;
    }

    public void setIndexMap(Map<String, Indexes> indexMap) {
        this.indexMap = indexMap;
    }

//    public String getInfluxdbUrl() {
//        return influxdbUrl;
//    }
//
//    public void setInfluxdbUrl(String influxdbUrl) {
//        this.influxdbUrl = influxdbUrl;
//    }
//
//    public String getInfluxdbUser() {
//        return influxdbUser;
//    }
//
//    public void setInfluxdbUser(String influxdbUser) {
//        this.influxdbUser = influxdbUser;
//    }
//
//    public String getInfluxdbPwd() {
//        return influxdbPwd;
//    }
//
//    public void setInfluxdbPwd(String influxdbPwd) {
//        this.influxdbPwd = influxdbPwd;
//    }
//
//    public String getInfluxdbDb() {
//        return influxdbDb;
//    }
//
//    public void setInfluxdbDb(String influxdbDb) {
//        this.influxdbDb = influxdbDb;
//    }
}
