/*
 * Copyright (c) 2019-2021 the original author or authors.
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
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-security
 * File Name: AbstractSocialHandlerFactory.java
 * Author: gengwei.zheng
 * Date: 2021/4/30 下午1:09
 * LastModified: 2021/4/4 下午5:43
 */

package cn.herodotus.eurynome.security.definition.social;

import cn.herodotus.eurynome.security.response.exception.IllegalAuthenticationArgumentException;
import cn.herodotus.eurynome.security.response.exception.SocialHandlerNotFoundException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: AbstractSocialHandlerFactory </p>
 *
 * <p>Description: Social Handler 工厂 </p>
 *
 * 本处使用基于Spring Boot 的工厂模式
 * {@see :https://www.pianshen.com/article/466978086/}
 *
 * @author : gengwei.zheng
 * @date : 2021/4/4 17:40
 */
public abstract class AbstractSocialHandlerFactory implements SocialHanderFactory{

    @Autowired
    private final Map<String, SocialHandler> handlers = new ConcurrentHashMap<>();

    @Override
    public SocialHandler getSocialHandler(String providerId) throws AuthenticationException {
        if (StringUtils.isBlank(providerId)) {
            throw new IllegalAuthenticationArgumentException("Cannot found providerId");
        }

        SocialHandler socialHandler = handlers.get(providerId);
        if (ObjectUtils.isNotEmpty(socialHandler)) {
            return socialHandler;
        } else {
            throw new SocialHandlerNotFoundException("Can not found Social Handler for " + "providerId");
        }
    }

}
