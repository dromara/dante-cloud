package cn.herodotus.eurynome.platform.gateway.filter;

import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.component.common.enums.ResultStatus;
import cn.herodotus.eurynome.platform.gateway.properties.ArtisanGatewayProperties;
import cn.herodotus.eurynome.platform.gateway.service.AuthorizationTokenService;
import cn.herodotus.eurynome.platform.gateway.utils.GatewayUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Component
@Data
public class AuthorizationFilter implements GlobalFilter, Ordered {

    @Autowired
    private AuthorizationTokenService authorizationTokenService;

    @Resource
    private ArtisanGatewayProperties artisanGatewayProperties;

    @Value(value = "${token-check-uri}")
    private String token_check_uri;

    private static final String COUNT_START_TIME = "countStartTime";
    // 放行名单
    private List<String> skipAuthUrls;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 记录用时
        exchange.getAttributes().put(COUNT_START_TIME, System.currentTimeMillis());

        String url = exchange.getRequest().getURI().getPath();

        this.skipAuthUrls = artisanGatewayProperties.getWhiteList();

        // 跳过不需要验证的路径
        if (CollectionUtils.isNotEmpty(skipAuthUrls) && skipAuthUrls.contains(url)) {
            return chain.filter(exchange);
        }

        // 获取token
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        // 检查token
        ServerHttpResponse exchangeResponse = exchange.getResponse();
        // 没有token
        if (StringUtils.isBlank(token)) {
            return GatewayUtils.writeJsonResponse(exchangeResponse, Result.failed().type(ResultStatus.UNAUTHORIZED));
        } else { // 有token 验证是否有效
            Result result = authorizationTokenService.checkToken(token);
            result.path(url);
            log.info("[Luban] |- Gateway Check Token Result: {}", result.toString());

            if (result.getHttpStatus() != 200) {
                return GatewayUtils.writeJsonResponse(exchangeResponse, result);
            }
        }
        //先放行
        return chain.filter(exchange).then(
                Mono.fromRunnable(() -> {
                    Long startTime = exchange.getAttribute(COUNT_START_TIME);
                    if (startTime != null) {
                        long endTime = (System.currentTimeMillis() - startTime);
                        log.info(exchange.getRequest().getURI().getRawPath() + ": " + endTime + "ms");
                    }
                })
        );
    }

    @Override
    public int getOrder() {
        //值越小优先级越高
        return Ordered.LOWEST_PRECEDENCE;
    }
}
