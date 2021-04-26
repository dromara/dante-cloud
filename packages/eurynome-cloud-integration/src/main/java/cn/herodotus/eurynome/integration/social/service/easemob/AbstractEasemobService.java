package cn.herodotus.eurynome.integration.social.service.easemob;

import cn.herodotus.eurynome.integration.definition.AbstractRestApiService;
import cn.herodotus.eurynome.integration.definition.IntegrationConstants;
import cn.herodotus.eurynome.integration.social.domain.easemob.common.Token;
import cn.herodotus.eurynome.integration.social.properties.EasemobProperties;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.ejlchina.okhttps.OkHttps;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>Description: 环信基础Service </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/6 11:28
 */
@Slf4j
public abstract class AbstractEasemobService extends AbstractRestApiService {

    @Autowired
    private EasemobProperties easemobProperties;
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    private static final int BUFFER_TIME = 10;

    @Override
    protected String getBaseUrl() {
        return easemobProperties.getBaseUrl();
    }

    @PostConstruct
    public void init() {
        if (ObjectUtils.isEmpty(redisTemplate)) {
            log.error("[Eurynome] |- Easemob depend on RedisTemplate, please check RedisTemplate configuration.");
        }
    }

    /**
     * 获取Easemob Token
     *
     * @return
     */
    private Token token() {
        Token token = this.http().sync("/token")
                .bodyType(OkHttps.JSON)
                .addBodyPara("client_id", easemobProperties.getClientId())
                .addBodyPara("client_secret", easemobProperties.getClientSecret())
                .addBodyPara("grant_type", easemobProperties.getGrantType())
                .post()
                .getBody()
                .toBean(Token.class);

        if (BeanUtil.isNotEmpty(token)) {
            redisTemplate.opsForValue().set(IntegrationConstants.EASEMOB_TOKEN, JSON.toJSONString(token), token.getExpiresIn().longValue(), TimeUnit.SECONDS);
            log.debug("[Eurynome] |- Fetch the easemob token and save to redis : {}", token);
        }

        return token;
    }

    protected Token getToken() {
        String redisStoreToken = redisTemplate.opsForValue().get(IntegrationConstants.EASEMOB_TOKEN);
        if (StringUtils.isNotBlank(redisStoreToken)) {
            return JSON.parseObject(redisStoreToken, Token.class);
        } else {
            return this.token();
        }
    }

    protected Map<String, String> getTokenHeader() {
        Map<String, String> header = new HashMap<>();
        header.put(HttpHeaders.AUTHORIZATION, "Bearer " + getToken().getAccessToken());
        return header;
    }

    protected String getUserUrl(String username) {
        String url = "/users/{username}";
        Map<String, String> formatter = ImmutableMap.of("username", username);
        return StrUtil.format(url, formatter);
    }




}
