package cn.herodotus.eurynome.upms.rest;

import cn.herodotus.eurynome.upms.logic.configuration.UpmsLogicConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@Import({
        UpmsLogicConfiguration.class
})
@EnableDiscoveryClient
@SpringBootApplication
public class UpmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UpmsApplication.class, args);
    }

}
