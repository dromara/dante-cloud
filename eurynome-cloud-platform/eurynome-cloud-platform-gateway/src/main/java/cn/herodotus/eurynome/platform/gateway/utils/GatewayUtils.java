package cn.herodotus.eurynome.platform.gateway.utils;

import cn.herodotus.eurynome.component.common.domain.Result;
import com.alibaba.fastjson.JSON;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class GatewayUtils {

    public static Mono<Void> writeJsonResponse(ServerHttpResponse response, Result result) {
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatusCode(HttpStatus.valueOf(result.getHttpStatus()));

        String jsonResult = JSON.toJSONString(result);
        byte[] bytes = jsonResult.getBytes(StandardCharsets.UTF_8);

        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Flux.just(buffer));
    }
}
