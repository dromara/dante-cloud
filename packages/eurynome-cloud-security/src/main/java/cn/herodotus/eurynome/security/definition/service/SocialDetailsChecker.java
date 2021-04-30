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
 * File Name: SocialDetailsChecker.java
 * Author: gengwei.zheng
 * Date: 2021/4/30 下午12:48
 * LastModified: 2021/4/30 下午12:48
 */

package cn.herodotus.eurynome.security.definition.service;

import cn.herodotus.eurynome.security.definition.social.HerodotusSocialDetails;
import org.springframework.security.core.AuthenticationException;

/**
 * <p>File: SocialDetailsChecker </p>
 *
 * <p>Description: 验证码等验证接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/30 12:48
 */
public interface SocialDetailsChecker {

    /**
     * 验证社交信息是否匹配
     *
     * @param herodotusSocialDetails 社交信息详情实体
     * @return ture 验证通过，false 验证失败
     */
    void check(HerodotusSocialDetails herodotusSocialDetails) throws AuthenticationException;
}
