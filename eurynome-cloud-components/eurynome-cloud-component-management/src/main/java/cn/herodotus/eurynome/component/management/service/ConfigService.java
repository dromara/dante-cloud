package cn.herodotus.eurynome.component.management.service;

import cn.herodotus.eurynome.component.management.nacos.NacosConfig;
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
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/3 12:48
 */
@Slf4j
@Service
public class ConfigService {

    private static final String CONFIG_FILE_FOLDER = "configs";
    private static final String CONFIG_FILE_RESOURCES = "classpath:" + CONFIG_FILE_FOLDER + "/**/*.yml";

    @Autowired
    private NacosConfig nacosConfig;

    @Async
    public void initialize() {
        log.debug("[Herodotus] |- Begin initialize Config Files.");

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resolver.getResources(CONFIG_FILE_RESOURCES);
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
    }

    private String getGroupName(Resource resource) throws IOException {
        if (ObjectUtils.isEmpty(resource)) {
            return NacosConfig.DEFAULT_GROUP;
        }

        String group = resource.getFile().getParentFile().getName();
        if (StringUtils.isNotBlank(group) && !StringUtils.equals(group, CONFIG_FILE_FOLDER)) {
            return group;
        } else {
            return NacosConfig.DEFAULT_GROUP;
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
