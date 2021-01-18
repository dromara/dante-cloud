package cn.herodotus.eurynome.uaa;

import cn.herodotus.eurynome.kernel.annotation.EnableHerodotusKernel;
import cn.herodotus.eurynome.upms.api.annotation.EnableUpmsInterface;
import cn.herodotus.eurynome.upms.logic.annotation.EnableUpmsLogic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableUpmsLogic
@EnableHerodotusKernel
public class UaaApplication {

    public static void main(String[] args) {
        SpringApplication.run(UaaApplication.class, args);
    }

}
