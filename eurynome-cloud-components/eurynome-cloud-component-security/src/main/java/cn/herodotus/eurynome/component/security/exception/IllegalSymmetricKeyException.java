package cn.herodotus.eurynome.component.security.exception;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/1/28 17:32
 */
public class IllegalSymmetricKeyException extends VerificationCodeException {

    public IllegalSymmetricKeyException(String message) {
        super(message);
    }
}
