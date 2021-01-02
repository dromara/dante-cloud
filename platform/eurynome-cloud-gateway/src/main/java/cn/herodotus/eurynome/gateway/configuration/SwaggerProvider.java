package cn.herodotus.eurynome.gateway.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.*;

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

    public static final String API_URI = "/v3/api-docs";

    /**
     * 网关应用名称
     */
    @Value("${spring.application.name}")
    private String self;

    private final RouteLocator routeLocator;

    @Autowired
    public SwaggerProvider(RouteLocator routeLocator) {
        this.routeLocator = routeLocator;
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<String> routes = new ArrayList<>();
        routeLocator.getRoutes()
                .filter(route -> route.getUri().getHost() != null && Objects.equals(route.getUri().getScheme(), "lb") && !self.equalsIgnoreCase(route.getUri().getHost()))
                .subscribe(route -> routes.add(route.getUri().getHost()));

        // 记录已经添加过的server，存在同一个应用注册了多个服务在注册中心上
        Set<String> processed = new HashSet<>();
        routes.forEach(service -> {
            System.out.println(service);
            // 拼接url ，请求swagger的url
            String url = "/" + service.toLowerCase() + API_URI;
            if (!processed.contains(url)) {
                processed.add(url);
                resources.add(createSwaggerResource(service, url));
            }
        });
        return resources;
    }

    private SwaggerResource createSwaggerResource(String name, String url) {

        log.debug("[Eurynome] |- Create Swagger Resource - Name: {}, Location {}.", name, url);

        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setUrl(url);
        swaggerResource.setLocation(url);
        swaggerResource.setSwaggerVersion("3.0");
        return swaggerResource;
    }
}
