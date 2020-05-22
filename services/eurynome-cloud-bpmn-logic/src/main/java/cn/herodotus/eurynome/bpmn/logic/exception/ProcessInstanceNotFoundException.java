package cn.herodotus.eurynome.bpmn.logic.exception;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/17 11:21
 */
public class ProcessInstanceNotFoundException extends ProcessException {

    public ProcessInstanceNotFoundException() {
    }

    public ProcessInstanceNotFoundException(String message) {
        super(message);
    }

    public ProcessInstanceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcessInstanceNotFoundException(Throwable cause) {
        super(cause);
    }

    public ProcessInstanceNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
