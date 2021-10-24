/*
 * Copyright (c) 2019-2021 Gengwei Zheng (herodotus@aliyun.com)
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
 * File Name: RedissonConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/10/24 21:05:24
 */

package cn.herodotus.eurynome.data.configuration;

import cn.herodotus.eurynome.assistant.annotation.conditional.ConditionalOnRedissonEnabled;
import cn.herodotus.eurynome.assistant.constant.SymbolConstants;
import cn.herodotus.eurynome.assistant.utils.ResourceUtils;
import cn.herodotus.eurynome.data.properties.RedissonProperties;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

/**
 * <p>Description: Redisson 配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/24 21:05
 */
@Configuration
@ConditionalOnRedissonEnabled
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonConfiguration {

    private static final Logger log = LoggerFactory.getLogger(RedissonConfiguration.class);

    @Autowired
    private RedissonProperties redissonProperties;

    @PostConstruct
    public void postConstruct() {
        log.debug("[Herodotus] |- Plugin [Herodotus Redisson] Auto Configure.");
    }

    private File readConfigFile() {
        String configFile = redissonProperties.getConfig();
        if (StringUtils.isNotBlank(configFile)) {
            try {
                return ResourceUtils.getFile(configFile);
            } catch (IOException e) {
                log.error("[Herodotus] |- Can not found the config file [{}], check whether the format is correct.", configFile);
            }
        }

        return null;
    }

    private Config getConfigByFile() {
        try {
            File configFile = readConfigFile();
            if (ObjectUtils.isNotEmpty(configFile)) {
                if (StringUtils.endsWithIgnoreCase(configFile.getName(), SymbolConstants.SUFFIX_YAML)) {
                    return Config.fromYAML(configFile);
                }

                if (StringUtils.endsWithIgnoreCase(configFile.getName(), SymbolConstants.SUFFIX_JSON)) {
                    return Config.fromJSON(configFile);
                }
            }
        } catch (IOException e) {
            log.error("[Herodotus] |- Redisson loading the config file error!");
        }

        return null;
    }

    private Config getDefaultConfig() {
        Config config = new Config();

        switch (redissonProperties.getMode()) {
            case CLUSTER:
                ClusterServersConfig clusterServersConfig = config.useClusterServers();
                BeanUtils.copyProperties(redissonProperties.getClusterServersConfig(), clusterServersConfig, ClusterServersConfig.class);
                redissonProperties.getClusterServersConfig().getNodeAddresses().parallelStream().forEach(clusterServersConfig::addNodeAddress);
                break;
            case SENTINEL:
                SentinelServersConfig sentinelServersConfig = config.useSentinelServers();
                BeanUtils.copyProperties(redissonProperties.getSentinelServersConfig(), sentinelServersConfig, SentinelServersConfig.class);
                redissonProperties.getSentinelServersConfig().getSentinelAddresses().parallelStream().forEach(sentinelServersConfig::addSentinelAddress);
                break;
            default:
                SingleServerConfig singleServerConfig = config.useSingleServer();
                BeanUtils.copyProperties(redissonProperties.getSingleServerConfig(), singleServerConfig, SingleServerConfig.class);
                break;
        }

        config.setCodec(new JsonJacksonCodec());
        //默认情况下，看门狗的检查锁的超时时间是30秒钟
        config.setLockWatchdogTimeout(1000 * 30);
        return config;
    }

    @Bean
    public RedissonClient redissonClient() {
        Config config = getConfigByFile();
        if (ObjectUtils.isEmpty(config)) {
            config = getDefaultConfig();
        }

        RedissonClient redissonClient = Redisson.create(config);

        log.trace("[Herodotus] |- Bean [Redisson Client] Auto Configure.");

        return redissonClient;
    }
}