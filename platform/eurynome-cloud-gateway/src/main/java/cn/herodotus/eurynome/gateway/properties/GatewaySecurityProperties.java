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
 * Module Name: eurynome-cloud-gateway
 * File Name: GatewaySecurityProperties.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

package cn.herodotus.eurynome.gateway.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.util.List;

/**
 * <p> Description : Gateway配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/6 15:31
 */
@Configuration
@ConfigurationProperties(prefix = "herodotus.gateway")
public class GatewaySecurityProperties {

    private List<String> whiteList;

    private Trace trace = new Trace();

    public List<String> getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(List<String> whiteList) {
        this.whiteList = whiteList;
    }

    public Trace getTrace() {
        return trace;
    }

    public void setTrace(Trace trace) {
        this.trace = trace;
    }

    public static class Trace implements Serializable {
        /**
         * Trace key 在Redis中有效时间，单位秒, 默认5分钟
         */
        private long expired = 60 * 5;
        /**
         * Trace key 在Redis中效时间还是剩余多长时间，就需要进行更新，单位秒, 默认1分钟
         * 即，如果Trace key在Redis中过期时间小于60秒，那么就重新创建Trace key
         */
        private long threshold = 60;

        public long getExpired() {
            return expired;
        }

        public void setExpired(long expired) {
            this.expired = expired;
        }

        public long getThreshold() {
            return threshold;
        }

        public void setThreshold(long threshold) {
            this.threshold = threshold;
        }
    }
}
