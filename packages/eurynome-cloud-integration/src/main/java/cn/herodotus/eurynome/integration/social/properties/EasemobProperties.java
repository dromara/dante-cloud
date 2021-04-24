package cn.herodotus.eurynome.integration.social.properties;

import com.google.common.base.MoreObjects;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: 环信配置属性 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/1 13:46
 */
@ConfigurationProperties(prefix = "herodotus.integration.social.easemob")
public class EasemobProperties {

    private String baseUrl;
    private String clientId;
    private String clientSecret;
    private String grantType = "client_credentials";

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("url", baseUrl)
				.add("clientId", clientId)
				.add("clientSecret", clientSecret)
				.add("grantType", grantType)
				.toString();
	}
}
