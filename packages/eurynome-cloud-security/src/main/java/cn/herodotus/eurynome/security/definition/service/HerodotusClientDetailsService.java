/*
 * Copyright (c) 2019-2021 Gengwei Zheng(herodotus@aliyun.com)
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
 * File Name: HerodotusClientDetailsService.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

package cn.herodotus.eurynome.security.definition.service;

import cn.herodotus.engine.security.core.definition.domain.HerodotusClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: HerodotusClientDetailsService </p>
 *
 * <p>Description: 自定义ClientDetailsService，方便以后扩展  </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/1/17 12:49
 */
public interface HerodotusClientDetailsService extends ClientDetailsService {

    /**
     * 根据clienId获取Client Details，并且转换为HerodotusClientDetails
     * @param clientId
     * @return
     */
    public HerodotusClientDetails getOauthClientDetails(String clientId);
}
