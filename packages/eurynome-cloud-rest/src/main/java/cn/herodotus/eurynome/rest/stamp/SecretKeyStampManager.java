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
 * File Name: SecretKeyStampManager.java
 * Author: gengwei.zheng
 * Date: 2021/11/15 15:31:15
 */

package cn.herodotus.eurynome.rest.stamp;

import cn.herodotus.eurynome.cache.constant.CacheConstants;
import cn.herodotus.eurynome.cache.definition.AbstractStampManager;
import cn.herodotus.eurynome.data.domain.SecretKey;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.RSA;
import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Description: 数据加密秘钥缓存 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/8 19:05
 */
public class SecretKeyStampManager extends AbstractStampManager<String, SecretKey> {

    private static final Logger log = LoggerFactory.getLogger(SecretKeyStampManager.class);

    @CreateCache(name = CacheConstants.CACHE_NAME_TOKEN_SECURE_KEY, cacheType = CacheType.BOTH)
    protected Cache<String, SecretKey> cache;

    @Override
    protected Cache<String, SecretKey> getCache() {
        return this.cache;
    }

    @Override
    public SecretKey generate(String key) {
        // 生成 AES 秘钥
        String aesKey = RandomUtil.randomStringUpper(16);

        // 生成 RSA 公钥和私钥
        RSA rsa = SecureUtil.rsa();

        SecretKey secretKey = new SecretKey();
        secretKey.setSessionId(key);
        secretKey.setAesKey(aesKey);
        secretKey.setPrivateKeyBase64(rsa.getPrivateKeyBase64());
        secretKey.setPublicKeyBase64(rsa.getPublicKeyBase64());

        log.debug("[Herodotus] |- Generate secret key, value is : [{}]", secretKey);
        return secretKey;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
