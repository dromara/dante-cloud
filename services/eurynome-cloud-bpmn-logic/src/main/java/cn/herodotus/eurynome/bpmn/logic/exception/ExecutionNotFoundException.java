package cn.herodotus.eurynome.bpmn.logic.exception;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/19 11:48
 */
public class ExecutionNotFoundException extends ProcessException {

    public ExecutionNotFoundException() {
    }

    public ExecutionNotFoundException(String message) {
        super(message);
    }

    public ExecutionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExecutionNotFoundException(Throwable cause) {
        super(cause);
    }

    public ExecutionNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
