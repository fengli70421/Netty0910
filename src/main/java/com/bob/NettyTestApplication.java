package com.bob;

import com.bob.service.StartClientService;
import com.bob.service.StartServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})

public class NettyTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyTestApplication.class, args);
    }

}
