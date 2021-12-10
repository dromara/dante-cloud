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
 * Module Name: eurynome-cloud-rest
 * File Name: AccessLimitedStampManager.java
 * Author: gengwei.zheng
 * Date: 2021/11/15 15:31:15
 */

package cn.herodotus.eurynome.rest.stamp;

import cn.herodotus.eurynome.cache.constant.CacheConstants;
import cn.herodotus.eurynome.cache.definition.AbstractStampManager;
import cn.herodotus.eurynome.rest.properties.StampProperties;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;

/**
 * <p>Description: 防刷签章管理器 </p>
 * <p>
 * 这里使用Long类型作为值的存储类型，是为了解决该Cache 同时可以存储Duration相关的数据
 *
 * @author : gengwei.zheng
 * @date : 2021/8/26 19:01
 */
public class AccessLimitedStampManager extends AbstractStampManager<String, Long> {

    private StampProperties stampProperties;

    public void setStampProperties(StampProperties stampProperties) {
        this.stampProperties = stampProperties;
    }

    @CreateCache(name = CacheConstants.CACHE_NAME_TOKEN_ACCESS_LIMITED, cacheType = CacheType.BOTH)
    protected Cache<String, Long> cache;

    @Override
    protected Cache<String, Long> getCache() {
        return this.cache;
    }

    @Override
    public Long generate(String key) {
        return 1L;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.setExpire(stampProperties.getAccessLimited().getExpire());
    }
}
