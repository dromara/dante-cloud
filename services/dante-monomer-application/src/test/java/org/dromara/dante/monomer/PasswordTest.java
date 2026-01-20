package org.dromara.dante.monomer;

import cn.herodotus.engine.core.identity.utils.SecurityUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2026/1/20 17:00
 */
class PasswordTest {

    @Test
    void testPasswordMatch() {

        String password = "!QAZ#EDC567";
        String encryptedPassword = SecurityUtils.encrypt(password);

        System.out.println("Encrypted: " + encryptedPassword);

        boolean isMatched = SecurityUtils.matches(password, encryptedPassword);

        System.out.println("isMatched: " + isMatched);

        Assertions.assertTrue(isMatched, "Password not matched.");
    }
}
