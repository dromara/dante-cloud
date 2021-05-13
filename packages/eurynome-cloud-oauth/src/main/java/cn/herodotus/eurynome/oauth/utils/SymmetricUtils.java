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
 * Module Name: eurynome-cloud-oauth
 * File Name: SymmetricUtils.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

package cn.herodotus.eurynome.oauth.utils;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.herodotus.eurynome.common.constants.SymbolConstants;
import cn.herodotus.eurynome.oauth.exception.IllegalSymmetricKeyException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * <p> Description : 基于Hutool的Aes加解密工具 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/1/28 17:25
 */
@Slf4j
public class SymmetricUtils {

    private static String encryptedRealSecretKey(String symmetricKey) {
        String realSecretKey = RandomUtil.randomString(16);
        log.trace("[Eurynome] |- Generate Random Secret Key is : [{}]", realSecretKey);

        AES ase = SecureUtil.aes(symmetricKey.getBytes());
        String encryptedRealSecretKey = ase.encryptHex(realSecretKey);
        log.trace("[Eurynome] |- Generate Encrypt Hex Secret Key is : [{}]", encryptedRealSecretKey);

        return encryptedRealSecretKey;
    }

    public static String getEncryptedSymmetricKey() {
        String symmetricKey = RandomUtil.randomString(16);
        String realSecretKey = encryptedRealSecretKey(symmetricKey);
        log.trace("[Eurynome] |- Generate Symmetric Key is : [{}]", realSecretKey);

        StringBuilder builder = new StringBuilder();
        builder.append(symmetricKey);
        builder.append(SymbolConstants.FORWARD_SLASH);
        builder.append(realSecretKey);
        return builder.toString();
    }

    public static byte[] getDecryptedSymmetricKey(String key) {
        if (!StringUtils.contains(key, SymbolConstants.FORWARD_SLASH)) {
            throw new IllegalSymmetricKeyException("Parameter Illegal!");
        }

        String[] keys = StringUtils.split(key, SymbolConstants.FORWARD_SLASH);
        String symmetricKey = keys[0];
        String realSecretKey = keys[1];

        AES ase = SecureUtil.aes(symmetricKey.getBytes());
        return ase.decrypt(realSecretKey);
    }

    public static String decrypt(String content, byte[] key) {
        if (ArrayUtils.isNotEmpty(key)) {
            AES ase = SecureUtil.aes(key);
            return ase.decryptStr(content);
        }

        return "";
    }
}
