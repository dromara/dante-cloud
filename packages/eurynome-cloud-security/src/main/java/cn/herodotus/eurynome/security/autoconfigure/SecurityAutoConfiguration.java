/*
 * Copyright (c) 2019-2021 Gengwei Zheng(herodotus@aliyun.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-security
 * File Name: SecurityAutoConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/05/13 10:59:13
 */

package cn.herodotus.eurynome.security.autoconfigure;

import cn.herodotus.engine.web.core.definition.RequestMappingScanManager;
import cn.herodotus.eurynome.security.authentication.HerodotusRequestMappingScanManager;
import cn.herodotus.eurynome.security.authentication.HerodotusUserAuthenticationConverter;
import cn.herodotus.eurynome.security.authentication.RequestMappingLocalCache;
import cn.herodotus.eurynome.security.configuration.MethodSecurityMetadataConfiguration;
import cn.herodotus.eurynome.security.properties.SecurityProperties;
import cn.herodotus.eurynome.security.response.SecurityGlobalExceptionHandler;
import cn.herodotus.eurynome.security.service.RequestMappingGatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
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
@Configuration
@EnableAsync
@EnableConfigurationProperties({
        SecurityProperties.class
})
@Import({
        MethodSecurityMetadataConfiguration.class
})
@ComponentScan(basePackageClasses = SecurityGlobalExceptionHandler.class)
@RemoteApplicationEventScan({
        "cn.herodotus.eurynome.security.event.remote"
})
public class SecurityAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(SecurityAutoConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Components [Herodotus Security] Auto Configure.");
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
        log.trace("[Herodotus] |- Bean [Default Access Token Converter] Auto Configure.");
        return defaultAccessTokenConverter;
    }

    @Bean
    @ConditionalOnMissingBean(RequestMappingLocalCache.class)
    public RequestMappingLocalCache requestMappingLocalCache() {
        RequestMappingLocalCache requestMappingLocalCache = new RequestMappingLocalCache();
        log.trace("[Herodotus] |- Bean [Request Mapping Local Cache] Auto Configure.");
        return requestMappingLocalCache;
    }

    @Bean
    @ConditionalOnMissingBean(RequestMappingGatherService.class)
    public RequestMappingGatherService requestMappingGatherService(RequestMappingLocalCache requestMappingLocalCache) {
        RequestMappingGatherService requestMappingGatherService = new RequestMappingGatherService();
        requestMappingGatherService.setRequestMappingLocalCache(requestMappingLocalCache);
        log.trace("[Herodotus] |- Bean [Request Mapping Gather Service] Auto Configure.");
        return requestMappingGatherService;
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestMappingScanManager requestMappingScanManager(RequestMappingGatherService requestMappingGatherService) {
        HerodotusRequestMappingScanManager herodotusRequestMappingScanManager = new HerodotusRequestMappingScanManager(requestMappingGatherService);
        log.trace("[Herodotus] |- Bean [Request Mapping Scan Manager] Auto Configure.");
        return herodotusRequestMappingScanManager;
    }
}
