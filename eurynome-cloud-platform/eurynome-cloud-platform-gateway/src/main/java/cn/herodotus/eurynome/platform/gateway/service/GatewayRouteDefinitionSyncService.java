package cn.herodotus.eurynome.platform.gateway.service;

import cn.herodotus.eurynome.platform.gateway.entity.GatewayFilterDefinition;
import cn.herodotus.eurynome.platform.gateway.entity.GatewayPredicateDefinition;
import cn.herodotus.eurynome.platform.gateway.entity.GatewayRouteDefinition;
import cn.herodotus.eurynome.platform.gateway.writer.RedisRouteDefinitionWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GatewayRouteDefinitionSyncService implements ApplicationEventPublisherAware {

    private final RedisRouteDefinitionWriter routeDefinitionWriter;
    private final GatewayRouteDefinitionService gatewayRouteDefinitionService;
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public GatewayRouteDefinitionSyncService(RedisRouteDefinitionWriter routeDefinitionWriter, GatewayRouteDefinitionService gatewayRouteDefinitionService) {
        this.routeDefinitionWriter = routeDefinitionWriter;
        this.gatewayRouteDefinitionService = gatewayRouteDefinitionService;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    private void publish() {
        this.applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
    }

    private void delete(String id) {
        this.routeDefinitionWriter.delete(Mono.just(id)).subscribe();
    }

    private void save(GatewayRouteDefinition gatewayRouteDefinition) {
        RouteDefinition routeDefinition = convertRouteDefinition(gatewayRouteDefinition);
        this.routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
        this.publish();
    }

    /**
     * 增加路由
     *
     * @param gatewayRouteDefinition
     * @return
     */
    public String addRouteDefinition(GatewayRouteDefinition gatewayRouteDefinition) {
        // 等做界面的时候增加上，在数据库中增删改查，然后同步至Redis
        GatewayRouteDefinition returnResult = gatewayRouteDefinitionService.saveOrUpdate(gatewayRouteDefinition);
//        GatewayRouteDefinition returnResult = gatewayRouteDefinition;
        if (returnResult != null) {
            this.save(returnResult);
            return "success";
        } else {
            return "failed";
        }
    }

    /**
     * 更新路由
     *
     * @param gatewayRouteDefinition
     * @return
     */
    public String updateRouteDefinition(GatewayRouteDefinition gatewayRouteDefinition) {
        this.delete(gatewayRouteDefinition.getId());
        return this.addRouteDefinition(gatewayRouteDefinition);
    }

    /**
     * 删除路由
     *
     * @param id
     * @return
     */
    public String deleteRouteDefinition(String id) {
        this.delete(id);
        this.publish();
        // 等做界面的时候增加上，在数据库中增删改查，然后同步至Redis
//        this.gatewayRouteDefinitionService.delete(id);
        return "delete success";
    }

    private RouteDefinition convertRouteDefinition(GatewayRouteDefinition gatewayRouteDefinition){
        RouteDefinition routeDefinition = new RouteDefinition();
        routeDefinition.setId(gatewayRouteDefinition.getId());
        routeDefinition.setPredicates(convertPredicateDefinitions(gatewayRouteDefinition.getPredicates()));
        routeDefinition.setFilters(convertFilterDefinitions(gatewayRouteDefinition.getFilters()));

        URI uri = UriComponentsBuilder.fromUriString(gatewayRouteDefinition.getUri()).build().toUri();
        routeDefinition.setUri(uri);

        routeDefinition.setOrder(gatewayRouteDefinition.getOrder());
        return routeDefinition;
    }

    private List<PredicateDefinition> convertPredicateDefinitions(List<GatewayPredicateDefinition> gatewayPredicateDefinitions) {
        return gatewayPredicateDefinitions.stream().map(this::convertPredicateDefinition).collect(Collectors.toList());
    }

    private List<FilterDefinition> convertFilterDefinitions(List<GatewayFilterDefinition> gatewayFilterDefinitions) {
        return gatewayFilterDefinitions.stream().map(this::convertFilterDefinition).collect(Collectors.toList());
    }

    private FilterDefinition convertFilterDefinition(GatewayFilterDefinition gatewayFilterDefinition) {
        FilterDefinition filterDefinition = new FilterDefinition();
        filterDefinition.setName(gatewayFilterDefinition.getName());
        filterDefinition.setArgs(gatewayFilterDefinition.getArgs());
        return filterDefinition;
    }

    private PredicateDefinition convertPredicateDefinition(GatewayPredicateDefinition gatewayPredicateDefinition) {
        PredicateDefinition predicateDefinition = new PredicateDefinition();
        predicateDefinition.setName(gatewayPredicateDefinition.getName());
        predicateDefinition.setArgs(gatewayPredicateDefinition.getArgs());
        return predicateDefinition;
    }
}
