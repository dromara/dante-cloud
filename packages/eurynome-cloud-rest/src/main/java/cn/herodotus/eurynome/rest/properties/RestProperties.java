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
 * Module Name: eurynome-cloud-rest
 * File Name: RestProperties.java
 * Author: gengwei.zheng
 * Date: 2021/08/26 20:40:26
 */

package cn.herodotus.eurynome.rest.properties;

import cn.herodotus.eurynome.common.constant.magic.PropertyConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: RestProperties </p>
 *
 * <p>Description: Rest相关配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/31 16:39
 */
@Slf4j
@ConfigurationProperties(prefix = PropertyConstants.PROPERTY_PREFIX_REST)
public class RestProperties {

    private RequestMapping requestMapping = new RequestMapping();
    private RestTemplate restTemplate = new RestTemplate();

    public RestProperties() {
        log.debug("[Herodotus] |- Properties [Rest] is Enabled.");
    }

    public RequestMapping getRequestMapping() {
        return requestMapping;
    }

    public void setRequestMapping(RequestMapping requestMapping) {
        this.requestMapping = requestMapping;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public static class RequestMapping implements Serializable {
        /**
         * 指定扫描的命名空间。未指定的命名空间中，即使包含RequestMapping，也不会被添加进来。
         */
        private List<String> scanGroupIds;
        /**
         * Spring 中会包含 Controller和 RestController，
         * 如果该配置设置为True，那么就只扫描RestController
         * 如果该配置设置为False，那么Controller和 RestController斗扫描。
         */
        private boolean justScanRestController = false;

        public void setScanGroupIds(List<String> scanGroupIds) {
            this.scanGroupIds = scanGroupIds;
        }

        public List<String> getScanGroupIds() {
            String defaultGroupId = "cn.herodotus.eurynome";

            if (CollectionUtils.isEmpty(this.scanGroupIds)) {
                this.scanGroupIds = new ArrayList<>();
                this.scanGroupIds.add(defaultGroupId);
            } else if (!this.scanGroupIds.contains(defaultGroupId)) {
                this.scanGroupIds.add(defaultGroupId);
            }

            return scanGroupIds;
        }

        public boolean isJustScanRestController() {
            return justScanRestController;
        }

        public void setJustScanRestController(boolean justScanRestController) {
            this.justScanRestController = justScanRestController;
        }
    }

    public static class RestTemplate implements Serializable {
        /**
         * RestTemplate 读取超时5秒,默认无限限制,单位：毫秒
         */
        private int readTimeout = 15000;
        /**
         * 连接超时15秒，默认无限制，单位：毫秒
         */
        private int connectTimeout = 15000;

        public int getReadTimeout() {
            return readTimeout;
        }

        public void setReadTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
        }

        public int getConnectTimeout() {
            return connectTimeout;
        }

        public void setConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
        }
    }
}
