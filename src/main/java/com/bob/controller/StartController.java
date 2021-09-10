package com.bob.controller;


import com.bob.service.StartClientService;
import com.bob.service.StartServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class StartController {
    @Autowired
    StartClientService startClientService = new StartClientService();
    @Autowired
    StartServerService startServerService;
    @RequestMapping(value = "/client")
    public String Client() throws InterruptedException {
        startClientService.startNettyTimer();
        return "start client";
    }

//    @RequestMapping(value = "/dbInsert")
//    public String dbInsert() throws InterruptedException {
//        System.out.println("========================================================="+startClientService.transactionManager.getUndoneTransactions().size());
//        startClientService.startDBTimer();
//        return "start dbInsert";
//    }

    @RequestMapping(value = "/server")
    public String Server() throws InterruptedException {
        startServerService.startServer();
        return "start server";
    }

    @RequestMapping(value = "/test")
    public String test() {
        return "test";
    }
}
