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
 * File Name: HerodotusSocialDetails.java
 * Author: gengwei.zheng
 * Date: 2021/4/30 下午1:02
 * LastModified: 2021/4/30 下午12:56
 */

package cn.herodotus.eurynome.security.definition.social;

import cn.herodotus.eurynome.security.definition.domain.SocialProvider;

import java.io.Serializable;

/**
 * <p>File: HerodotusSocialDetails </p>
 *
 * <p>Description: 社交登录参数统一实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/30 12:51
 */
public abstract class HerodotusSocialDetails implements Serializable {

    /**
     * 社交详情中，主要的区分信息
     * @return String
     */
    public abstract String getPrincipal();

    /**
     * 社交详情应用代码，与区分信息对应
     * @return String
     */
    public abstract String getCode();

    /**
     * 社交登录提供商, 用于区分不同的登录
     * @return SocialProvider {@link SocialProvider}
     */
    public abstract SocialProvider getSocialProvider();
}
