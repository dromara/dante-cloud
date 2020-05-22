/*
 * Copyright 2019-2020 the original author or authors.
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
 *
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-component-data
 * File Name: DynamicDataSourceProperties.java
 * Author: gengwei.zheng
 * Date: 2020/5/21 下午2:18
 * LastModified: 2020/5/21 下午2:18
 */

package cn.herodotus.eurynome.component.data.datasource.properties;

import cn.herodotus.eurynome.component.data.datasource.domain.DataSourceMetadata;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: DynamicDataSourceProperties </p>
 *
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/21 14:18
 */
@ConfigurationProperties(prefix = "spring.datasource.dynamic")
public class DataSourceProperties {

    /**
     * 必须设置默认的库,默认master
     */
    private String primary = "master";
    /**
     * 是否启用严格模式,默认不启动. 严格模式下未匹配到数据源直接报错, 非严格模式下则使用默认数据源primary所设置的数据源
     */
    private Boolean strict = false;
    /**
     * 是否使用p6spy输出，默认不输出
     */
    private Boolean p6spy = false;
    /**
     * 是否使用seata,默认不使用
     */
    private Boolean seata = false;
    /**
     * 是否使用 spring actuator 监控检查，默认不检查
     */
    private boolean health = false;

    /**
     * 数据源定义
     */
    private Map<String, DataSourceMetadata> metadatas = new LinkedHashMap<>();

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public Boolean getStrict() {
        return strict;
    }

    public void setStrict(Boolean strict) {
        this.strict = strict;
    }

    public Boolean getP6spy() {
        return p6spy;
    }

    public void setP6spy(Boolean p6spy) {
        this.p6spy = p6spy;
    }

    public Boolean getSeata() {
        return seata;
    }

    public void setSeata(Boolean seata) {
        this.seata = seata;
    }

    public boolean isHealth() {
        return health;
    }

    public void setHealth(boolean health) {
        this.health = health;
    }

    public Map<String, DataSourceMetadata> getMetadatas() {
        return metadatas;
    }

    public void setMetadatas(Map<String, DataSourceMetadata> metadatas) {
        this.metadatas = metadatas;
    }
}
