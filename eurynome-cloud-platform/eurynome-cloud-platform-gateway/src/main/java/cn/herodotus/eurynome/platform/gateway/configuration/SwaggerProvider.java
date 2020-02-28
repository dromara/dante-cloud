package cn.herodotus.eurynome.platform.gateway.configuration;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
@AllArgsConstructor
public class SwaggerProvider implements SwaggerResourcesProvider {

    public static final String API_URI = "/v2/api-docs";
    private static final String DISCOVERY_CLIENT_PREFIX = "CompositeDiscoveryClient_";


    @Autowired
    private RouteDefinitionLocator routeDefinitionLocator;


    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<RouteDefinition> routeDefinitions = new ArrayList<>();

        routeDefinitionLocator.getRouteDefinitions().subscribe(routeDefinitions::add);

        routeDefinitions.stream()
                .filter(routeDefinition -> StringUtils.startsWithIgnoreCase(routeDefinition.getId(), DISCOVERY_CLIENT_PREFIX))
                .forEach(routeDefinition -> routeDefinition.getPredicates().stream()
                        .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
                        .forEach(predicateDefinition -> resources.add(
                                swaggerResource(
                                        StringUtils.remove(routeDefinition.getId(), DISCOVERY_CLIENT_PREFIX),
                                        predicateDefinition.getArgs().get("pattern").replace("/**", API_URI)))));

        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
