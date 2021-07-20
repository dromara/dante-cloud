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
 * File Name: Payload.java
 * Author: gengwei.zheng
 * Date: 2021/07/20 19:03:20
 */

package cn.herodotus.eurynome.bpmn.rest.domain.debezium;

import cn.herodotus.eurynome.bpmn.rest.domain.base.BaseEntity;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Description: Debezium事件响应对应实体Payload </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/20 19:01
 */
public class Payload<T extends BaseEntity> implements Serializable {

    private T before;

    private T after;

    private Source source;

    @JSONField(name = "op")
    @JsonProperty("op")
    private String operation;

    @JSONField(name = "ts_ms")
    @JsonProperty("ts_ms")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timestamp;

    private String transaction;

    public T getBefore() {
        return before;
    }

    public void setBefore(T before) {
        this.before = before;
    }

    public T getAfter() {
        return after;
    }

    public void setAfter(T after) {
        this.after = after;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("before", before)
                .add("after", after)
                .add("source", source)
                .add("operation", operation)
                .add("timestamp", timestamp)
                .add("transaction", transaction)
                .toString();
    }
}
