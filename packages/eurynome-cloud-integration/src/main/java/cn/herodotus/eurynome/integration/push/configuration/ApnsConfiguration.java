package cn.herodotus.eurynome.integration.push.configuration;

import cn.herodotus.eurynome.integration.push.properties.ApnsProperties;
import com.turo.pushy.apns.ApnsClient;
import com.turo.pushy.apns.ApnsClientBuilder;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

/**
 * <p>Description: IOS Apns 配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/5 14:59
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(ApnsProperties.class)
@ComponentScan(basePackages = {
        "cn.herodotus.eurynome.integration.push.service.apns"
})
public class ApnsConfiguration {

    @Autowired
    private ApnsProperties apnsProperties;

    @PostConstruct
    public void init() {
        log.info("[Herodotus] |- Bean [IOS Apns] Auto Configure.");
    }

    @Bean
    public ApnsClient getApnsClient() {

        ApnsClient apnsClient = null;

        File p12File;
        String p12Password = apnsProperties.getPassword();
        String apnsServer = apnsProperties.isSandbox() ? ApnsClientBuilder.DEVELOPMENT_APNS_HOST : ApnsClientBuilder.PRODUCTION_APNS_HOST;

        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            p12File = resolver.getResource(apnsProperties.getFilePath()).getFile();
        } catch (IOException e) {
            log.error("[Herodotus] |- Can not find the p12 file!");
            return null;
        }

        try {
            EventLoopGroup eventLoopGroup = new NioEventLoopGroup(4);
            apnsClient = new ApnsClientBuilder()
                    .setApnsServer(apnsServer)
                    .setClientCredentials(p12File, p12Password)
                    .setConcurrentConnections(4)
                    .setEventLoopGroup(eventLoopGroup)
                    .build();
        } catch (Exception e) {
            log.error("[Herodotus] |- ios get pushy apns client failed!");
        }

        return apnsClient;
    }
}
