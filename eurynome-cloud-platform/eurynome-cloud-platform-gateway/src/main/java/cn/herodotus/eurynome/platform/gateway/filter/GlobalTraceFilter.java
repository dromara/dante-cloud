package cn.herodotus.eurynome.platform.gateway.filter;

import cn.herodotus.eurynome.common.constants.SecurityConstants;
import cn.herodotus.eurynome.platform.gateway.properties.GatewaySecurityProperties;
import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.SecureUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;
    @Resource
    private GatewaySecurityProperties gatewaySecurityProperties;

    private static final String COUNT_START_TIME = "countStartTime";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.debug("[Herodotus] |- Gateway Global Trace Filter in use!");

        // 记录用时
        exchange.getAttributes().put(COUNT_START_TIME, System.currentTimeMillis());

        // 设置跟踪标识
        String secretKey = create(SecurityConstants.GATEWAY_STORAGE_KEY);

        ServerHttpRequest request = exchange.getRequest().mutate().headers(httpHeaders -> {
            List<String> gatewayHeaderValues = new ArrayList<>();
            gatewayHeaderValues.add(secretKey);
            httpHeaders.put(SecurityConstants.GATEWAY_TRACE_HEADER, gatewayHeaderValues);
        }).build();

        exchange.mutate().request(request).build();

        log.debug("[Herodotus] |- Gateway Generate Trace Key: [{}].", secretKey);


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

    private String create(String key) {

        long threshold = gatewaySecurityProperties.getTrace().getThreshold();
        long expired = gatewaySecurityProperties.getTrace().getExpired();

        if (StringUtils.isBlank(key)) {
            return null;
        }
        String secretKey;
        if (redisTemplate.hasKey(key)) {
            if (redisTemplate.boundHashOps(key).getExpire() < threshold) {
                redisTemplate.opsForValue().set(key, SecureUtil.md5(UUID.randomUUID().toString()), expired, TimeUnit.SECONDS);
            }
            secretKey = (String) redisTemplate.opsForValue().get(key);
        } else {
            secretKey = SecureUtil.md5(UUID.randomUUID().toString());
            redisTemplate.opsForValue().set(key, secretKey, expired, TimeUnit.SECONDS);
        }
        return secretKey;
    }
}
