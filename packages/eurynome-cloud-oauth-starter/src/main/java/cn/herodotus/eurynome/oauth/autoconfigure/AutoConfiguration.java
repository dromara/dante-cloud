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
 * Module Name: eurynome-cloud-oauth-starter
 * File Name: AutoConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/05/13 11:10:13
 */

package cn.herodotus.eurynome.oauth.autoconfigure;

import cn.herodotus.eurynome.common.definition.MessageProducer;
import cn.herodotus.eurynome.crud.annotation.EnableHerodotusCrud;
import cn.herodotus.eurynome.message.queue.KafkaProducer;
import cn.herodotus.eurynome.oauth.autoconfigure.service.HerodotusOauthClientDetailsService;
import cn.herodotus.eurynome.oauth.autoconfigure.service.HerodotusOauthUserDetailsService;
import cn.herodotus.eurynome.oauth.condition.LocalStrategyCondition;
import cn.herodotus.eurynome.oauth.condition.RemoteStrategyCondition;
import cn.herodotus.eurynome.security.definition.service.HerodotusClientDetailsService;
import cn.herodotus.eurynome.security.definition.service.HerodotusUserDetailsService;
import cn.herodotus.eurynome.security.definition.service.StrategySecurityMetadataService;
import cn.herodotus.eurynome.security.service.RemoteSecurityMetadataService;
import cn.herodotus.eurynome.upms.api.annotation.EnableUpmsInterface;
import cn.herodotus.eurynome.upms.logic.annotation.EnableUpmsLogic;
import cn.herodotus.eurynome.upms.logic.strategy.LocalSecurityMetadataService;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import javax.annotation.PostConstruct;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: AutoConfiguration </p>
 *
 * <p>Description: OAuth Starter </p>
 *
 * ----- Bean注册优先级 -----
 * Spring Boot注册Bean多种方式，常见的有：
 * · 直接注册(@SpringBootApplication)、
 * · 自动配置（classpath:/META-INF/spring.factories）、
 * · 启用组件(@EnableXXX)。
 *
 * 而直接注册、自动配置、启用组件中注册方式通常分为：
 * · 扫描(@ComponentScan) 和
 * · Java配置方式(@Bean)注册。
 *
 * 使用Spring Boot时会通常我们会使用 @ConditionalOnBean 做判断，依据某个Bean是否存在而决定是否注册另一个Bean，但由于Srping Boot Bean注册顺序的问题可能导致意外的情况发生。
 * Bean注册可简单理解为存在优先级梯队，在同一梯队或者向上梯队使用@ConditionalOnBean来决定是否注册Bean是可以的，但如果从上梯队向下梯队使用@ConditionalOnBean则会导致无法成功注册。
 *
 * Bean注册优先级梯队
 * 第一梯队如下：
 * 1.1. 扫描的Bean（即@ComponentScan），包括直接扫描、自动配置中扫描、启用组件中扫描的Bean：
 *      即扫描标注了 @Controller @Service @Repository @Component 的类
 * 1.2. 直接的Java配置，即@SpringBootApplication直接覆盖的包下用@Bean注册的Bean
 *
 * 第二梯队如下：
 * 2.1. 启用组件中的Java配置注册的Bean，即在@EnableXXX引入、而且使用@Bean注册的Bean
 *
 * 第三梯队如下：
 * 3.1. 自动配置中的Java配置注册的Bean，即在自动配置（classpath:/META-INF/spring.factories）中引入、而且使用@Bean注册的Bean
 * {@link :https://www.jianshu.com/p/0527648871d3}
 *
 *
 * ----- @ConditionalOnBean解析优先级 -----
 * 在spring ioc的过程中，
 * - 优先解析@Component，@Service，@Controller注解的类。
 * - 其次解析配置类，也就是@Configuration标注的类。
 * - 最后开始解析配置类中定义的bean。
 *
 * 示例代码中bean1是定义在配置类中的，当执行到配置类解析的时候，@Component，@Service，@Controller ,@Configuration标注的类已经全部被解析，所以这些BeanDifinition已经被同步。
 * 但是bean1的条件注解依赖的是bean2，bean2是被定义的配置类中的，因为两个Bean都是配置类中Bean，所以此时配置类的解析无法保证先后顺序，就会出现不生效的情况。
 *
 * 解决办法，有两种方式：
 *
 * 项目中条件注解依赖的类，大多会交给spring容器管理，所以如果要在配置中Bean通过@ConditionalOnBean依赖配置中的Bean时，完全可以用@ConditionalOnClass(Bean2.class)来代替。
 * 如果一定要区分两个配置类的先后顺序，可以将这两个类交与EnableAutoConfiguration管理和触发。也就是定义在META-INF\spring.factories中声明是配置类，
 * 然后通过@AutoConfigureBefore、AutoConfigureAfter、AutoConfigureOrder控制先后顺序。因为这三个注解只对自动配置类生效。
 *
 * {@link :https://blog.csdn.net/woshilijiuyi/article/details/84147483}
 *
 * @author : gengwei.zheng
 * @date : 2021/1/17 11:07
 */
@Slf4j
@Configuration
@EnableHerodotusCrud
@EnableUpmsInterface
@EnableUpmsLogic
@EnableMethodCache(basePackages = {
        "cn.herodotus.eurynome.oauth.autoconfigure.service",
})
public class AutoConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.info("[Eurynome] |- Starter [Herodotus OAuth Starter] Auto Configure.");
    }

    @Configuration(proxyBeanMethods = false)
    @Conditional(LocalStrategyCondition.class)
    static class DataAccessStrategyLocalConfiguration {

        @Bean
        public StrategySecurityMetadataService localSecurityMetadataStoreService() {
            log.trace("[Eurynome] |- Bean [Local Security Metadata Storage Service] Auto Configure.");
            return new LocalSecurityMetadataService();
        }
    }

    @Configuration
    @Conditional(RemoteStrategyCondition.class)
    static class DataAccessStrategyRemoteConfiguration {

        @Bean
        @ConditionalOnMissingBean(MessageProducer.class)
        public MessageProducer kafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
            KafkaProducer kafkaProducer = new KafkaProducer(kafkaTemplate);
            log.trace("[Eurynome] |- Bean [Kafka Producer] Auto Configure.");
            return kafkaProducer;
        }

        @Bean
        @ConditionalOnBean(MessageProducer.class)
        public StrategySecurityMetadataService remoteSecurityMetadataStorageService(MessageProducer messageProducer) {
            log.trace("[Eurynome] |- Bean [Remote Security Metadata Storage Service] Auto Configure.");
            return new RemoteSecurityMetadataService(messageProducer);
        }
    }

    @Bean
    @ConditionalOnMissingBean(HerodotusUserDetailsService.class)
    public HerodotusUserDetailsService herodotusUserDetailsService() {
        HerodotusOauthUserDetailsService herodotusOauthUserDetailsService = new HerodotusOauthUserDetailsService();
        log.trace("[Eurynome] |- Bean [Herodotus User Details Service] Auto Configure.");
        return herodotusOauthUserDetailsService;
    }

    @Bean
    @ConditionalOnMissingBean(HerodotusClientDetailsService.class)
    public HerodotusClientDetailsService herodotusClientDetailsService() {
        HerodotusOauthClientDetailsService herodotusOauthClientDetailsService = new HerodotusOauthClientDetailsService();
        log.trace("[Eurynome] |- Bean [Herodotus Client Details Service] Auto Configure.");
        return herodotusOauthClientDetailsService;
    }
}
