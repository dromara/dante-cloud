package cn.herodotus.eurynome.common.exception;

/**
 * <p> Description : 非法参数错误 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/17 11:17
 */
public class IllegalArgumentException extends PlatformException {

    public IllegalArgumentException() {
    }

    public IllegalArgumentException(String message) {
        super(message);
    }

    public IllegalArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalArgumentException(Throwable cause) {
        super(cause);
    }

    public IllegalArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
