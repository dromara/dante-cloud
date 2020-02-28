package cn.herodotus.eurynome.component.security.utils;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.herodotus.eurynome.component.common.constants.SymbolConstants;
import cn.herodotus.eurynome.component.security.exception.IllegalSymmetricKeyException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/1/28 17:25
 */
@Slf4j
public class SymmetricUtils {

    private static String encryptedRealSecretKey(String symmetricKey) {
        String realSecretKey = RandomUtil.randomString(16);
        log.trace("[Luban] |- Generate Random Secret Key is : [{}]", realSecretKey);

        AES ase = SecureUtil.aes(symmetricKey.getBytes());
        String encryptedRealSecretKey = ase.encryptHex(realSecretKey);
        log.trace("[Luban] |- Generate Encrypt Hex Secret Key is : [{}]", encryptedRealSecretKey);

        return encryptedRealSecretKey;
    }

    public static String getEncryptedSymmetricKey() {
        String symmetricKey = RandomUtil.randomString(16);
        String realSecretKey = encryptedRealSecretKey(symmetricKey);
        log.trace("[Luban] |- Generate Symmetric Key is : [{}]", realSecretKey);

        StringBuilder builder = new StringBuilder();
        builder.append(symmetricKey);
        builder.append(SymbolConstants.FORWARD_SLASH);
        builder.append(realSecretKey);
        return builder.toString();
    }

    public static byte[] getDecryptdSymmetricKey(String key) {
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
