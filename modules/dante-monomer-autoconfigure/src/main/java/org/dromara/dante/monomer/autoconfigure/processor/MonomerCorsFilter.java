/*
 * Copyright 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * Dante Cloud licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Cloud 采用 APACHE LICENSE 2.0 开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1. 请不要删除和修改根目录下的LICENSE文件。
 * 2. 请不要删除和修改 Dante Cloud 源码头部的版权声明。
 * 3. 请保留源码和相关描述文件的项目出处，作者声明等。
 * 4. 分发源码时候，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 5. 在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 6. 若您的项目无法满足以上几点，可申请商业授权
 */

package org.dromara.dante.monomer.autoconfigure.processor;

import com.google.common.net.HttpHeaders;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.dromara.dante.core.constant.HerodotusHeaders;
import org.dromara.dante.core.constant.SymbolConstants;
import org.dromara.dante.web.servlet.utils.HeaderUtils;
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

    private static final String[] ACCESS_CONTROL_ALLOW_HEADERS = new String[]{HttpHeaders.X_REQUESTED_WITH, HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE, "X-XSRF-TOKEN", HerodotusHeaders.X_HERODOTUS_SESSION_ID, HerodotusHeaders.X_HERODOTUS_TENANT_ID};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, HeaderUtils.getOrigin(request));
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, StringUtils.join(ACCESS_CONTROL_ALLOW_METHODS, SymbolConstants.COMMA));
        response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, StringUtils.join(ACCESS_CONTROL_ALLOW_HEADERS, SymbolConstants.COMMA));

        if (Strings.CI.equals(HttpMethod.OPTIONS.name(), request.getMethod())) {
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
