package cn.herodotus.eurynome.integration.push.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: 极光推送配置属性 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/20 8:58
 */
@ConfigurationProperties(prefix = "herodotus.integration.push.jpush")
public class JPushProperties {

    /**
     * masterSecret(从极光后台获得)
     */
    private String masterSecret;

    /**
     * appKey(从极光后台获得)
     */
    private String appKey;

    /**
     * 是IOS 开发环境，还是生产环境
     */
    private Boolean apnsProduction = false;

    /**
     * 是否启用代理服务器
     */
    private boolean useProxy = false;

    /**
     * 代理服务器主机名或IP
     */
    private String proxyHost;

    /**
     * 代理服务器端口号
     */
    private int proxyPort;

    /**
     * 代理服务器用户名
     */
    private String proxyUsername;

    /**
     * 代理服务器密码
     */
    private String proxyPassword;

    /**
     * 重试时间间隔(毫秒)
     */
    private Long retryInterval = 500L;

    /**
     * 最大重试次数(0表示不重试)
     */
    private Integer retryMaxAttempts = 0;

    public Boolean getApnsProduction() {
        return apnsProduction;
    }

    public void setApnsProduction(Boolean apnsProduction) {
        this.apnsProduction = apnsProduction;
    }

    public String getMasterSecret() {
        return masterSecret;
    }

    public void setMasterSecret(String masterSecret) {
        this.masterSecret = masterSecret;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public boolean isUseProxy() {
        return useProxy;
    }

    public void setUseProxy(boolean useProxy) {
        this.useProxy = useProxy;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getProxyUsername() {
        return proxyUsername;
    }

    public void setProxyUsername(String proxyUsername) {
        this.proxyUsername = proxyUsername;
    }

    public String getProxyPassword() {
        return proxyPassword;
    }

    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
    }

    public Long getRetryInterval() {
        return retryInterval;
    }

    public void setRetryInterval(Long retryInterval) {
        this.retryInterval = retryInterval;
    }

    public Integer getRetryMaxAttempts() {
        return retryMaxAttempts;
    }

    public void setRetryMaxAttempts(Integer retryMaxAttempts) {
        this.retryMaxAttempts = retryMaxAttempts;
    }
}
