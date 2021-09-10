//package com.bob.Entity;
//
//import com.bob.client.NettyClient;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class GlobalTransactionManager {
//    private static NettyClient nettyClient;
//    private static ThreadLocal<MyTransaction> current = new ThreadLocal<>();
//    private static ThreadLocal<String> currentGroupId = new ThreadLocal<>();
//
//    @Autowired
//    public void setNettyClient(NettyClient nettyClient) {
//        GlobalTransactionManager.nettyClient = nettyClient;
//    }
//
//    public static Map<String, MyTransaction> transactionMap = new HashMap<>();
//
//    /**
//     * 创建事务组
//     *
//     * @return
//     */
////    public static String createGroup() {
////        if (currentGroupId.get() != null) {
////            return currentGroupId.get();
////        }
////        String groupId = UUID.randomUUID().toString();
////        JSONObject jsonObject = new JSONObject();
////        jsonObject.put("groupId", groupId);
////        jsonObject.put("command", CommandType.create.name());
////        nettyClient.send(jsonObject);
////        currentGroupId.set(groupId);
////        System.out.println("创建事务组");
////        return groupId;
////    }
//
//    public static MyTransaction getMyTransaction(String groupId) {
//        return transactionMap.get(groupId);
//    }
//
//    public static MyTransaction getCurrent() {
//        return current.get();
//    }
//
//    public static String getCurrentGroupId() {
//        return currentGroupId.get();
//    }
//
//    public static void setCurrentGroupId(String groupId) {
//        currentGroupId.set(groupId);
//    }
//
//
//}
