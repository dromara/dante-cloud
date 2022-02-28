package cn.herodotus.eurynome.cloud.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>Description: Spring Boot Admin 服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2022/2/28 16:49
 */
@EnableAdminServer
@SpringBootApplication
public class MonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitorApplication.class, args);
    }

}
