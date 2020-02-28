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

package cn.herodotus.eurynome.component.security.properties;

import cn.herodotus.eurynome.component.common.enums.Architecture;
import cn.herodotus.eurynome.component.common.utils.TreeUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** 
 * <p>Description: 平台服务相关配置 </p>
 * 
 * @author : gengwei.zheng
 * @date : 2019/11/17 15:22
 */
@ConfigurationProperties(prefix = "eurynome.platform.application")
public class ApplicationProperties {

    private String serviceDisplayName;
    private String authorityTreeRoot;
    private Architecture architecture = Architecture.MICROSERVICE;
    private RequestMapping requestMapping = new RequestMapping();

    public String getServiceDisplayName() {
        return serviceDisplayName;
    }

    public void setServiceDisplayName(String serviceDisplayName) {
        this.serviceDisplayName = serviceDisplayName;
    }

    public String getAuthorityTreeRoot() {
        if (StringUtils.isEmpty(authorityTreeRoot)) {
            setAuthorityTreeRoot(TreeUtils.DEFAULT_ROOT_ID);
        }
        return authorityTreeRoot;
    }

    public RequestMapping getRequestMapping() {
        return requestMapping;
    }

    public void setRequestMapping(RequestMapping requestMapping) {
        this.requestMapping = requestMapping;
    }

    public void setAuthorityTreeRoot(String authorityTreeRoot) {
        this.authorityTreeRoot = authorityTreeRoot;
    }

    public Architecture getArchitecture() {
        return architecture;
    }

    public void setArchitecture(Architecture architecture) {
        this.architecture = architecture;
    }

    public static class RequestMapping implements Serializable {
        private boolean registerRequestMapping = false;
        private List<String> scanGroupIds;

        public boolean isRegisterRequestMapping() {
            return registerRequestMapping;
        }

        public void setRegisterRequestMapping(boolean registerRequestMapping) {
            this.registerRequestMapping = registerRequestMapping;
        }

        public void setScanGroupIds(List<String> scanGroupIds) {
            this.scanGroupIds = scanGroupIds;
        }

        public List<String> getScanGroupIds() {
            String defaultGroupId = "cn.herodotus.eurynome";

            if (CollectionUtils.isEmpty(this.scanGroupIds)) {
                this.scanGroupIds = new ArrayList<>();
                this.scanGroupIds.add(defaultGroupId);
            } else if (!this.scanGroupIds.contains(defaultGroupId)) {
                this.scanGroupIds.add(defaultGroupId);
            }

            return scanGroupIds;
        }
    }

}
