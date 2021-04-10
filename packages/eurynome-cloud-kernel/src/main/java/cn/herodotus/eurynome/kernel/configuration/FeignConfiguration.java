/*
 * Copyright (c) 2019-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-kernel
 * File Name: FeignConfiguration.java
 * Author: gengwei.zheng
 * Date: 2020/12/30 上午10:10
 * LastModified: 2020/6/19 下午3:36
 */

package cn.herodotus.eurynome.kernel.configuration;

import cn.herodotus.eurynome.kernel.feign.HerodotusFeignRequestInterceptor;
import feign.Request;
import feign.RequestInterceptor;
import feign.Retryer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * <p> Description : 自定义通用的Feign Fallback处理工厂(基于Sentinel) </p>
 * <p>
 * {@see https://blog.csdn.net/ttzommed/article/details/90669320}
 *
 * @author : gengwei.zheng
 * @date : 2020/3/1 18:35
 */
@Slf4j
@Configuration
public class FeignConfiguration {

    @Bean
    @ConditionalOnMissingBean(HerodotusFeignRequestInterceptor.class)
    public RequestInterceptor feignRequestInterceptor() {
        HerodotusFeignRequestInterceptor oAuth2FeignRequestInterceptor = new HerodotusFeignRequestInterceptor();
        log.debug("[Eurynome] |- Bean [Feign Request Interceptor] Auto Configure.");
        return oAuth2FeignRequestInterceptor;
    }

    /**
     * FeignClient超时设置
     * feign超时设置有3种方式：配置文件直接配置FeignClient、自定义Request.Options及配置文件配置Ribbon，优先级从高到低如下。
     * 1、配置文件里对特定FeignClient配置属性： feign.client.config.demo.connectTimeout=1000，feign.client.config.demo.readTimeout=2000 ；
     * 2、自定义对特定FeignClient生效的Request.Options类型的Bean；
     * 3、配置文件里对所有FeienClient属性的配置：feign.client.config.default.connectTimeout=1000，feign.client.config.default.readTimeout=5000
     * 4、对全体FeignClient生效的Request.Options类型的Bean；
     * 5、特定服务的ribbon配置：demo.ribbon.ConnectTimeout=1000，demo.ribbon.ReadTimeout=5000
     * 6、全体服务的ribbon配置：ribbon.ConnectTimeout=1000，ribbon.ReadTimeout=5000
     * 7、Ribbon默认配置：默认连接超时和读取超时都是1000，即1秒
     *
     * 总结一下：
     * 1、FeignClient的直接配置高于Ribbon的配置
     * 2、特定服务的配置高于全体服务的配置
     * 3、配置文件的配置高于自定义Request.Options
     * 4、如果有特定服务的Options和全体服务的配置文件配置，遵循第二条规则，以特定服务的Options为准；
     * 5、如果有特性服务的Ribbon配置和全体服务的FeignClient配置，遵循第一条规则，以FeingClient的配置为准
     *
     * 最佳实践：
     * 1、不要采用Ribbon配置而要直接配置FeignClient，即配置feign.client.xx
     * 2、配置文件配置全体FeignClient的超时设置，同时对特定服务有特殊设置的，也在配置文件里配置
     *
     * {@see :https://blog.csdn.net/weixin_36244726/article/details/103953852}
     */
    @Bean
    public Request.Options options() {
        return new Request.Options(10, TimeUnit.SECONDS, 60, TimeUnit.SECONDS, true);
    }

    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default();
    }
}
