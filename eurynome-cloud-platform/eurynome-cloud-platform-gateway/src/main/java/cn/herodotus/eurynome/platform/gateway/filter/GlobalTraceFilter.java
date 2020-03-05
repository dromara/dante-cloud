package cn.herodotus.eurynome.platform.gateway.filter;

import cn.herodotus.eurynome.component.common.constants.SecurityConstants;
import cn.herodotus.eurynome.component.data.component.RedisGatewayTrace;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

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
public class GlobalTraceFilter implements GlobalFilter, Ordered {

    @Autowired
    private RedisGatewayTrace redisGatewayTrace;

    private static final String COUNT_START_TIME = "countStartTime";
    // 放行名单
    private List<String> skipAuthUrls;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.debug("[Herodotus] |- Gateway Global Trace Filter in use!");

        // 记录用时
        exchange.getAttributes().put(COUNT_START_TIME, System.currentTimeMillis());

        // 是否约束从gateway访问
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

        return chain.filter(exchange).then(
                Mono.fromRunnable(() -> {
                    Long startTime = exchange.getAttribute(COUNT_START_TIME);
                    if (startTime != null) {
                        long endTime = (System.currentTimeMillis() - startTime);
                        log.info("[Herodotus] |- Request [{}] use: {} ms", exchange.getRequest().getURI().getRawPath(), endTime);
                    }
                })
        );
    }

    @Override
    public int getOrder() {
        return FilterOrder.GLOBAL_TRACE_FILTER_ORDER;
    }
}
