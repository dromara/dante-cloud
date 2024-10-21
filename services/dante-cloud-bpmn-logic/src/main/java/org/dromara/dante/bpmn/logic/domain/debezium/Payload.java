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

package org.dromara.dante.bpmn.logic.domain.debezium;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import org.dromara.dante.bpmn.logic.domain.base.BaseEntity;

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
