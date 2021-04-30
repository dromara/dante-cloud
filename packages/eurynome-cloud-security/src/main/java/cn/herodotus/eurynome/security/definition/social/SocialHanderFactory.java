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
 * File Name: SocialHanderFactory.java
 * Author: gengwei.zheng
 * Date: 2021/4/30 下午3:36
 * LastModified: 2021/4/30 下午3:36
 */

package cn.herodotus.eurynome.security.definition.social;

import org.springframework.security.core.AuthenticationException;

/**
 * <p>File: SocialHanderFactory </p>
 *
 * <p>Description: 社交登录工厂定义 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/30 15:36
 */
public interface SocialHanderFactory {

    /**
     * 根据providerId获取对应的社交登录处理器
     *
     * @param providerId 与SocialProvider对应的值
     * @return
     * @throws AuthenticationException
     */
    SocialHandler getSocialHandler(String providerId) throws AuthenticationException;
}
