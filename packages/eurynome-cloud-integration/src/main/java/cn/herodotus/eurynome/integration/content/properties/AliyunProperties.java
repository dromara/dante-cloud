package cn.herodotus.eurynome.integration.content.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: 阿里云通用属性 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/15 11:37
 */
@ConfigurationProperties(prefix = "herodotus.integration.content.aliyun")
public class AliyunProperties {

    private String accessKeyId;
    private String accessKeySecret;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }
}
