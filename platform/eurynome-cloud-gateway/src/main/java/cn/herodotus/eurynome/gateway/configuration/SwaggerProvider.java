package cn.herodotus.eurynome.gateway.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: Swagger文档聚集配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/4/6 16:16
 */
@Slf4j
@Component
@Primary
public class SwaggerProvider implements SwaggerResourcesProvider {

    public static final String API_URI = "/v2/api-docs";
    private static final String DISCOVERY_CLIENT_PREFIX = "ReactiveCompositeDiscoveryClient_";

    private final RouteDefinitionLocator routeDefinitionLocator;

    @Autowired
    public SwaggerProvider(RouteDefinitionLocator routeDefinitionLocator) {
        this.routeDefinitionLocator = routeDefinitionLocator;
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> swaggerResources = new ArrayList<>();

        //结合配置的route-路径(Path)，和route过滤，只获取有效的route节点
        routeDefinitionLocator.getRouteDefinitions()
                .filter(routeDefinition -> routeDefinition.getUri().toString().contains("lb://"))
                .subscribe(routeDefinition -> {
                            routeDefinition.getPredicates().stream()
                                    .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
                                    .filter(predicateDefinition -> !predicateDefinition.getArgs().containsKey("_rateLimit"))
                                    .forEach(predicateDefinition -> swaggerResources.add(createSwaggerResource(StringUtils.remove(routeDefinition.getId(), DISCOVERY_CLIENT_PREFIX),
                                            predicateDefinition.getArgs().get("pattern")
                                                    .replace("/**", API_URI))));
                        }
                );
        // TODO: 如果Gateway中包含接口API，可以将下面内容删除。目前Gateway还没有文档，就先在聚集文档当中去掉取消Gateway服务相关内容
        swaggerResources.removeIf(swaggerResource -> StringUtils.contains(swaggerResource.getName(), "gateway"));

        return swaggerResources;
    }

    private SwaggerResource createSwaggerResource(String name, String location) {

        log.debug("[Herodotus] |- Create Swagger Resource - Name: {}, Location {}.", name, location);

        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
