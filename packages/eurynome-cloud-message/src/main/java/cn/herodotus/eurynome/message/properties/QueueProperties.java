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
 * Module Name: eurynome-cloud-message
 * File Name: QueueProperties.java
 * Author: gengwei.zheng
 * Date: 2021/10/24 21:09:24
 */

package cn.herodotus.eurynome.message.properties;

import cn.herodotus.eurynome.assistant.constant.PropertyConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: 消息队列配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/8 10:41
 */
@ConfigurationProperties(prefix = PropertyConstants.PROPERTY_MANAGEMENT_QUEUE)
public class QueueProperties {

    private Kafka kafka = new Kafka();

    public Kafka getKafka() {
        return kafka;
    }

    public void setKafka(Kafka kafka) {
        this.kafka = kafka;
    }

    public static class Kafka {

        /**
         * Kakfa监听是否自动启动
         */
        private Boolean enabled = false;

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }
    }
}
