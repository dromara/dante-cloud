package cn.herodotus.eurynome.bpmn.logic.dto;

import cn.herodotus.eurynome.component.data.base.dto.BaseDTO;

/**
 * <p> Description : 流程参数基础类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/19 11:21
 */
public class BaseProcessDTO extends BaseDTO {

    /**
     * 任务id 必填
     */
    private String taskId;

    /**
     * 操作人code 必填
     */
    private String userId;
    /**
     * 审批意见 必填
     */
    private String message;
    /**
     * 流程实例的id 必填
     */
    private String processInstanceId;
    /**
     * 审批类型 必填
     */
    private String type;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "BaseProcessDTO{" +
                "taskId='" + taskId + '\'' +
                ", userId='" + userId + '\'' +
                ", message='" + message + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
