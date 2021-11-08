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
 * Module Name: eurynome-integration-oss
 * File Name: MinioProperties.java
 * Author: gengwei.zheng
 * Date: 2021/11/08 20:49:08
 */

package cn.herodotus.eurynome.integration.oss.properties;

import cn.herodotus.eurynome.assistant.constant.PropertyConstants;
import com.google.common.base.MoreObjects;
import org.apache.commons.pool2.impl.BaseObjectPoolConfig;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * <p>Description: Minio 配置参数 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/11/8 10:31
 */
@ConfigurationProperties(prefix = PropertyConstants.PROPERTY_OSS_MINIO)
public class MinioProperties {

    /**
     * Minio Server URL
     */
    private String endpoint;

    /**
     * Minio Server accessKey
     */
    private String accessKey;

    /**
     * Minio Server secretKey
     */
    private String secretKey;

    private String bucketNamePrefix;

    /**
     * 文件名中时间标识内容格式。
     */
    private String timestampFormat = "yyyy-MM-dd";

    private Pool pool = new Pool();

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucketNamePrefix() {
        return bucketNamePrefix;
    }

    public void setBucketNamePrefix(String bucketNamePrefix) {
        this.bucketNamePrefix = bucketNamePrefix;
    }

    public String getTimestampFormat() {
        return timestampFormat;
    }

    public void setTimestampFormat(String timestampFormat) {
        this.timestampFormat = timestampFormat;
    }

    public Pool getPool() {
        return pool;
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    public static class Pool {

        /**
         * 最大总数
         */
        private int maxTotal = GenericObjectPoolConfig.DEFAULT_MAX_TOTAL;

        /**
         * 最大空闲数
         */
        private int maxIdle = GenericObjectPoolConfig.DEFAULT_MAX_IDLE;

        /**
         * 最小空闲数
         */
        private int minIdle = GenericObjectPoolConfig.DEFAULT_MIN_IDLE;

        /**
         * 最大等待时间，支持 Duration 表达式
         */
        private Duration maxWait = Duration.ofMillis(BaseObjectPoolConfig.DEFAULT_MAX_WAIT_MILLIS);
        private boolean blockWhenExhausted;

        public int getMaxTotal() {
            return maxTotal;
        }

        public void setMaxTotal(int maxTotal) {
            this.maxTotal = maxTotal;
        }

        public int getMaxIdle() {
            return maxIdle;
        }

        public void setMaxIdle(int maxIdle) {
            this.maxIdle = maxIdle;
        }

        public int getMinIdle() {
            return minIdle;
        }

        public void setMinIdle(int minIdle) {
            this.minIdle = minIdle;
        }

        public Duration getMaxWait() {
            return maxWait;
        }

        public void setMaxWait(Duration maxWait) {
            this.maxWait = maxWait;
        }

        public boolean isBlockWhenExhausted() {
            return blockWhenExhausted;
        }

        public void setBlockWhenExhausted(boolean blockWhenExhausted) {
            this.blockWhenExhausted = blockWhenExhausted;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("maxTotal", maxTotal)
                    .add("maxIdle", maxIdle)
                    .add("minIdle", minIdle)
                    .add("maxWaitMillis", maxWait)
                    .add("blockWhenExhausted", blockWhenExhausted)
                    .toString();
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("endpoint", endpoint)
                .add("accessKey", accessKey)
                .add("secretKey", secretKey)
                .add("bucketNamePrefix", bucketNamePrefix)
                .add("timestampFormat", timestampFormat)
                .add("pool", pool)
                .toString();
    }
}
