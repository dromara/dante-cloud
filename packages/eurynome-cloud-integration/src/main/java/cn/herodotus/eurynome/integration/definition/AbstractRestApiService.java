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
 * Module Name: eurynome-cloud-integration
 * File Name: AbstractRestApiService.java
 * Author: gengwei.zheng
 * Date: 2021/4/10 下午3:33
 * LastModified: 2021/4/10 下午3:33
 */

package cn.herodotus.eurynome.integration.definition;

import com.ejlchina.okhttps.FastjsonMsgConvertor;
import com.ejlchina.okhttps.HTTP;

/**
 * <p>File: AbstractRestApiService </p>
 *
 * <p>Description: 外部Rest API抽象服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/10 15:33
 */
public abstract class AbstractRestApiService {

    /**
     * 获取外部Rest API基础地址
     * @return
     */
    protected abstract String getBaseUrl();

    protected HTTP http() {
        return HTTP.builder()
                .baseUrl(getBaseUrl())
                .addMsgConvertor(new FastjsonMsgConvertor())
                .build();
    }
}
