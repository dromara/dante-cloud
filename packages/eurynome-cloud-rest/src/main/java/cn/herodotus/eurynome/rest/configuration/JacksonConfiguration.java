/*
 * Copyright (c) 2019-2021 the original author or authors.
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
 * Module Name: eurynome-cloud-rest
 * File Name: JacksonAutoConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/1/17 下午3:58
 * LastModified: 2020/6/19 下午3:38
 */

package cn.herodotus.eurynome.rest.configuration;

import cn.herodotus.eurynome.common.utils.JacksonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * <p>Description: Jackson配置 </p>
 * 
 * @author : gengwei.zheng
 * @date : 2019/11/8 17:15
 */
@Slf4j
@Configuration
@AutoConfigureAfter(org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration.class)
public class JacksonConfiguration {

    @Bean(name = "jacksonObjectMapper")
    @ConditionalOnMissingBean(ObjectMapper.class)
    @Primary
    public ObjectMapper jacksonObjectMapper() {
        log.debug("[Eurynome] |- Bean [Jackson Object Mapper] Auto Configure.");
        return JacksonUtils.getObjectMapper();
    }

    /**
     * 转换器全局配置
     *
     * @return MappingJackson2HttpMessageConverter
     */
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter(jacksonObjectMapper());
    }

}
