package cn.herodotus.eurynome.integration.social.service.easemob;

import cn.herodotus.eurynome.integration.definition.AbstractRestApiService;
import cn.herodotus.eurynome.integration.social.domain.easemob.Token;
import cn.herodotus.eurynome.integration.social.properties.EasemobProperties;
import com.ejlchina.okhttps.OkHttps;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description: 环信基础Service </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/6 11:28
 */
public abstract class AbstractBaseService extends AbstractRestApiService {

    @Autowired
    private EasemobProperties easemobProperties;

    private static final int BUFFER_TIME = 10;

    @Override
    protected String getBaseUrl() {
        return easemobProperties.getBaseUrl();
    }

    /**
     * 获取Easemob Token
     *
     * TODO：这里可以根据实际情况，利用Redis等进行缓存
     *
     * @return
     */
    private Token token() {
        return this.http().sync("/token")
                .bodyType(OkHttps.JSON)
                .addBodyPara("client_id", easemobProperties.getClientId())
                .addBodyPara("client_secret", easemobProperties.getClientSecret())
                .addBodyPara("grant_type", easemobProperties.getGrantType())
                .post()
                .getBody()
                .toBean(Token.class);
    }

    protected Token getToken() {
        Token redisStoreToken = null;
        if (ObjectUtils.isNotEmpty(redisStoreToken)) {
            return redisStoreToken;
        } else {
            return this.token();
        }
    }

    protected Map<String, String> getTokenHeader() {
        Map<String, String> header = new HashMap<>();
        header.put(HttpHeaders.AUTHORIZATION, "Bearer " + getToken().getAccessToken());
        return header;
    }

}
