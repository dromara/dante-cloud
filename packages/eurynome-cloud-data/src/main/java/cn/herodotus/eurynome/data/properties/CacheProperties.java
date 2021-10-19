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
 * File Name: CacheProperties.java
 * Author: gengwei.zheng
 * Date: 2021/07/14 11:11:14
 */

package cn.herodotus.eurynome.data.properties;

import cn.herodotus.eurynome.assistant.constant.PropertyConstants;
import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * <p>Description: 缓存配置属性 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/14 11:11
 */
@ConfigurationProperties(prefix = PropertyConstants.PROPERTY_PREFIX_CACHE)
public class CacheProperties {

    /**
     * 分布式缓存Redis端是否进行数据脱敏， 默认值，true
     * <p>
     * Hibernate二级缓存中，会基于SQL进行数据缓存。这种缓存以SQL作为key，一方面这个Key会比较长，另一方面SQL明文存入Redis缺少安全性。
     * 通过这个配置，可以设定是否对Hibernate二级缓存的SQL进行脱敏，脱敏后会将SQL转换为MD5值。当然这也会带来一定的性能损耗
     */
    private Boolean desensitization = true;

    /**
     * 退出时是否清理远端缓存，默认值，false
     * <p>
     * 服务退出时，会清理本地以及远端的缓存，为了在集群情况下避免因此导致的缓存击穿问题，默认退出时不清除远端缓存。
     */
    private Boolean clearRemoteOnExit = false;

    /**
     * 是否允许存储空值
     */
    private Boolean allowNullValues = true;

    /**
     * 统一缓存时长，默认：1
     */
    private Long duration = 1L;

    /**
     * 统一缓存时长单位，默认：小时。
     */
    private TimeUnit unit = TimeUnit.HOURS;

    /**
     * Redis缓存TTL设置，默认：1小时，单位小时
     * <p>
     * 使用Duration类型，配置参数形式如下：
     * "?ns" //纳秒
     * "?us" //微秒
     * "?ms" //毫秒
     * "?s" //秒
     * "?m" //分
     * "?h" //小时
     * "?d" //天
     */
    private Duration ttl;

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public void setUnit(TimeUnit unit) {
        this.unit = unit;
    }

    public Duration getTtl() {
        if (ObjectUtils.isEmpty(this.ttl)) {
            this.ttl = convertToDuration(this.duration, this.unit);
        }
        return ttl;
    }

    public Boolean getAllowNullValues() {
        return allowNullValues;
    }

    public void setAllowNullValues(Boolean allowNullValues) {
        this.allowNullValues = allowNullValues;
    }

    public void setTtl(Duration ttl) {
        this.ttl = ttl;
    }

    public Boolean getDesensitization() {
        return desensitization;
    }

    public void setDesensitization(Boolean desensitization) {
        this.desensitization = desensitization;
    }

    public Boolean getClearRemoteOnExit() {
        return clearRemoteOnExit;
    }

    public void setClearRemoteOnExit(Boolean clearRemoteOnExit) {
        this.clearRemoteOnExit = clearRemoteOnExit;
    }

    private Duration convertToDuration(Long duration, TimeUnit timeUnit) {
        switch (timeUnit) {
            case DAYS:
                return Duration.ofDays(duration);
            case HOURS:
                return Duration.ofHours(duration);
            case SECONDS:
                return Duration.ofSeconds(duration);
            default:
                return Duration.ofMinutes(duration);
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("desensitization", desensitization)
                .add("clearRemoteOnExit", clearRemoteOnExit)
                .add("allowNullValues", allowNullValues)
                .add("duration", duration)
                .add("unit", unit)
                .add("ttl", ttl)
                .toString();
    }
}
