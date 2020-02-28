package cn.herodotus.eurynome.bpmn.logic.exception;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/19 16:34
 */
public class HistoricTaskInstanceNotFoundException extends ProcessException {

    public HistoricTaskInstanceNotFoundException() {
    }

    public HistoricTaskInstanceNotFoundException(String message) {
        super(message);
    }

    public HistoricTaskInstanceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public HistoricTaskInstanceNotFoundException(Throwable cause) {
        super(cause);
    }

    public HistoricTaskInstanceNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
