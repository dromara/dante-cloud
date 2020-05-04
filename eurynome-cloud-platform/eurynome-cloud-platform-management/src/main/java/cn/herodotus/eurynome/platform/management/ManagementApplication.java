package cn.herodotus.eurynome.platform.management;

import cn.herodotus.eurynome.component.management.annotation.EnableHerodotusManagement;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>Description: 平台管理服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/3 16:08
 */
@SpringBootApplication
@EnableAdminServer
@EnableHerodotusManagement
public class ManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagementApplication.class, args);
    }

}
