package cn.herodotus.eurynome.upms.rest;

import cn.herodotus.eurynome.operation.annotation.EnableHerodotusManagement;
import cn.herodotus.eurynome.upms.logic.annotation.EnableUpmsLogic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@EnableUpmsLogic
@EnableHerodotusManagement
public class UpmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UpmsApplication.class, args);
    }

}
