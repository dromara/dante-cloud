package cn.herodotus.eurynome.integration.facilities.configuration;

import cn.herodotus.eurynome.integration.facilities.properties.NacosProperties;
import com.ejlchina.okhttps.FastjsonMsgConvertor;
import com.ejlchina.okhttps.HTTP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;

/**
 * <p>File: NacosConfiguration </p>
 *
 * <p>Description: Nacos Configuration </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/5 17:19
 */
@Slf4j
@EnableConfigurationProperties(NacosProperties.class)
@ComponentScan(basePackages = {
        "cn.herodotus.eurynome.integration.facilities.service.nacos"
})
public class NacosConfiguration {

    @Autowired
    private NacosProperties nacosProperties;

    @PostConstruct
    public void postConstruct() {
        log.info("[Eurynome] |- Bean [Nacos Configuration] Auto Configure.");
    }

    @Bean("nacosApi")
    public HTTP nacosApi() {
        log.debug("[Eurynome] |- Nacos API Auto Configure.");
        return HTTP.builder()
                .baseUrl(nacosProperties.getBaseUrl())
                .addMsgConvertor(new FastjsonMsgConvertor())
                .build();
    }
}
