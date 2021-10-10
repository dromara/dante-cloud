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
 * File Name: WebMvcAutoConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

package cn.herodotus.eurynome.security.autoconfigure;

import cn.herodotus.eurynome.rest.crypto.DecryptRequestParamMapResolver;
import cn.herodotus.eurynome.rest.crypto.DecryptRequestParamResolver;
import cn.herodotus.eurynome.rest.security.AccessLimitedInterceptor;
import cn.herodotus.eurynome.rest.security.IdempotentInterceptor;
import cn.herodotus.eurynome.security.configuration.WebMvcSecurityConfiguration;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.method.annotation.RequestParamMapMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * <p>Description: WebMvcAutoConfiguration </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/4 11:00
 */
@Configuration
@Import({
        WebMvcSecurityConfiguration.class
})
public class WebMvcAutoConfiguration extends WebMvcConfigurationSupport {

    private static final Logger log = LoggerFactory.getLogger(WebMvcAutoConfiguration.class);

    @Autowired
    private IdempotentInterceptor idempotentInterceptor;
    @Autowired
    private AccessLimitedInterceptor accessLimitedInterceptor;
    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
    @Autowired
    private DecryptRequestParamResolver decryptRequestParamResolver;
    @Autowired
    private DecryptRequestParamMapResolver decryptRequestParamMapResolver;

    @PostConstruct
    public void postConstruct() {
        this.postArgumentMethodHandlers();
        log.info("[Herodotus] |- Core [Web Mvc Configurer] Auto Configure.");
    }

    private void postArgumentMethodHandlers() {
        List<HandlerMethodArgumentResolver> unmodifiableList = requestMappingHandlerAdapter.getArgumentResolvers();
        List<HandlerMethodArgumentResolver> list = Lists.newArrayList();
        for (HandlerMethodArgumentResolver methodArgumentResolver : unmodifiableList) {
            //需要在requestParam之前执行自定义逻辑，然后再执行下一个逻辑（责任链模式）
            if (methodArgumentResolver instanceof RequestParamMapMethodArgumentResolver) {
                decryptRequestParamMapResolver.setRequestParamMapMethodArgumentResolver((RequestParamMapMethodArgumentResolver) methodArgumentResolver);
                list.add(decryptRequestParamMapResolver);
            }
            if (methodArgumentResolver instanceof RequestParamMethodArgumentResolver) {
                decryptRequestParamResolver.setRequestParamMethodArgumentResolver((RequestParamMethodArgumentResolver) methodArgumentResolver);
                list.add(decryptRequestParamResolver);
            }
            list.add(methodArgumentResolver);
        }
        log.debug("[Herodotus] |- Custom HandlerMethodArgumentResolver Auto Configure.");
        requestMappingHandlerAdapter.setArgumentResolvers(list);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessLimitedInterceptor);
        registry.addInterceptor(idempotentInterceptor);
        super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
