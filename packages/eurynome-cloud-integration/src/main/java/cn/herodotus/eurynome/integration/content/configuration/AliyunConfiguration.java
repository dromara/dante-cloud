package cn.herodotus.eurynome.integration.content.configuration;

import cn.herodotus.eurynome.integration.content.properties.AliyunOssProperties;
import cn.herodotus.eurynome.integration.content.properties.AliyunProperties;
import cn.herodotus.eurynome.integration.content.properties.AliyunScanProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

/**
 * <p>Description: 阿里云统一配置类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/15 11:43
 */
@Slf4j
@EnableConfigurationProperties({AliyunProperties.class, AliyunOssProperties.class, AliyunScanProperties.class})
@ComponentScan(basePackages = {
        "cn.herodotus.eurynome.integration.content.service.aliyun"
})
@Import({AliyunScanConfiguration.class, AliyunOssConfiguration.class})
public class AliyunConfiguration {

    @PostConstruct
    public void init() {
        log.info("[Eurynome] |- Bean [Aliyun] Auto Configure.");
    }
}
