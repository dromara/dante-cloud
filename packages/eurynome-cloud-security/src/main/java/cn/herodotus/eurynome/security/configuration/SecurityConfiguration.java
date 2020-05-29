package cn.herodotus.eurynome.security.configuration;

import cn.herodotus.eurynome.localstorage.annotation.EnableHerodotusLocalStorage;
import cn.herodotus.eurynome.localstorage.service.SecurityMetadataService;
import cn.herodotus.eurynome.message.annotation.EnableHerodotusMessage;
import cn.herodotus.eurynome.message.stream.service.SecurityMetadataMessage;
import cn.herodotus.eurynome.rest.annotation.EnableHerodotusRest;
import cn.herodotus.eurynome.rest.metadata.RequestMappingScan;
import cn.herodotus.eurynome.rest.metadata.SecurityMetadataPersistence;
import cn.herodotus.eurynome.rest.properties.ApplicationProperties;
import cn.herodotus.eurynome.security.web.access.intercept.HerodotusSecurityMetadataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import javax.annotation.Resource;

/**
 * <p> Description : 模块辅助注册类 </p>
 * <p>
 * 由于采用了模块化的方式进行划分，一方面不能将所有的模块放入同一个包中，另一方面如果在每一个使用类中都使用@ComponentScan，不是很优雅。
 * 因此之前就采用在starter中用@ComponentScan进行包扫描。这种方式会有很多隐性问题。
 * <p>
 * 查到一篇文章，里面不建议这么使用。  {@link :https://gooroo.io/GoorooTHINK/Article/17466/Lessons-Learned-Writing-Spring-Boot-Auto-Configurations/29652#.XjfZ9LCHqUn}
 * <p>
 * ·Auto configurations should never be included via @ComponentScan because ordering cannot be guaranteed.
 * ·Auto configurations should live in a different package to avoid being accidentally picked up by @ComponentScan.
 * ·Auto configurations should be declared in a META-INF/spring.factories and should NOT be subject to @ComponentScan as mentioned above.
 * ·@Ordered does not apply to @Configuration classes since Spring Boot 1.3.
 * ·Use @AutoConfigureOrder, @AutoConfigureBefore, and @AutoConfigureAfter to order auto configurations for Spring Boot 1.3 or greater.
 * ·Avoid using @ConditionalOnX annotations outside of auto-configurations. @ConditionalOnX annotations are sensitive to ordering and ordering cannot be guaranteed with just @Configuration classes.
 * <p>
 * 参考Flowable的用法，单独再定义一个@Component，进行@ComponentScan。在其它使用的地方进行@Import调用
 * <p>
 * 因此，至此形成一个约定：
 * 1、如果一个模块中有需要扫描的内容，例如properties和configuration等。那么就新建一个@Component进行辅助。
 * 2、@ComponentScan尽可能定位到具体的包，尽量不要用通配符。
 *
 * @author : gengwei.zheng
 * @date : 2020/3/2 16:00
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = {
        "cn.hutool.extra.spring",
        "cn.herodotus.eurynome.security.configuration",
        "cn.herodotus.eurynome.security.authentication",
})
@EnableHerodotusLocalStorage
@EnableHerodotusMessage
@EnableHerodotusRest
public class SecurityConfiguration  {

    @Resource
    private SecurityMetadataMessage securityMetadataMessage;
    @Resource
    private SecurityMetadataService securityMetadataService;

    @Bean
    @ConditionalOnMissingBean(SecurityMetadataPersistence.class)
    @ConditionalOnBean({SecurityMetadataMessage.class, SecurityMetadataService.class})
    public SecurityMetadataPersistence securityMetadataPersistence() {
        SecurityMetadataPersistence securityMetadataPersistence = new SecurityMetadataPersistence(securityMetadataService, securityMetadataMessage);
        log.debug("[Herodotus] |- Bean [Security Metadata Persistence] Auto Configure.");
        return securityMetadataPersistence;
    }

    /**
     * 自定义注解扫描
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(RequestMappingScan.class)
    @ConditionalOnBean(SecurityMetadataPersistence.class)
    public RequestMappingScan requestMappingScan(SecurityMetadataPersistence securityMetadataPersistence, ApplicationProperties applicationProperties) {
        RequestMappingScan requestMappingScan = new RequestMappingScan(securityMetadataPersistence, applicationProperties, EnableResourceServer.class);
        log.debug("[Herodotus] |- Bean [Request Mapping Scan] Auto Configure.");
        return requestMappingScan;
    }

    @Bean
    @ConditionalOnBean(SecurityMetadataService.class)
    public FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource() {
        HerodotusSecurityMetadataSource herodotusSecurityMetadataSource = new HerodotusSecurityMetadataSource(securityMetadataService);
        log.debug("[Herodotus] |- Bean [Security Metadata Source] Auto Configure.");
        return herodotusSecurityMetadataSource;
    }
}
