package com.bob.service;

import com.bob.server.NettyServer;
import org.springframework.stereotype.Service;

@Service
public class StartServerService {
    NettyServer nettyServer = new NettyServer();
    public void startServer() throws InterruptedException {
        nettyServer.run();
    }
}
