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
 * Module Name: eurynome-cloud-data
 * File Name: IdempotentStampManager.java
 * Author: gengwei.zheng
 * Date: 2021/08/26 19:02:26
 */

package cn.herodotus.eurynome.data.stamp;

import cn.herodotus.eurynome.cache.constant.CacheConstants;
import cn.herodotus.eurynome.cache.definition.AbstractStampManager;
import cn.herodotus.eurynome.data.properties.StampProperties;
import cn.hutool.core.util.IdUtil;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;

/**
 * <p>Description: 幂等Stamp管理 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/8/26 19:02
 */
public class IdempotentStampManager extends AbstractStampManager<String, String> {

    private StampProperties stampProperties;

    public void setStampProperties(StampProperties stampProperties) {
        this.stampProperties = stampProperties;
    }

    @CreateCache(name = CacheConstants.CACHE_NAME_TOKEN_IDEMPOTENT, cacheType = CacheType.BOTH)
    protected Cache<String, String> cache;

    @Override
    protected Cache<String, String> getCache() {
        return this.cache;
    }

    @Override
    public String generate(String key) {
        return IdUtil.fastSimpleUUID();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.setExpire(stampProperties.getIdempotent().getExpire());
    }
}
