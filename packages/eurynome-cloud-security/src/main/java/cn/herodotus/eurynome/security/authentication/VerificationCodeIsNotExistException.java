package cn.herodotus.eurynome.security.authentication;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/1/26 18:11
 */
public class VerificationCodeIsNotExistException extends VerificationCodeException {

    public VerificationCodeIsNotExistException(String message) {
        super(message);
    }
}
