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
 * File Name: PlatformApplicationProperties.java
 * Author: gengwei.zheng
 * Date: 2019/11/26 上午10:50
 * LastModified: 2019/11/22 上午10:40
 */

package cn.herodotus.eurynome.rest.properties;

import cn.herodotus.eurynome.rest.enums.Architecture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

/** 
 * <p>Description: 平台服务相关配置 </p>
 * 
 * @author : gengwei.zheng
 * @date : 2019/11/17 15:22
 */
@Slf4j
@ConfigurationProperties(prefix = "herodotus.platform.application")
public class ApplicationProperties {

    public ApplicationProperties() {
        log.info("[Eurynome] |- Properties [Application] is Enabled.");
    }

    private Architecture architecture = Architecture.MICROSERVICE;

    public Architecture getArchitecture() {
        return architecture;
    }

    public void setArchitecture(Architecture architecture) {
        this.architecture = architecture;
    }
}
