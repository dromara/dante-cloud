/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2020-2030 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 *    Author: ZHENGGENGWEI<码匠君>
 *    Contact: <herodotus@aliyun.com>
 *    Blog and source code availability: <https://gitee.com/herodotus/herodotus-cloud>
 */

package cn.herodotus.cloud.gateway.filter;

import cn.herodotus.stirrup.core.definition.constants.ErrorCodes;
import cn.herodotus.stirrup.core.foundation.utils.protect.SqlInjectionUtils;
import cn.herodotus.stirrup.web.core.reactive.secure.SqlInjectionRequestBodyChecker;
import cn.herodotus.stirrup.web.core.reactive.utils.ExchangeUtils;
import cn.herodotus.stirrup.web.core.reactive.utils.ResponseUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.core5.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * <p>Description: SQL 注入拦截过滤器 </p>
 * <p>
 * 对xss字符集的转换会导致会第三方平台接入的接口出现一些列问题，尤其是需要参数签名验签的接口，因为参数的变化导致验签不成功
 * 对于第三方平台（尤其时强势的第三方），我们往往无法要求第三方按照我们的参数规则传递参数，这类的接口会包含sql注入的关键字
 * 在请求重构过程，可能会改变参数的结构，会导致验签失败
 * 对post请求，虽然目前前后端大多交互都是通过Json，但如有特殊请求参数可能是非Json格式参数，需要多改类型参数进行兼容
 *
 * @author : gengwei.zheng
 * @date : 2021/9/1 8:47
 */
@Component
public class GlobalSqlInjectionFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(GlobalSqlInjectionFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        log.debug("[Herodotus] |- Global SQL Injection Filter in use!");

        if (ExchangeUtils.isGetTypeRequest(exchange)) {
            URI uri = exchange.getRequest().getURI();
            String rawQuery = uri.getRawQuery();
            if (StringUtils.isBlank(rawQuery)) {
                return chain.filter(exchange);
            }

            boolean isSQLInjection = SqlInjectionUtils.checkForGet(rawQuery);

            // 如果存在sql注入,直接拦截请求
            if (isSQLInjection) {
                return sqlInjectionResponse(exchange);
            }
            // 不对参数做任何处理
            return chain.filter(exchange);
        }

        //post请求时，如果是文件上传之类的请求，不修改请求消息体
        if (ExchangeUtils.isPostTypeRequest(exchange)) {
            // 由于post的body只能订阅一次，由于上面代码中已经订阅过一次body。所以要再次封装请求到request才行，不然会报错请求已经订阅过
            return ExchangeUtils.modify(exchange, new SqlInjectionRequestBodyChecker(), chain::filter, this::sqlInjectionResponse);
        }

        return chain.filter(exchange);
    }

    private Mono<Void> sqlInjectionResponse(ServerWebExchange exchange) {
        URI uri = ExchangeUtils.getRequestURI(exchange);
        log.error("[Herodotus] |- Parameters of Request [{}] contain illegal SQL keyword!", uri.getRawPath());
        return ResponseUtils.renderError(exchange, ErrorCodes.SQL_INJECTION_REQUEST, HttpStatus.SC_FORBIDDEN);
    }

    /**
     * 自定义过滤器执行的顺序，数值越大越靠后执行，越小就越先执行
     *
     * @return order
     */
    @Override
    public int getOrder() {
        return FilterOrder.GLOBAL_SQL_INJECTION_FILTER_ORDER;
    }
}
