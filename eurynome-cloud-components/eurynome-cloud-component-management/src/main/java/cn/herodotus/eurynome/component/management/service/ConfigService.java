package cn.herodotus.eurynome.component.management.service;

import cn.herodotus.eurynome.common.constants.SymbolConstants;
import cn.herodotus.eurynome.component.management.domain.Config;
import cn.herodotus.eurynome.component.management.nacos.NacosConfig;
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
    private NacosConfig nacosConfig;

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

                boolean isPublishOk  = nacosConfig.publishOrUpdateConfig(configFileName, groupName, content);

                log.debug("[Herodotus] |- Config File: [{}] is publish {}！", configFileName, isPublishOk ? "success" : "failed");
            }
        } catch (IOException e) {
            log.error("[Herodotus] |- ConfigService read file failed", e);
        }

        log.debug("[Herodotus] |- Initialize Config Files Finished.");
        LocalConfigInfoProcessor.cleanAllSnapshot();
    }

    private String getPrefix() {
        String prefix = nacosConfig.getManagementProperties().getConfigCenter().getPrefix();
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
        return fileSize <= NacosConfig.MAX_CONFIG_CONTENT_SIZE;
    }
}
