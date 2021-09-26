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
 * File Name: StampProperties.java
 * Author: gengwei.zheng
 * Date: 2021/08/26 18:56:26
 */

package cn.herodotus.eurynome.data.properties;

import cn.herodotus.eurynome.common.constant.magic.PropertyConstants;
import com.google.common.base.MoreObjects;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.time.Duration;

/**
 * <p>Description: 跟踪标记配置属性 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/26 18:56
 */
@ConfigurationProperties(prefix = PropertyConstants.PROPERTY_PREFIX_STAMP)
public class StampProperties {

    private Idempotent idempotent = new Idempotent();
    private AccessLimited accessLimited = new AccessLimited();

    public Idempotent getIdempotent() {
        return idempotent;
    }

    public void setIdempotent(Idempotent idempotent) {
        this.idempotent = idempotent;
    }

    public AccessLimited getAccessLimited() {
        return accessLimited;
    }

    public void setAccessLimited(AccessLimited accessLimited) {
        this.accessLimited = accessLimited;
    }

    public static class Idempotent implements Serializable {

        /**
         * 幂等签章缓存默认过期时间，以防Token删除失败后，缓存数据始终存在影响使用，默认：30秒
         */
        private Duration expire = Duration.ofSeconds(30);

        public Duration getExpire() {
            return expire;
        }

        public void setExpire(Duration expire) {
            this.expire = expire;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("expire", expire)
                    .toString();
        }
    }

    public static class AccessLimited implements Serializable {

        /**
         * 单位时间内同一个接口可以访问的次数，默认10次
         */
        private int maxTimes = 10;

        /**
         * 持续时间，即在多长时间内，限制访问多少次。默认为 30秒。
         */
        private Duration expire = Duration.ofSeconds(30);

        public int getMaxTimes() {
            return maxTimes;
        }

        public void setMaxTimes(int maxTimes) {
            this.maxTimes = maxTimes;
        }

        public Duration getExpire() {
            return expire;
        }

        public void setExpire(Duration expire) {
            this.expire = expire;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("maxTimes", maxTimes)
                    .add("expire", expire)
                    .toString();
        }
    }
}
