package cn.herodotus.eurynome.integration.content.configuration;

import cn.herodotus.eurynome.integration.content.properties.AliyunProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;

/**
 * <p>Description: 阿里云对象存储配置类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/15 11:41
 */
@Slf4j
@EnableConfigurationProperties(AliyunProperties.class)
@ComponentScan(basePackages = {
		"cn.herodotus.eurynome.integration.content.service.aliyun.oss"
})
public class AliyunOssConfiguration {

    @PostConstruct
    public void init() {
        log.info("[Braineex] |- Bean [Aliyun Oss] Auto Configure.");
    }
}
