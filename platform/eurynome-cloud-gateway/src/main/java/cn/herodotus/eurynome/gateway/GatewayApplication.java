package cn.herodotus.eurynome.gateway;

import cn.herodotus.eurynome.data.annotation.EnableRedisStorage;
import com.alicp.jetcache.autoconfigure.JetCacheAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * <p>Description: 服务网关服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/6/9 14:41
 */
@EnableRedisStorage
@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, JetCacheAutoConfiguration.class})
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
