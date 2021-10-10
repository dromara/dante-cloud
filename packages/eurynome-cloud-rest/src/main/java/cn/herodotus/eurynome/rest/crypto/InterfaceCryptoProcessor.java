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
 * File Name: InterfaceCryptoProcessor.java
 * Author: gengwei.zheng
 * Date: 2021/10/08 19:17:08
 */

package cn.herodotus.eurynome.rest.crypto;

import cn.herodotus.eurynome.assistant.exception.crypto.SessionInvalidException;
import cn.herodotus.eurynome.common.constant.magic.SymbolConstants;
import cn.herodotus.eurynome.data.domain.SecretKey;
import cn.herodotus.eurynome.data.stamp.SecretKeyStampManager;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.AES;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * <p>Description: 接口加密解密处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/8 19:17
 */
public class InterfaceCryptoProcessor {

    private static final Logger log = LoggerFactory.getLogger(InterfaceCryptoProcessor.class);

    private static final String PKCS8_PUBLIC_KEY_BEGIN = "-----BEGIN PUBLIC KEY-----";
    private static final String PKCS8_PUBLIC_KEY_END = "-----END PUBLIC KEY-----";

    private SecretKeyStampManager secretKeyStampManager;

    public void setSecretKeyStampManager(SecretKeyStampManager secretKeyStampManager) {
        this.secretKeyStampManager = secretKeyStampManager;
    }

    private boolean isSessionValid(String sessionKey) {
        return secretKeyStampManager.containKey(sessionKey);
    }

    private SecretKey getSecretKey(String sessionKey) throws SessionInvalidException {
        if (isSessionValid(sessionKey)) {
            return secretKeyStampManager.get(sessionKey);
        }

        throw new SessionInvalidException("Session key is expired!");
    }

    private Duration getExpire(Integer accessTokenValiditySeconds) {
        if (accessTokenValiditySeconds == 0) {
            return Duration.ofHours(2L);
        } else {
            return Duration.ofSeconds(accessTokenValiditySeconds.longValue());
        }
    }

    /**
     * 根据SessionId创建SecretKey {@link SecretKey}。如果前端有可以唯一确定的SessionId，并且使用该值，则用该值创建SecretKey。否则就由后端动态生成一个SessionId。
     *
     * @param sessionId                  SessionId，可以为空。
     * @param accessTokenValiditySeconds Session过期时间，单位秒
     * @return {@link SecretKey}
     */
    public SecretKey createSecretKey(String sessionId, Integer accessTokenValiditySeconds) {
        // 前端如果设置sessionId，则由后端生成
        if (StringUtils.isBlank(sessionId)) {
            sessionId = IdUtil.fastUUID();
        }

        // 根据Token的有效时间设置
        Duration expire = getExpire(accessTokenValiditySeconds);
        return secretKeyStampManager.create(sessionId, expire);
    }

    /**
     * 前端获取后端生成 AES Key
     *
     * @param sessionId          Session ID
     * @param confidentialBase64 前端和后端加解密结果都是Base64的
     * @return 前端 PublicKey 加密后的 AES KEY
     * @throws SessionInvalidException sessionId不可用，无法从缓存中找到对应的值
     */
    public String exchange(String sessionId, String confidentialBase64) throws SessionInvalidException {
        SecretKey secretKey = getSecretKey(sessionId);
        String frontendPublicKey = decryptFrontendPublicKey(secretKey, confidentialBase64);
        return encryptBackendAesKey(secretKey, frontendPublicKey);
    }

    /**
     * 用后端 RSA PrivateKey，解密前端传递过来的、用后端 RSA PublicKey 加密的 前端 RSA PublicKey
     *
     * @param secretKey          自定义加密数据标识 {@link SecretKey}
     * @param confidentialBase64 用后端 RSA PublicKey 加密的 前端 RSA PublicKey, 需要为Base64编码
     * @return 前端 RSA PublicKey 字符串
     */
    private String decryptFrontendPublicKey(SecretKey secretKey, String confidentialBase64) {
        // 将前端加密内容用Base64解码。
        byte[] confidentialBytes = Base64.decode(confidentialBase64);
        // 使用后端 RSA PrivateKey 构建 RSA 对象
        RSA rsa = SecureUtil.rsa(secretKey.getPrivateKeyBase64(), null);
        // 使用后端 RSA PrivateKey解密前端发送过来的加密信息，得到前端 RSA PublicKey
        byte[] frontendPublicKeyBytes = rsa.decrypt(confidentialBytes, KeyType.PrivateKey);
        // 将前端 RSA PublicKey 转成字符串
        String frontendPublicKey = StrUtil.str(frontendPublicKeyBytes, StandardCharsets.UTF_8);
        log.debug("[Herodotus] |- Decrypt frontend public key, value is : [{}]", frontendPublicKey);
        return frontendPublicKey;
    }

