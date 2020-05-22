/*
 * Copyright 2019-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * Project Name: luban-cloud
 * Module Name: luban-cloud-component-security
 * File Name: BanAccessDeniedHandler.java
 * Author: gengwei.zheng
 * Date: 2019/11/18 上午8:01
 * LastModified: 2019/11/17 下午8:55
 */

package cn.herodotus.eurynome.security.web.access;

import cn.herodotus.eurynome.common.domain.Result;
import cn.herodotus.eurynome.security.authentication.SecurityGlobalExceptionHandler;
import cn.herodotus.eurynome.security.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义访问拒绝
 * @author gengwei.zheng
 */
@Slf4j
public class HerodotusAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {
        Result<String> result = SecurityGlobalExceptionHandler.resolveException(exception, request.getRequestURI());
        response.setStatus(result.getHttpStatus());
        WebUtils.renderJson(response, result);
    }
}
