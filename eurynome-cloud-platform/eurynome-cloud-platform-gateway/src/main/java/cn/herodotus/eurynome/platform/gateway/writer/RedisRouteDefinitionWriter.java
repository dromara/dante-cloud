package cn.herodotus.eurynome.platform.gateway.writer;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class RedisRouteDefinitionWriter implements RouteDefinitionRepository {

    public static final String GATEWAY_ROUTES = "gateway:routes";

    @Resource
    private RedisTemplate<String, RouteDefinition> redisTemplate;

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        List<RouteDefinition> routeDefinitions = new ArrayList<>();
        redisTemplate.opsForHash().entries(GATEWAY_ROUTES).values().forEach(routeDefinition -> routeDefinitions.add(JSON.parseObject(routeDefinition.toString(), RouteDefinition.class)));
        return Flux.fromIterable(routeDefinitions);
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> routeDefinition) {
        return routeDefinition.flatMap(route -> {
            redisTemplate.opsForHash().put(GATEWAY_ROUTES, route.getId(), JSON.toJSONString(route));
            log.debug("[Herodotus] |- Redis cache the new route definition.");
            return Mono.empty();
        });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        routeId.subscribe(id -> {
            redisTemplate.opsForHash().delete(GATEWAY_ROUTES, id);
            log.info("[Herodotus] |- Redis cache remove route definition for {}", id);
        });
        return Mono.empty();
    }
}
