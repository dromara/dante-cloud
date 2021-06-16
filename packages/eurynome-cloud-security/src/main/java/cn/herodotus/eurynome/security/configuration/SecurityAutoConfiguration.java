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

package cn.herodotus.eurynome.security.configuration;

import cn.herodotus.eurynome.rest.annotation.EnableHerodotusRest;
import cn.herodotus.eurynome.rest.properties.PlatformProperties;
import cn.herodotus.eurynome.rest.properties.RestProperties;
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
        log.trace("[Eurynome] |- Bean [Default Access Token Converter] Auto Configure.");
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
    public RequestMappingScanner requestMappingScanner(RestProperties restProperties, PlatformProperties platformProperties, SecurityMetadataStorage securityMetadataStorage) {
        RequestMappingScanner requestMappingScan = new RequestMappingScanner(restProperties, platformProperties, securityMetadataStorage);
        log.trace("[Eurynome] |- Bean [Request Mapping Scanner] Auto Configure.");
        return requestMappingScan;
    }
}
