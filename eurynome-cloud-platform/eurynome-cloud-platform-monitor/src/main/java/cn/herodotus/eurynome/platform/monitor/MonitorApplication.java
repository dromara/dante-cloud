package cn.herodotus.eurynome.platform.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * <p>Description: MonitorApplication </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/4/6 12:26
 */
@SpringBootApplication
@EnableAdminServer
public class MonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitorApplication.class, args);
    }

}
