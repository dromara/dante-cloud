package cn.herodotus.eurynome.integration.content.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: 阿里云OSS配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/15 11:23
 */
@ConfigurationProperties(prefix = "herodotus.integration.content.aliyun.oss")
public class AliyunOssProperties extends AliyunProperties{

    private String endpoint;
    private String bucketName;
    private String baseUrl;

    private String[] pictureTypes;
    private String[] videoTypes;
    private String[] voiceTypes;
    private String[] appTypes;
    private String[] docTypes;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String[] getPictureTypes() {
        return pictureTypes;
    }

    public void setPictureTypes(String[] pictureTypes) {
        this.pictureTypes = pictureTypes;
    }

    public String[] getVideoTypes() {
        return videoTypes;
    }

    public void setVideoTypes(String[] videoTypes) {
        this.videoTypes = videoTypes;
    }

    public String[] getVoiceTypes() {
        return voiceTypes;
    }

    public void setVoiceTypes(String[] voiceTypes) {
        this.voiceTypes = voiceTypes;
    }

    public String[] getAppTypes() {
        return appTypes;
    }

    public void setAppTypes(String[] appTypes) {
        this.appTypes = appTypes;
    }

    public String[] getDocTypes() {
        return docTypes;
    }

    public void setDocTypes(String[] docTypes) {
        this.docTypes = docTypes;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
