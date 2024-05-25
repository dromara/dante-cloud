/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Dante Cloud.
 *
 * Dante Cloud is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Dante Cloud is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.cn>.
 */

package cn.herodotus.dante.bpmn.logic.processor;

import cn.herodotus.dante.bpmn.logic.domain.base.BaseEntity;
import cn.herodotus.dante.bpmn.logic.domain.debezium.Message;
import cn.herodotus.dante.bpmn.logic.domain.enums.DebeziumEvent;
import cn.herodotus.engine.assistant.core.json.jackson2.utils.Jackson2Utils;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
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

    protected boolean execute(String body, Class<T> clazz) {
        Message<T> response = this.convert(body, clazz);
        if (ObjectUtils.isNotEmpty(response)) {
            DebeziumEvent event = this.parseEvent(response);
            if (ObjectUtils.isNotEmpty(event)) {
                this.operate(event, response);

                log.info("[Herodotus] |- Sync data base on Debezium for  [{}] succeed!", event.name());
                return true;
            }
        }

        log.info("[Herodotus] |- Sync data base on Debezium for [{}] failed!", body);
        return false;
    }

    private Message<T> convert(String body, Class<T> clazz) {
        if (StringUtils.isNotBlank(body)) {

            TypeFactory typeFactory = Jackson2Utils.getTypeFactory();
            JavaType javaType = typeFactory.constructParametricType(Message.class, clazz);
            Message<T> response = Jackson2Utils.toObject(body, javaType);

            if (ObjectUtils.isNotEmpty(response)) {
                log.debug("[Herodotus] |- Convert Object is : [{}]", response);
                return response;
            }
        }
        log.error("[Herodotus] |- JSON parse the string body error!");
        return null;
    }

    private DebeziumEvent parseEvent(Message<T> response) {
        String action = response.getPayload().getOperation();
        DebeziumEvent event = DebeziumEvent.getDebeziumEvent(action);
        if (ObjectUtils.isNotEmpty(event)) {
            log.debug("[Herodotus] |- The  Debezium event is : [{}]", event.name());
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