    /**
     * 用前端 RSA PublicKey 加密后端生成的 AES Key
     *
     * @param secretKey               自定义加密数据标识 {@link SecretKey}
     * @param frontendPublicKeyString 前端 RSA PublicKey
     * @return 用前端 RSA PublicKey 加密后 AES Key
     */
    private String encryptBackendAesKey(SecretKey secretKey, String frontendPublicKeyString) {
        // 去除前端 RSA PublicKey中的 '-----BEGIN PUBLIC KEY-----'格式
        String frontendPublicKey = removePkcs8Padding(frontendPublicKeyString);
        // 使用前端 RSA PublicKey 构建 RSA 对象
        RSA rsa = SecureUtil.rsa(null, frontendPublicKey);
        // 使用前端 RSA PublicKey 加密后端生成的 AES Key
        byte[] encryptedAesKeyBytes = rsa.encrypt(secretKey.getAesKey(), KeyType.PublicKey);
        // 将前端公钥转成Base64
        String encryptedAesKey = Base64.encode(encryptedAesKeyBytes);
        log.debug("[Herodotus] |- Encrypt aes key use frontend public key, value is : [{}]", encryptedAesKey);
        return encryptedAesKey;
    }

    /**
     * 去除 RSA Pkcs8Padding 中的标记格式 '-----BEGIN PUBLIC KEY-----' 和 '-----END PUBLIC KEY-----'，以及 '\n'
     *
     * @param key RSA Key
     * @return 清楚格式后的 RSA KEY
     */
    private String removePkcs8Padding(String key) {
        String result = StringUtils.replace(key, SymbolConstants.NEW_LINE, SymbolConstants.BLANK);
        String[] values = StringUtils.split(result, "-----");
        if (ArrayUtils.isNotEmpty(values)) {
            return values[1];
        }
        return key;
    }

    /**
     * 将 RSA PublicKey 转换为 Pkcs8Padding 格式。
     *
     * @param key RSA PublicKey
     * @return 转换为 Pkcs8Padding 格式的 RSA PublicKey
     */
    public String convertPublicKeyToPkcs8Padding(String key) {
        return PKCS8_PUBLIC_KEY_BEGIN + SymbolConstants.NEW_LINE + key + SymbolConstants.NEW_LINE + PKCS8_PUBLIC_KEY_END;
    }

    public byte[] encrypt(String sessionId, String content) {
        try {
            SecretKey secretKey = getSecretKey(sessionId);
            AES aes = SecureUtil.aes(StrUtil.bytes(secretKey.getAesKey(), StandardCharsets.UTF_8));
            return aes.encrypt(content);
        } catch (Exception e) {
            log.warn("[Herodotus] |- Aes can not Encrypt content [{}], Skip!", content);
            return StrUtil.bytes(content, StandardCharsets.UTF_8);
        }
    }

    public String encryptToString(String sessionId, String content) {
        byte[] bytes = encrypt(sessionId, content);
        String result = StrUtil.str(bytes, StandardCharsets.UTF_8);
        log.debug("[Herodotus] |- Encrypt content from [{}] to [{}].", content, result);
        return result;
    }

    public byte[] decrypt(String sessionId, String content) {
        try {
            SecretKey secretKey = getSecretKey(sessionId);
            AES aes = SecureUtil.aes(StrUtil.bytes(secretKey.getAesKey(), StandardCharsets.UTF_8));
            return aes.decrypt(Base64.decode(StrUtil.bytes(content, StandardCharsets.UTF_8)));
        } catch (Exception e) {
            log.warn("[Herodotus] |- Aes can not Decrypt content [{}], Skip!", content);
            return StrUtil.bytes(content, StandardCharsets.UTF_8);
        }
    }

    public String decryptToString(String sessionId, String content) {
        byte[] bytes = decrypt(sessionId, content);
        String result = StrUtil.str(bytes, StandardCharsets.UTF_8);
        log.debug("[Herodotus] |- Decrypt content from [{}] to [{}].", content, result);
        return result;
    }
}
