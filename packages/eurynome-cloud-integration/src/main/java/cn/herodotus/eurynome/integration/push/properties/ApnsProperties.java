package cn.herodotus.eurynome.integration.push.properties;

import com.google.common.base.MoreObjects;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>File: ApnsProperties.java </p>
 *
 * <p>Description: Apns配置参数 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/10 14:20
 */
@ConfigurationProperties(prefix = "herodotus.integration.push.apns")
public class ApnsProperties {

    /**
     * 是否是Sandbox模式
     * true sandbox模式，false production模式
     */
    private boolean sandbox;
    /**
     * IOS 应用bundleId
     */
    private String bundleId;
    /**
     * p12文件路径
     */
    private String filePath;
    /**
     * p12文件密码
     */
    private String password;

    public boolean isSandbox() {
        return sandbox;
    }

    public void setSandbox(boolean sandbox) {
        this.sandbox = sandbox;
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("sandbox", sandbox)
                .add("bundleId", bundleId)
                .add("filePath", filePath)
                .add("password", password)
                .toString();
    }
}
