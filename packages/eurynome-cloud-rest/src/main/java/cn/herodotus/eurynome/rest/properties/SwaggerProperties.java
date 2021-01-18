/*
 * Copyright 2019-2019 the original author or authors.
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
 * Project Name: luban-cloud
 * Module Name: luban-cloud-component-data
 * File Name: SwaggerProperties.java
 * Author: gengwei.zheng
 * Date: 2019/11/26 上午10:51
 * LastModified: 2019/11/26 上午10:51
 */

package cn.herodotus.eurynome.rest.properties;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p> Description : Swagger相关配置信息 </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/26 10:51
 */
@Slf4j
@ConfigurationProperties(prefix = "herodotus.platform.swagger")
public class SwaggerProperties {

    /**
     * 是否启用swagger,生产环境建议关闭
     */
    private boolean enabled;

    /**
     * 文档标题
     */
    private String title;
    /**
     * 文档描述
     */
    private String description;

    private String version;

    public SwaggerProperties() {
        log.debug("[Eurynome] |- Properties [Swagger] is Enabled.");
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
