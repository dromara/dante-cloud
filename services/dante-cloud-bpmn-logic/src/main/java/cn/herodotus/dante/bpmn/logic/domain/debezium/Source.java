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

package cn.herodotus.dante.bpmn.logic.domain.debezium;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * <p>Description: Debezium事件响应对应实体Source </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/7/20 19:02
 */
public class Source implements Serializable {

    private String version;

    private String connector;

    private String name;
    @JSONField(name = "ts_ms")
    @JsonProperty("ts_ms")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timestamp;

    private String snapshot;

    @JSONField(name = "db")
    @JsonProperty("db")
    private String databaseName;

    private String[] sequence;

    @JSONField(name = "schema")
    @JsonProperty("schema")
    private String schemaName;

    @JSONField(name = "table")
    @JsonProperty("table")
    private String tableName;

    @JSONField(name = "txId")
    @JsonProperty("txId")
    private Integer transactionId;

    @JSONField(name = "lsn")
    @JsonProperty("lsn")
    private BigInteger LogSequenceNumber;

    private String xmin;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getConnector() {
        return connector;
    }

    public void setConnector(String connector) {
        this.connector = connector;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public BigInteger getLogSequenceNumber() {
        return LogSequenceNumber;
    }

    public void setLogSequenceNumber(BigInteger logSequenceNumber) {
        LogSequenceNumber = logSequenceNumber;
    }

    public String getXmin() {
        return xmin;
    }

    public void setXmin(String xmin) {
        this.xmin = xmin;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("version", version)
                .add("connector", connector)
                .add("name", name)
                .add("timestamp", timestamp)
                .add("snapshot", snapshot)
                .add("databaseName", databaseName)
                .add("schemaName", schemaName)
                .add("tableName", tableName)
                .add("transactionId", transactionId)
                .add("LogSequenceNumber", LogSequenceNumber)
                .add("xmin", xmin)
                .toString();
    }
}
