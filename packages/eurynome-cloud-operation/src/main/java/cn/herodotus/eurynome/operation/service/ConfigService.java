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
 * File Name: ConfigService.java
 * Author: gengwei.zheng
 * Date: 2020/5/22 下午5:00
 * LastModified: 2020/5/22 下午4:37
 */

package cn.herodotus.eurynome.operation.service;

import cn.herodotus.eurynome.common.constants.SymbolConstants;
import cn.herodotus.eurynome.operation.domain.Config;
import cn.herodotus.eurynome.operation.nacos.HerodotusNacosConfig;
import com.alibaba.nacos.client.config.impl.LocalConfigInfoProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * <p> Description : ConfigService </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/3 12:48
 */
@Slf4j
@Service
public class ConfigService {

    private static final String CONFIG_RESOURCES_PATH_SUFFIX = "/**/*.yaml";

    @Autowired
    private HerodotusNacosConfig herodotusNacosConfig;

    @Async
    public void initialize() {
        log.debug("[Herodotus] |- Begin initialize Config Files.");

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resolver.getResources(getConfigResourcesPath());
            for (Resource resource : resources) {

                String configFileName = resource.getFile().getName();
                log.debug("[Herodotus] |- Found Config File: [{}]", configFileName);

                if (!isValidFileSize(resource)) {
                    log.error("[Herodotus] |- Config File: [{}] is too large, exceed Nacos config max limitation！", configFileName);
                    continue;
                }

                String groupName = getGroupName(resource);
                String content = FileUtils.readFileToString(resource.getFile(), StandardCharsets.UTF_8);

                boolean isPublishOk  = herodotusNacosConfig.publishOrUpdateConfig(configFileName, groupName, content);

                log.debug("[Herodotus] |- Config File: [{}] is publish {}！", configFileName, isPublishOk ? "success" : "failed");
            }
        } catch (IOException e) {
            log.error("[Herodotus] |- ConfigService read file failed", e);
        }

        log.debug("[Herodotus] |- Initialize Config Files Finished.");
        LocalConfigInfoProcessor.cleanAllSnapshot();
    }

    private String getPrefix() {
        String prefix = herodotusNacosConfig.getManagementProperties().getConfigCenter().getPrefix();
        return StringUtils.removeEnd(prefix, SymbolConstants.FORWARD_SLASH);
    }

    private String getConfigResourcesPath() {
        String prefix = getPrefix();
        return prefix + CONFIG_RESOURCES_PATH_SUFFIX;
    }

    private String getConfigResourcesRoot() {
        String prefix = getPrefix();
        if (StringUtils.contains(prefix, SymbolConstants.FORWARD_SLASH)) {
            return StringUtils.substringAfterLast(prefix, SymbolConstants.FORWARD_SLASH);
        } else {
            return StringUtils.substringAfterLast(prefix, SymbolConstants.COLON);
        }
    }

    private String getGroupName(Resource resource) throws IOException {
        if (ObjectUtils.isEmpty(resource)) {
            return Config.DEFAULT_GROUP;
        }

        String group = resource.getFile().getParentFile().getName();
        if (StringUtils.isNotBlank(group) && !StringUtils.equals(group, getConfigResourcesRoot())) {
            return group;
        } else {
            return Config.DEFAULT_GROUP;
        }
    }

    private boolean isValidFileSize(Resource resource) throws IOException {
        if (ObjectUtils.isEmpty(resource)) {
            return false;
        }

        long fileSize = resource.getFile().length();
        return fileSize <= HerodotusNacosConfig.MAX_CONFIG_CONTENT_SIZE;
    }
}
