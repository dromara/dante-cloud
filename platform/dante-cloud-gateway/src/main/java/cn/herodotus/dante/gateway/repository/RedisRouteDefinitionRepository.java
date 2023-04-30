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

package cn.herodotus.dante.gateway.repository;

import cn.herodotus.engine.assistant.core.json.jackson2.utils.Jackson2Utils;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: 使用Redis进行Gateway动态路由管理 </p>
 * <p>
 * Spring Cloud Gateway作为所有请求流量的入口，在实际生产环境中为了保证高可靠和高可用，尽量避免重启, 需要实现Spring Cloud Gateway动态路由配置。
 * <p>
 * 实现动态路由其实很简单, 重点在于 RouteDefinitionRepository 这个接口. 这个接口继承自两个接口, 其中:
 * · RouteDefinitionLocator 是用来加载路由的. 它有很多实现类, 其中的 PropertiesRouteDefinitionLocator 就用来实现从yml中加载路由.
 * · RouteDefinitionWriter 用来实现路由的添加与删除.
 *
 * @author : gengwei.zheng
 * @date : 2020/3/3 17:23
 */
@Component
public class RedisRouteDefinitionRepository implements RouteDefinitionRepository {

    private static final Logger log = LoggerFactory.getLogger(RedisRouteDefinitionRepository.class);

    public static final String GATEWAY_ROUTES = "gateway:routes";

    @Resource
    private RedisTemplate<String, RouteDefinition> redisTemplate;

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        List<RouteDefinition> routeDefinitions = new ArrayList<>();
        redisTemplate.opsForHash().entries(GATEWAY_ROUTES).values().forEach(routeDefinition -> routeDefinitions.add(Jackson2Utils.toObject(routeDefinition.toString(), RouteDefinition.class)));
        log.trace("[Herodotus] |- Get all gateway route definition from redis!");
        return Flux.fromIterable(routeDefinitions);
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> routeDefinition) {
        return routeDefinition.flatMap(route -> {
            redisTemplate.opsForHash().put(GATEWAY_ROUTES, route.getId(), Jackson2Utils.toJson(route));
            log.debug("[Herodotus] |- Redis cache the new gateway route definition.");
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
