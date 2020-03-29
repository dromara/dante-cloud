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
 * File Name: BanWebResponseExceptionTranslator.java
 * Author: gengwei.zheng
 * Date: 2019/11/18 上午8:17
 * LastModified: 2019/11/18 上午8:07
 */

package cn.herodotus.eurynome.component.security.response;

import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.component.security.exception.SecurityGlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author gengwei.zheng
 */

@SuppressWarnings("rawtypes")
@Slf4j
public class HerodotusWebResponseExceptionTranslator implements WebResponseExceptionTranslator {

    @Override
    public ResponseEntity translate(Exception e) throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        Result<String> result = SecurityGlobalExceptionHandler.resolveOauthException(e, request.getRequestURI());
        return ResponseEntity.status(result.getHttpStatus()).body(result);
    }
}
