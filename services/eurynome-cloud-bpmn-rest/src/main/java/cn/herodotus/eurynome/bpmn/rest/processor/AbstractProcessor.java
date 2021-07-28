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
 * Module Name: eurynome-cloud-bpmn-rest
 * File Name: AbstractProcessor.java
 * Author: gengwei.zheng
 * Date: 2021/07/20 19:13:20
 */

package cn.herodotus.eurynome.bpmn.rest.processor;

import cn.herodotus.eurynome.bpmn.rest.domain.base.BaseEntity;
import cn.herodotus.eurynome.bpmn.rest.domain.debezium.Message;
import cn.herodotus.eurynome.bpmn.rest.domain.enums.DebeziumEvent;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Description: CRUD 消息抽象处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/20 15:41
 */
public abstract class AbstractProcessor<T extends BaseEntity> {

    private static final Logger log = LoggerFactory.getLogger(AbstractProcessor.class);

    protected boolean execute(String body) {
        Message<T> response = this.convert(body);
        if (ObjectUtils.isNotEmpty(response)) {
            DebeziumEvent event = this.parseEvent(response);
            if (ObjectUtils.isNotEmpty(event)) {
                this.operate(event, response);

                log.info("[Eurynome] |- Sync data base on Debezium for  [{}] succeed!", event.name());
                return true;
            }
        }

        log.info("[Eurynome] |- Sync data base on Debezium for [{}] failed!", body);
        return false;
    }

    private Message<T> convert(String body) {
        if (StringUtils.isNotBlank(body)) {
            Message<T> response = JSON.parseObject(body, new TypeReference<Message<T>>() {
            });
            if (ObjectUtils.isNotEmpty(response)) {
                log.debug("[Eurynome] |- Convert Object is : [{}]", response);
                return response;
            }
        }
        log.error("[Eurynome] |- JSON parse the string body error!");
        return null;
    }

    private DebeziumEvent parseEvent(Message<T> response) {
        String action = response.getPayload().getOperation();
        DebeziumEvent event = DebeziumEvent.getDebeziumEvent(action);
        if (ObjectUtils.isNotEmpty(event)) {
            log.debug("[Eurynome] |- The  Debezium event is : [{}]", event.name());
            return event;
        }

        return null;
    }

    public abstract void delete(T entity);

    public abstract T saveOrUpdate(T entity);

    private void operate(DebeziumEvent event, Message<T> message) {
        switch (event) {
            case DELETE:
                delete(message.getPayload().getBefore());
                break;
            default:
                saveOrUpdate(message.getPayload().getAfter());
                break;
        }
    }
}
