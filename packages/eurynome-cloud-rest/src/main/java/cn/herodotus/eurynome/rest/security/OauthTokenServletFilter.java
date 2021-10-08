/*
 * Copyright (c) 2019-2021 Gengwei Zheng (herodotus@aliyun.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-rest
 * File Name: OauthTokenServletFilter.java
 * Author: gengwei.zheng
 * Date: 2021/10/08 19:19:08
 */

package cn.herodotus.eurynome.rest.security;

import cn.herodotus.eurynome.common.constant.magic.HttpHeaders;
import cn.herodotus.eurynome.common.constant.magic.SecurityConstants;
import cn.herodotus.eurynome.rest.crypto.InterfaceCryptoProcessor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <p>Description: Oauth Token 解密处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/8 19:19
 */
public class OauthTokenServletFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(OauthTokenServletFilter.class);

    private InterfaceCryptoProcessor interfaceCryptoProcessor;

    public void setInterfaceCryptoProcessor(InterfaceCryptoProcessor interfaceCryptoProcessor) {
        this.interfaceCryptoProcessor = interfaceCryptoProcessor;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String requestURI = request.getRequestURI();
        String requestMethod = request.getMethod();
        String sessionId = request.getHeader(HttpHeaders.X_HERODOTUS_SESSION);

        if (StringUtils.equals(requestURI, SecurityConstants.ENDPOINT_OAUTH_TOKEN) && StringUtils.isNotBlank(sessionId) && StringUtils.equalsIgnoreCase(requestMethod, HttpMethod.POST.name())) {
            OauthTokenServletRequestWrapper oauthTokenRequest = new OauthTokenServletRequestWrapper(request, sessionId, interfaceCryptoProcessor);
            log.debug("[Herodotus] |- OauthTokenServletFilter wrapper request for [{}].", request.getRequestURI());
            filterChain.doFilter(oauthTokenRequest, servletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
