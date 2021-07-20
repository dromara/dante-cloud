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
 * File Name: Source.java
 * Author: gengwei.zheng
 * Date: 2021/07/20 19:03:20
 */

package cn.herodotus.eurynome.bpmn.rest.domain.debezium;

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
