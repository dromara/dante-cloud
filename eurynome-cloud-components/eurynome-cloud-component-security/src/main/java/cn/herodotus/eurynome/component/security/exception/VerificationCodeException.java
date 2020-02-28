package cn.herodotus.eurynome.component.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/29 13:46
 */
public class VerificationCodeException extends AuthenticationException {

    public VerificationCodeException(String message) {
        super(message);
    }
}
