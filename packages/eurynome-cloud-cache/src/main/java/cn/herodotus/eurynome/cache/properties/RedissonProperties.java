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
 * Module Name: eurynome-cloud-cache
 * File Name: RedissonProperties.java
 * Author: gengwei.zheng
 * Date: 2021/10/24 21:34:24
 */

package cn.herodotus.eurynome.cache.properties;

import cn.herodotus.eurynome.assistant.constant.PropertyConstants;
import cn.herodotus.eurynome.assistant.constant.SymbolConstants;
import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: Redisson 配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/24 21:05
 */
@ConfigurationProperties(prefix = PropertyConstants.PROPERTY_REDIS_REDISSON)
public class RedissonProperties {

    /**
     * Redisson 使用模式
     */
    public enum Mode {
        /**
         * 单机
         */
        SINGLE,
        /**
         * 哨兵
         */
        SENTINEL,
        /**
         * 集群
         */
        CLUSTER
    }

    /**
     * 是否开启 Redisson
     */
    private Boolean enabled = false;

    /**
     * Redis 模式
     */
    private Mode mode = Mode.SINGLE;

    /**
     * 配置文件路径
     */
    private String config;

    /**
     * 单体配置
     */
    private SingleServerConfig singleServerConfig;
    /**
     * 集群配置
     */
    private ClusterServersConfig clusterServersConfig;

    /**
     * 哨兵配置
     */
    private SentinelServersConfig sentinelServersConfig;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public SingleServerConfig getSingleServerConfig() {
        return singleServerConfig;
    }

    public void setSingleServerConfig(SingleServerConfig singleServerConfig) {
        this.singleServerConfig = singleServerConfig;
    }

    public ClusterServersConfig getClusterServersConfig() {
        return clusterServersConfig;
    }

    public void setClusterServersConfig(ClusterServersConfig clusterServersConfig) {
        this.clusterServersConfig = clusterServersConfig;
    }

    public SentinelServersConfig getSentinelServersConfig() {
        return sentinelServersConfig;
    }

    public void setSentinelServersConfig(SentinelServersConfig sentinelServersConfig) {
        this.sentinelServersConfig = sentinelServersConfig;
    }

    public boolean isExternalConfig() {
        return StringUtils.isNotBlank(this.getConfig());
    }

    public boolean isYamlConfig() {
        if (this.isExternalConfig()) {
            return StringUtils.endsWithIgnoreCase(this.getConfig(), SymbolConstants.SUFFIX_YAML);
        } else {
            return false;
        }
    }

    public boolean isJsonConfig() {
        if (this.isExternalConfig()) {
            return StringUtils.endsWithIgnoreCase(this.getConfig(), SymbolConstants.SUFFIX_JSON);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("enabled", enabled)
                .add("mode", mode)
                .add("config", config)
                .toString();
    }
}