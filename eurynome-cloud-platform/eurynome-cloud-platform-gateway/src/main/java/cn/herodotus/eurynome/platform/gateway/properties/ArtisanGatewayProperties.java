package cn.herodotus.eurynome.platform.gateway.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "luban.gateway")
public class ArtisanGatewayProperties {

    private static final String DEFAULT_TOKEN_CHECK_URI = "http://luban-cloud-platform-oauth/oauth/check_token";

    private String tokenCheckUri;
    private List<String> whiteList;

    public ArtisanGatewayProperties() {
        this.tokenCheckUri = DEFAULT_TOKEN_CHECK_URI;
    }

    public String getTokenCheckUri() {
        return tokenCheckUri;
    }

    public void setTokenCheckUri(String tokenCheckUri) {
        this.tokenCheckUri = tokenCheckUri;
    }

    public List<String> getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(List<String> whiteList) {
        this.whiteList = whiteList;
    }
}
