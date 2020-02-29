package cn.herodotus.eurynome.bpmn.logic.dto;

import cn.herodotus.eurynome.component.common.definition.AbstractDomain;

import java.util.Arrays;
import java.util.Date;

/**
 * <p> Description : 工作流评审意见存储表 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/4 10:18
 */
public class Comment extends AbstractDomain {

    /**
     * 备注ID
     */
    private String commentId;
    /**
     * 备注类型
     */
    private String type;

    /**
     * 备注类型
     */
    private String typeName;

    /**
     * 提交时间
     */
    private Date submitTime;

    /**
     * 提交人员工ID。
     * 使用EmployeeID，作为flowable的用户ID
     */
    private String employeeId;
    /**
     * 提交人姓名
     */
    private String employeeName;

    /**
     * 为了今后使用方便，减少查询。用flowable的 user lastname存部门
     */
    private String departmentName;

    /**
     * 为了今后使用方便，减少查询。用flowable的 user firstname存单位
     */
    private String organizationName;
    /**
     * 任务ID
     */
    private String taskId;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 流程实例ID
     */
    private String processInstanceId;
    /**
     * 意见信息
     */
    private String message;
    /**
     * 评论全信息
     */
    private byte[] fullMessage;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public byte[] getFullMessage() {
        return fullMessage;
    }

    public void setFullMessage(byte[] fullMessage) {
        this.fullMessage = fullMessage;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId='" + commentId + '\'' +
                ", type='" + type + '\'' +
                ", typeName='" + typeName + '\'' +
                ", submitTime=" + submitTime +
                ", employeeId='" + employeeId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", organizationName='" + organizationName + '\'' +
                ", taskId='" + taskId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", message='" + message + '\'' +
                ", fullMessage=" + Arrays.toString(fullMessage) +
                '}';
    }
}
