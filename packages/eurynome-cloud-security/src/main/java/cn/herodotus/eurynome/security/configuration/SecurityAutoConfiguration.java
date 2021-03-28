package cn.herodotus.eurynome.security.configuration;

import cn.herodotus.eurynome.rest.annotation.EnableHerodotusRest;
import cn.herodotus.eurynome.rest.properties.ApplicationProperties;
import cn.herodotus.eurynome.rest.properties.RestProperties;
import cn.herodotus.eurynome.security.authentication.access.HerodotusAccessDecisionManager;
import cn.herodotus.eurynome.security.authentication.access.HerodotusSecurityMetadataSource;
import cn.herodotus.eurynome.security.authentication.access.RequestMappingScanner;
import cn.herodotus.eurynome.security.authentication.token.HerodotusUserAuthenticationConverter;
import cn.herodotus.eurynome.security.definition.service.SecurityMetadataStorage;
import cn.herodotus.eurynome.security.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

import javax.annotation.PostConstruct;

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
@EnableHerodotusRest
@EnableConfigurationProperties({
        SecurityProperties.class
})
@ComponentScan(basePackages = {
        "cn.hutool.extra.spring",
        "cn.herodotus.eurynome.security.response.exception"
})
public class SecurityAutoConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.info("[Eurynome] |- Components [Herodotus Security] Auto Configure.");
    }

    /**
     * 将所有授权信息都返回到资源服务器
     * 调用自定义用户转换器
     * 用于token解析转换
     */
    @Bean
    public DefaultAccessTokenConverter createDefaultAccessTokenConverter() {
        HerodotusUserAuthenticationConverter herodotusUserAuthenticationConverter = new HerodotusUserAuthenticationConverter();
        DefaultAccessTokenConverter defaultAccessTokenConverter = new DefaultAccessTokenConverter();
        defaultAccessTokenConverter.setUserTokenConverter(herodotusUserAuthenticationConverter);
        log.debug("[Eurynome] |- Bean [Default Access Token Converter] Auto Configure.");
        return defaultAccessTokenConverter;
    }

    /**
     * 自定义注解扫描器
     * <p>
     * 服务权限验证逻辑
     * 2、根据配置扫描服务注解，并存入服务本地Security Metadata存储
     */
    @Bean
    @ConditionalOnMissingBean(RequestMappingScanner.class)
    public RequestMappingScanner requestMappingScanner(RestProperties restProperties, ApplicationProperties applicationProperties, SecurityMetadataStorage securityMetadataStorage) {
        RequestMappingScanner requestMappingScan = new RequestMappingScanner(restProperties, applicationProperties, securityMetadataStorage);
        log.debug("[Eurynome] |- Bean [Request Mapping Scanner] Auto Configure.");
        return requestMappingScan;
    }

    /**
     * 权限信息存储器
     */
    @Bean
    @ConditionalOnMissingBean(HerodotusSecurityMetadataSource.class)
    public HerodotusSecurityMetadataSource herodotusSecurityMetadataSource(SecurityProperties securityProperties, SecurityMetadataStorage securityMetadataStorage) {
        HerodotusSecurityMetadataSource herodotusSecurityMetadataSource = new HerodotusSecurityMetadataSource();
        herodotusSecurityMetadataSource.setSecurityMetadataStorage(securityMetadataStorage);
        herodotusSecurityMetadataSource.setSecurityProperties(securityProperties);
        log.debug("[Eurynome] |- Bean [Herodotus Security Metadata Source] Auto Configure.");
        return herodotusSecurityMetadataSource;
    }

    /**
     * 权限信息判断器
     * <p>
     * 服务权限验证逻辑：
     * 5、捕获用户访问的请求信息，从权限存储其中查找是否有对应的Security Metadata信息。如果有，就说明是权限管控请求；如果没有，就说明是非权限管控请求。
     * 6、权限控制主要针对权限管控请求，把这个请求对应的配置信息，与用户Token中带的权限信息进行比较。如果用户Token中没有这个权限信息，说明该用户就没有被授权。
     */
    @Bean
    @ConditionalOnMissingBean(HerodotusAccessDecisionManager.class)
    public HerodotusAccessDecisionManager herodotusAccessDecisionManager() {
        HerodotusAccessDecisionManager herodotusAccessDecisionManager = new HerodotusAccessDecisionManager();
        log.debug("[Eurynome] |- Bean [Access Decision Manager] Auto Configure.");
        return herodotusAccessDecisionManager;
    }

}
