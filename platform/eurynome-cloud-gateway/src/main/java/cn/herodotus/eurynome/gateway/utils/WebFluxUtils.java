package cn.herodotus.eurynome.gateway.utils;

import cn.herodotus.eurynome.common.domain.Result;
import com.alibaba.fastjson.JSON;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.reactive.resource.ResourceUrlProvider;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class WebFluxUtils {

    private static final PathMatcher pathMatcher = new AntPathMatcher();
    private static final ResourceUrlProvider resourceUrlProvider = new ResourceUrlProvider();

    public static PathMatcher getPathMatcher() {
        return pathMatcher;
    }

    public static ResourceUrlProvider getResourceUrlProvider() {
        return resourceUrlProvider;
    }

    public static Mono<Boolean> isStaticResources(String uri, ServerWebExchange exchange) {
        ResourceUrlProvider resourceUrlProvider = getResourceUrlProvider();
        Mono<String> staticUri = resourceUrlProvider.getForUriString(uri, exchange);
        return staticUri.hasElement();
    }

    /**
     * 判断路径是否与路径模式匹配
     * @param patterns 路径模式数组
     * @param path url
     * @return 是否匹配
     */
    public static boolean isPathMatch(String[] patterns, String path) {
        return isPathMatch(Arrays.asList(patterns), path);
    }

    /**
     * 判断路径是否与路径模式匹配
     * @param patterns 路径模式字符串List
     * @param path url
     * @return 是否匹配
     */
    public static boolean isPathMatch(List<String> patterns, String path) {
        PathMatcher pathMatcher = getPathMatcher();
        for (String pattern : patterns) {
            if (pathMatcher.match(pattern, path)) {
                return true;
            }
        }
        return false;
    }

    public static Mono<Void> writeJsonResponse(ServerHttpResponse response, Result<String> result) {
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.setStatusCode(HttpStatus.valueOf(result.getHttpStatus()));

        String jsonResult = JSON.toJSONString(result);
        byte[] bytes = jsonResult.getBytes(StandardCharsets.UTF_8);

        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Flux.just(buffer));
    }
}
