/*
 * Copyright (c) 2020-2030 ZHENGGENGWEI(码匠君)<herodotus@aliyun.com>
 *
 * Dante Cloud Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Cloud 采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3.请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://gitee.com/dromara/dante-cloud
 * 5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/dromara/dante-cloud
 * 6.若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.dante.gateway.filter;

import cn.herodotus.dante.gateway.properties.GatewaySecurityProperties;
import cn.herodotus.dante.gateway.utils.WebFluxUtils;
import cn.herodotus.engine.assistant.core.definition.constants.BaseConstants;
import cn.herodotus.engine.assistant.core.domain.Result;
import cn.herodotus.engine.assistant.core.enums.ResultErrorCodes;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> Description : GlobalCertificationFilter </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/4 18:36
 */
@Component
public class GlobalCertificationFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(GlobalCertificationFilter.class);

    @Resource
    private GatewaySecurityProperties gatewaySecurityProperties;
    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 说明：目前所有请求均是通过Gateway进行访问。
     * /oauth/check_token，是比较特殊的地址，不是使用token的方式进行鉴权。
     * 虽然目前使用的是“permitAll”的方式，不够安全。但是不管什么情况，在Gateway这一端，不应该进行拦截。
     * 后续可以根据IP，以及OAuth2鉴权的方式进行安全控制。
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        log.debug("[Herodotus] |- Gateway Global Certification Filter in use!");

        // 1.检查是否是免登陆连接
        String url = exchange.getRequest().getURI().getPath();
        List<String> whiteList = gatewaySecurityProperties.getWhiteList();

        log.debug("[Herodotus] |- current url is [{}]!", url);
        if (WebFluxUtils.isPathMatch(whiteList, url)) {
            log.debug("[Herodotus] |- is match!");
            return chain.filter(exchange);
        }

        // 2.外部进入的请求，如果包含 X_HERODOTUS_FROM_IN 请求头，认为是非法请求，直接拦截。X_HERODOTUS_FROM_IN 只能用于内部 Feign 间忽略权限使用
        String fromIn = exchange.getRequest().getHeaders().getFirst(cn.herodotus.engine.assistant.core.definition.constants.HttpHeaders.X_HERODOTUS_FROM_IN);
        if (ObjectUtils.isNotEmpty(fromIn)) {
            log.warn("[Herodotus] |- Illegal request to disable access!");
            return WebFluxUtils.writeJsonResponse(exchange.getResponse(), new Result<String>().type(ResultErrorCodes.ACCESS_DENIED).status(HttpStatus.SC_FORBIDDEN));
        }

        String webSocketToken =  exchange.getRequest().getHeaders().getFirst(com.google.common.net.HttpHeaders.SEC_WEBSOCKET_PROTOCOL);
        if (StringUtils.isNotBlank(webSocketToken) && StringUtils.endsWith(webSocketToken,".stomp")) {
            return chain.filter(exchange);
        }

        // 3.非免登陆地址，获取token 检查token，如果未空，或者不是 Bearer XXX形式，则认为未授权。
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (!isTokenWellFormed(token)) {
            log.debug("[Herodotus] |- Token is not Well Formed!");
            return WebFluxUtils.writeJsonResponse(exchange.getResponse(), new Result<String>().type(ResultErrorCodes.ACCESS_DENIED).status(HttpStatus.SC_UNAUTHORIZED));
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return FilterOrder.GLOBAL_CERTIFICATION_FILTER_ORDER;
    }

    private boolean isTokenWellFormed(String token) {
        return !StringUtils.isBlank(token) && !StringUtils.containsOnly(token, BaseConstants.BEARER_TOKEN);
    }
}
