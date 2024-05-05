/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 *    Author: ZHENGGENGWEI<码匠君>
 *    Contact: <herodotus@aliyun.com>
 *    Blog and source code availability: <https://gitee.com/herodotus/herodotus-cloud>
 */

package cn.herodotus.cloud.gateway.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.List;

/**
 * <p> Description : Gateway配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/6 15:31
 */
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
