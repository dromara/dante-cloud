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
 * Module Name: eurynome-cloud-data
 * File Name: RedisConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

package cn.herodotus.eurynome.data.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Redis缓存配置
 *
 * @author gengwei.zheng
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter({RedisAutoConfiguration.class})
public class RedisConfiguration {

    private final Logger log = LoggerFactory.getLogger(RedisConfiguration.class);

    @Resource
    private LettuceConnectionFactory lettuceConnectionFactory;

    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

    private RedisSerializer<Object> valueSerializer() {
        return new Jackson2JsonRedisSerializer<>(Object.class);
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("[Eurynome] |- Plugin [Herodotus Redis] Auto Configure.");
    }

    /**
     * 重新配置一个RedisTemplate
     *
     * @return
     */
    @Bean(name = "redisTemplate")
    @ConditionalOnMissingBean(RedisTemplate.class)
    public RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        redisTemplate.setKeySerializer(keySerializer());
        redisTemplate.setHashKeySerializer(keySerializer());
        redisTemplate.setValueSerializer(valueSerializer());
        redisTemplate.setHashValueSerializer(valueSerializer());
        redisTemplate.setDefaultSerializer(valueSerializer());
        redisTemplate.afterPropertiesSet();

        log.trace("[Eurynome] |- Bean [Redis Template] Auto Configure.");

        return redisTemplate;
    }

    @Bean(name = "stringRedisTemplate")
    @ConditionalOnMissingBean(StringRedisTemplate.class)
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(lettuceConnectionFactory);
        stringRedisTemplate.afterPropertiesSet();

        log.trace("[Eurynome] |- Bean [String Redis Template] Auto Configure.");

        return stringRedisTemplate;
    }

//    @Bean
//    @ConditionalOnBean(RedisTemplate.class)
//    public RedisCaffeineCacheManager cacheManager(RedisTemplate<Object, Object> redisTemplate) {
//        log.debug("[Eurynome] |- Bean [Redis Caffeine Cache Manager] Auto Configure.");
//        return new RedisCaffeineCacheManager(redisCaffeineCacheProperties, redisTemplate);
//    }
//
//    @Bean(name = CacheConstants.DEFAULT_KEY_GENERATOR)
//    public KeyGenerator keyGenerator(){
//        log.debug("[Eurynome] |- Bean [Redis Caffeine Cache Key Generator] Auto Configure.");
//        return new RedisCaffeineCacheKeyGenerator();
//    }
//
//    @Bean
//    public RedisMessageListenerContainer redisMessageListenerContainer(RedisTemplate<Object, Object> redisTemplate,
//                                                                       RedisCaffeineCacheManager redisCaffeineCacheManager) {
//        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
//        redisMessageListenerContainer.setConnectionFactory(redisTemplate.getConnectionFactory());
//        CacheMessageListener cacheMessageListener = new CacheMessageListener(redisTemplate, redisCaffeineCacheManager);
//        redisMessageListenerContainer.addMessageListener(cacheMessageListener, new ChannelTopic(redisCaffeineCacheProperties.getRedis().getTopic()));
//
//        log.debug("[Eurynome] |- Bean [Redis Message Listener Container] Auto Configure.");
//        return redisMessageListenerContainer;
//    }
}
