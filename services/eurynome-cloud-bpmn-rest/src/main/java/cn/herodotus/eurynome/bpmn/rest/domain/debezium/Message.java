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
 * File Name: Message.java
 * Author: gengwei.zheng
 * Date: 2021/07/20 19:05:20
 */

package cn.herodotus.eurynome.bpmn.rest.domain.debezium;

import cn.herodotus.eurynome.bpmn.rest.domain.base.BaseEntity;
import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * <p>Description: Debezium事件响应对应实体Message </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/20 19:03
 */
public class Message<T extends BaseEntity> implements Serializable {

    private Field schema;
    private Payload<T> payload;

    public Field getSchema() {
        return schema;
    }

    public void setSchema(Field schema) {
        this.schema = schema;
    }

    public Payload<T> getPayload() {
        return payload;
    }

    public void setPayload(Payload<T> payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("schema", schema)
                .add("payload", payload)
                .toString();
    }
}
