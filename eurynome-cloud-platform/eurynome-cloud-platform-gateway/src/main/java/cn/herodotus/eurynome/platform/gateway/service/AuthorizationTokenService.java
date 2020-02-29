package cn.herodotus.eurynome.platform.gateway.service;

import cn.herodotus.eurynome.component.common.constants.SecurityConstants;
import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.platform.gateway.properties.ArtisanGatewayProperties;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/** 
 * <p>Description: TODO </p>
 * 
 * @author : gengwei.zheng
 * @date : 2019/11/18 10:08
 */
@Service
@Slf4j
public class AuthorizationTokenService {

    @Resource
    private RestTemplate restTemplate;
    @Resource
    private ArtisanGatewayProperties artisanGatewayProperties;

    public Result checkToken(String token) {

        String tokenContent = "";

        if (StringUtils.isNotEmpty(token) && token.contains(SecurityConstants.BEARER_TYPE)) {
            tokenContent = token.replace(SecurityConstants.BEARER_TOKEN, "");
        }

        String tokenCheckUri = artisanGatewayProperties.getTokenCheckUri();
        String resultContent = restTemplate.getForObject(tokenCheckUri + "?token=" + tokenContent, String.class);
        
        if (StringUtils.contains(resultContent, SecurityConstants.CODE)) {
            Result result = JSON.parseObject(resultContent, Result.class);
            return result;
        } else {
            return new Result().ok();
        }
    }
}
