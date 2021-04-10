package cn.herodotus.eurynome.integration.facilities.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>File: NacosProperties.java </p>
 *
 * <p>Description: Nacos 配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/10 14:05
 */
@ConfigurationProperties(prefix = "herodotus.integration.facilities.nacos")
public class NacosProperties {

    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
