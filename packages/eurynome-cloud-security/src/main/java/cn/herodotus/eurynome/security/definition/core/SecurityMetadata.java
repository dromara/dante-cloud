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
 * Module Name: eurynome-cloud-security
 * File Name: SecurityMetadata.java
 * Author: gengwei.zheng
 * Date: 2021/08/05 17:14:05
 */

package cn.herodotus.eurynome.security.definition.core;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * <p>Description: 安全元数据 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/5 17:14
 */
public interface SecurityMetadata {

    String getDefaultExpression();

    String getStaticExpression();

    String getDynamicExpression();

    String getScopeExpression();

    Collection<? extends GrantedAuthority> getRoles();

    Collection<? extends GrantedAuthority> getScopes();

    String getIpExpression();

    String getUrl();

    String getRequestMethod();

    String getServiceId();
}
