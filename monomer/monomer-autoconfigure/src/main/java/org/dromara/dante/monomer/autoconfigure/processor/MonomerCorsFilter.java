/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2020-2030 郑庚伟 ZHENGGENGWEI (码匠君), <herodotus@aliyun.com> Licensed under the AGPL License
 *
 * This file is part of Dante Cloud.
 *
 * Dante Cloud is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Dante Cloud is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.herodotus.cn>.
 */

package org.dromara.dante.monomer.autoconfigure.processor;

import cn.herodotus.engine.assistant.core.utils.http.HeaderUtils;
import cn.herodotus.engine.assistant.definition.constants.SymbolConstants;
import com.google.common.net.HttpHeaders;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * <p>Description: Dante Cloud 单体版本 CORS 配置 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/9/20 15:27
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MonomerCorsFilter implements Filter {

    private static final String[] ACCESS_CONTROL_ALLOW_METHODS = new String[]{HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name(), HttpMethod.OPTIONS.name()};

    private static final String[] ACCESS_CONTROL_ALLOW_HEADERS = new String[]{HttpHeaders.X_REQUESTED_WITH, HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE, "X-XSRF-TOKEN", HeaderUtils.X_HERODOTUS_SESSION_ID, HeaderUtils.X_HERODOTUS_TENANT_ID};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, HeaderUtils.getOrigin(request));
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, StringUtils.join(ACCESS_CONTROL_ALLOW_METHODS, SymbolConstants.COMMA));
        response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, StringUtils.join(ACCESS_CONTROL_ALLOW_HEADERS, SymbolConstants.COMMA));

        if (HttpMethod.OPTIONS.name().equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(servletRequest, servletResponse);
        }

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }
}
