/*
 * Copyright 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.herodotus.dantecloud.gateway.filter;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalSqlInjectionFilterTest {
    @Test
    void rebuiltBodyLengthMatchesUtf8Bytes() {
        assertEquals(StandardCharsets.ISO_8859_1, Charset.defaultCharset());
        String body = "{\"name\":\"中文\"}";
        MockServerWebExchange exchange = MockServerWebExchange.from(
                MockServerHttpRequest.post("/test").contentType(MediaType.APPLICATION_JSON).body(body));
        AtomicLong length = new AtomicLong(-1);
        AtomicReference<byte[]> sent = new AtomicReference<>();
        new GlobalSqlInjectionFilter().filter(exchange, forwarded -> {
            length.set(forwarded.getRequest().getHeaders().getContentLength());
            return DataBufferUtils.join(forwarded.getRequest().getBody()).flatMap(buffer -> {
                byte[] bytes = new byte[buffer.readableByteCount()];
                buffer.read(bytes);
                DataBufferUtils.release(buffer);
                sent.set(bytes);
                return Mono.empty();
            });
        }).block();
        assertEquals(body, new String(sent.get(), StandardCharsets.UTF_8));
        assertEquals(sent.get().length, length.get());
    }
}
