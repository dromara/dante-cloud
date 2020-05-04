package cn.herodotus.eurynome.component.management.nacos;

import cn.herodotus.eurynome.component.common.exception.PlatformException;
import cn.herodotus.eurynome.component.management.domain.Config;
import cn.hutool.core.bean.BeanUtil;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/4/30 15:59
 */

@Slf4j
public class NacosConfig extends AbstractNacos {

    /**
     * Nacos SDK中，对配置的Content大小有限制。最大为100K字节
     * {@link com.alibaba.nacos.client.config.NacosConfigService#publishConfig(String, String, String)}
     * 单位：字节
     */
    public static final int MAX_CONFIG_CONTENT_SIZE = 102400;
    public static final String GROUP_SUFFIX = "_GROUP";

    public NacosConfig(String serverAddress) {
        super(serverAddress);
    }

    /**
     * 获取Nacos Config 服务
     *
     * @return ConfigService
     * @throws NacosException Nacos错误
     */
    private ConfigService getConfigService() throws NacosException {
        return getConfigService(getServerAddress());
    }

    private ConfigService getConfigService(String serverAddress) throws NacosException, PlatformException {
        if (StringUtils.isBlank(serverAddress)) {
            throw new PlatformException("can not read spring.cloud.nacos.config.server-addr property");
        }
        return NacosFactory.createConfigService(serverAddress);
    }

    /**
     * 判断ConfigService是否获取正确，以及是否正常运行中
     *
     * @param configService configService
     * @return 是否configService有效
     */
    public boolean isValidConfigService(ConfigService configService) {
        return ObjectUtils.isNotEmpty(configService) && configService.getServerStatus().equals(SERVER_STATUS_UP);
    }

    public boolean publishOrUpdateConfig(String dataId, String content) {
        return publishOrUpdateConfig(dataId, DEFAULT_GROUP, content);
    }

    public boolean publishOrUpdateConfig(Config config) {
        if (BeanUtil.isEmpty(config)) {
            return false;
        }
        return publishOrUpdateConfig(config.getDataId(), config.getGroup(), config.getContent());
    }

    /**
     * 创建和修改配置时使用的同一个发布接口，当配置不存在时会创建配置，当配置已存在时会更新配置。
     *
     * @param dataId  配置 ID，采用类似 package.class（如 com.taobao.tc.refund.log.level）的命名规则保证全局唯一性。建议根据配置的业务含义来定义 class 部分。全部字符均为小写。只允许英文字符和 4 种特殊字符（“.”、“:”、“-”、“_”），不超过 256 字节
     * @param group   配置分组，建议填写产品名:模块名（如 Nacos:Test）来保证唯一性。只允许英文字符和 4 种特殊字符（“.”、“:”、“-”、“_”），不超过 128 字节。
     * @param content 配置内容，不超过 100K 字节。
     * @return 是否发布成功
     */
    public boolean publishOrUpdateConfig(String dataId, String group, String content) {
        try {
            ConfigService configService = getConfigService();
            if (isValidConfigService(configService)) {
                return configService.publishConfig(dataId, groupNameRegulate(group), content);
            } else {
                return false;
            }
        } catch (NacosException e) {
            log.error("[Herodotus] |- NacosConfig Publish Or Update Config FAILED!", e);
        } catch (PlatformException e) {
            log.error("[Herodotus] |- Nacos Server Address is not correct!", e);
        }

        return false;
    }

    public boolean removeConfig(String dataId) {
        return removeConfig(dataId, DEFAULT_GROUP);
    }

    public boolean removeConfig(Config config) {
        if (BeanUtil.isEmpty(config)) {
            return false;
        }
        return removeConfig(config.getDataId(), config.getGroup());
    }

    public boolean removeConfig(String dataId, String group) {
        try {
            ConfigService configService = getConfigService();
            if (isValidConfigService(configService)) {
                return configService.removeConfig(dataId, groupNameRegulate(group));
            } else {
                return false;
            }
        } catch (NacosException e) {
            log.error("[Herodotus] |- NacosConfig Remove Config FAILED!", e);
        } catch (PlatformException e) {
            log.error("[Herodotus] |- Nacos Server Address is not correct!", e);
        }

        return false;
    }

    public String getConfig(String dataId) {
        return getConfig(dataId, DEFAULT_GROUP);
    }

    public String getConfig(String dataId, String group) {
        return getConfig(dataId, group, DEFAULT_TIMEOUT);
    }

    public String getConfig(String dataId, String group, long timeout) {
        try {
            ConfigService configService = getConfigService();
            if (isValidConfigService(configService)) {
                return configService.getConfig(dataId, groupNameRegulate(group), timeout);
            } else {
                return null;
            }
        } catch (NacosException e) {
            log.error("[Herodotus] |- NacosConfig Get Config FAILED!", e);
        } catch (PlatformException e) {
            log.error("[Herodotus] |- Nacos Server Address is not correct!", e);
        }

        return null;
    }

    private String groupNameRegulate(String group) {
        if (StringUtils.isNotBlank(group)) {
            String resource = StringUtils.upperCase(group) ;
            if (StringUtils.endsWith(resource, GROUP_SUFFIX)) {
                return resource;
            } else {
                return resource + GROUP_SUFFIX;
            }
        }
        return group;
    }

}
