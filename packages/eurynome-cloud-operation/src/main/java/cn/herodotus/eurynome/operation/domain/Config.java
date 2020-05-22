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
 * Module Name: eurynome-cloud-operation
 * File Name: Config.java
 * Author: gengwei.zheng
 * Date: 2020/5/22 下午5:00
 * LastModified: 2020/5/19 下午3:31
 */

package cn.herodotus.eurynome.operation.domain;

import java.io.Serializable;

/**
 * <p> Description : Config </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/4 14:42
 */
public class Config implements Serializable {

    public static final String DEFAULT_GROUP = "DEFAULT_GROUP";

    /**
     * the data id of extended configuration.
     */
    private String dataId;

    /**
     * the group of extended configuration, the default value is DEFAULT_GROUP.
     */
    private String group = "DEFAULT_GROUP";

    private String content;

    public Config() {
    }

    public Config(String dataId) {
        this.dataId = dataId;
    }

    public Config(String dataId, String group) {
        this.dataId = dataId;
        this.group = group;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
