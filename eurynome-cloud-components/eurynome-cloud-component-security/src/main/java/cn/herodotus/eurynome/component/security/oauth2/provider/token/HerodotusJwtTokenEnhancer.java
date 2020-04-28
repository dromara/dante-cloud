package cn.herodotus.eurynome.component.security.oauth2.provider.token;

import cn.herodotus.eurynome.component.common.constants.SecurityConstants;
import cn.herodotus.eurynome.component.security.core.userdetails.HerodotusUserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
/**
 * <p>Description: 自定义JwtAccessToken增强 </p>
 *
 * jwt生成token 我们可以自己定义jwt里面的内容
 *
 * @author : gengwei.zheng
 * @date : 2020/3/4 16:07
 */
public class HerodotusJwtTokenEnhancer implements TokenEnhancer {

    /**
     * 生成token,添加额外信息。
     * @param accessToken accessToken
     * @param authentication authentication
     * @return OAuth2AccessToken
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken defaultOAuth2AccessToken = new DefaultOAuth2AccessToken(accessToken);
        if (authentication.getPrincipal() != null && authentication.getPrincipal() instanceof HerodotusUserDetails) {
            // 设置额外用户信息
            // 与登录时候放进去的UserDetail实现类一直查看link{SecurityConfiguration}
            HerodotusUserDetails herodotusUserDetails = (HerodotusUserDetails) authentication.getPrincipal();
            final Map<String, Object> additionalInfo = new HashMap<>(8);
            additionalInfo.put(SecurityConstants.OPEN_ID, herodotusUserDetails.getUserId());
            additionalInfo.put(SecurityConstants.LICENSE, "herodotus-cloud");
            defaultOAuth2AccessToken.setAdditionalInformation(additionalInfo);
        }

        return defaultOAuth2AccessToken;
    }
}
