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
 * File Name: HerodotusUserDetailsService.java
 * Author: gengwei.zheng
 * Date: 2021/1/17 下午12:49
 * LastModified: 2021/1/17 下午12:49
 */

package cn.herodotus.eurynome.security.definition.service;

import cn.herodotus.eurynome.security.definition.domain.SocialProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: HerodotusUserDetailsService </p>
 *
 * <p>Description: 自定义UserDetailsService接口，方便以后扩展 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/1/17 12:49
 */
public interface HerodotusUserDetailsService extends UserDetailsService {

    /**
     * 通过社交集成的唯一id，获取用户信息
     *
     * 如果是短信验证码，openId就是手机号码
     *
     * @param openId 社交集成唯一Id
     * @param socialProvider 社交集成提供商
     * @return {@link UserDetails}
     * @throws UsernameNotFoundException 用户不存在
     */
    UserDetails loadUserBySocial(String openId, SocialProvider socialProvider) throws UsernameNotFoundException;
}
