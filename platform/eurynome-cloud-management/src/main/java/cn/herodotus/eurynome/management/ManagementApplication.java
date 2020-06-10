package cn.herodotus.eurynome.management;

import cn.herodotus.eurynome.kernel.annotaion.EnableLogCenter;
import cn.herodotus.eurynome.operation.annotation.EnableHerodotusManagement;
import com.alicp.jetcache.autoconfigure.JetCacheAutoConfiguration;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * <p>Description: 平台管理服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/3 16:08
 */
@EnableAdminServer
@EnableLogCenter
@EnableHerodotusManagement
@SpringBootApplication(exclude = {JetCacheAutoConfiguration.class})
public class ManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagementApplication.class, args);
    }

}
