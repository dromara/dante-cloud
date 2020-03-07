package cn.herodotus.eurynome.platform.gateway.filter;

import cn.herodotus.eurynome.component.common.constants.SecurityConstants;
import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.component.common.enums.ResultStatus;
import cn.herodotus.eurynome.platform.gateway.properties.GatewaySecurityProperties;
import cn.herodotus.eurynome.platform.gateway.utils.WebFluxUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
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
@Slf4j
@Component
public class GlobalCertificationFilter implements GlobalFilter, Ordered {

    @Resource
    private GatewaySecurityProperties gatewaySecurityProperties;
    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.debug("[Herodotus] |- Gateway Global Certification Filter in use!");

        // 获取token
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        // 检查token
        if (!isTokenWellFormed(token)) {
            String url = exchange.getRequest().getURI().getPath();

            List<String> whiteList = gatewaySecurityProperties.getWhiteList();
            if (WebFluxUtils.isPathMatch(whiteList, url)) {
                return chain.filter(exchange);
            } else {
                return WebFluxUtils.writeJsonResponse(exchange.getResponse(), new Result<String>().type(ResultStatus.UNAUTHORIZED).httpStatus(HttpStatus.SC_UNAUTHORIZED));
            }
        } else {
            String redisTokenKey = StringUtils.replace(token, SecurityConstants.BEARER_TOKEN, "access:");
            if (!redisTemplate.hasKey(redisTokenKey)) {
                return WebFluxUtils.writeJsonResponse(exchange.getResponse(), new Result<String>().type(ResultStatus.INVALID_TOKEN).httpStatus(HttpStatus.SC_FORBIDDEN));
            } else {
                return chain.filter(exchange);
            }
        }
    }

    @Override
    public int getOrder() {
        return FilterOrder.GLOBAL_CERTIFICATION_FILTER_ORDER;
    }

    private boolean isTokenWellFormed(String token) {
        if (StringUtils.isBlank(token) || StringUtils.containsOnly(token, SecurityConstants.BEARER_TOKEN)) {
            return false;
        }

        return true;
    }
}
