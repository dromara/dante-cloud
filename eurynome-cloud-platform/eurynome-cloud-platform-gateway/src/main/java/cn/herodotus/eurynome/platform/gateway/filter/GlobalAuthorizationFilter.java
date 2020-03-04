package cn.herodotus.eurynome.platform.gateway.filter;

import cn.herodotus.eurynome.component.common.constants.SecurityConstants;
import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.component.common.enums.ResultStatus;
import cn.herodotus.eurynome.component.data.component.RedisGatewayTrace;
import cn.herodotus.eurynome.platform.gateway.properties.ArtisanGatewayProperties;
import cn.herodotus.eurynome.platform.gateway.utils.GatewayUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: GlobalAuthorizationFilter </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/4 11:01
 */
@Slf4j
@Component
@Data
public class GlobalAuthorizationFilter implements GlobalFilter, Ordered {

    @Autowired
    private RedisGatewayTrace redisGatewayTrace;

    @Resource
    private ArtisanGatewayProperties artisanGatewayProperties;

    private static final String COUNT_START_TIME = "countStartTime";
    // 放行名单
    private List<String> skipAuthUrls;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 记录用时
        exchange.getAttributes().put(COUNT_START_TIME, System.currentTimeMillis());

        String url = exchange.getRequest().getURI().getPath();

        if (redisGatewayTrace.isMustBeAccessed()) {
            // 设置跟踪标识
            String secretKey = redisGatewayTrace.create(SecurityConstants.GATEWAY_STORAGE_KEY);

            ServerHttpRequest request = exchange.getRequest().mutate().headers(httpHeaders -> {
                List<String> gatewayHeaderValues = new ArrayList<>();
                gatewayHeaderValues.add(secretKey);
                httpHeaders.put(SecurityConstants.GATEWAY_TRACE_HEADER, gatewayHeaderValues);
            }).build();

            exchange.mutate().request(request).build();
        }

        this.skipAuthUrls = artisanGatewayProperties.getWhiteList();

        // 跳过不需要验证的路径
        if (CollectionUtils.isNotEmpty(skipAuthUrls) && skipAuthUrls.contains(url)) {
            return chain.filter(exchange);
        }

        // 获取token
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        // 检查token
        if (StringUtils.isBlank(token)) {
            return GatewayUtils.writeJsonResponse(exchange.getResponse(), new Result<String>().type(ResultStatus.UNAUTHORIZED));
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
