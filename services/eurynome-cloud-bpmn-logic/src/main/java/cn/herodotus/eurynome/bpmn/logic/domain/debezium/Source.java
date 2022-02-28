/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Eurynome Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Eurynome Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/herodotus/eurynome-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/herodotus/eurynome-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.eurynome.bpmn.logic.domain.debezium;

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
