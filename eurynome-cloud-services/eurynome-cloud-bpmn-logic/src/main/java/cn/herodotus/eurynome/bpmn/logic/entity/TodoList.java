package cn.herodotus.eurynome.bpmn.logic.entity;

import cn.herodotus.eurynome.component.common.definition.AbstractDomain;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/9 9:00
 */
@Entity
@Table(name = "act_task_todo_list")
public class TodoList extends AbstractDomain {

    @Id
    @Column(name = "task_id")
    private String taskId;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "form_name")
    private String formName;

    @Column(name = "tenant_id")
    private String tenantId;

    @Column(name = "business_key")
    private String businessKey;

    @Column(name = "process_definition_id")
    private String processDefinitionId;

    @Column(name = "process_definition_name")
    private String processDefinitionName;

    @Column(name = "process_instance_id")
    private String processInstanceId;

    @Column(name = "start_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @Column(name = "initiator_id")
    private String initiatorId;

    @Column(name = "initiator_name")
    private String initiatorName;

    @Column(name = "assignee_id")
    private String assigneeId;

    @Column(name = "link_user_id")
    private String linkUserId;

    @Column(name = "candidate_user_id")
    private String candidateUserId;

    @Column(name = "gourp_id")
    private String gourpId;

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

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getProcessDefinitionName() {
        return processDefinitionName;
    }

    public void setProcessDefinitionName(String processDefinitionName) {
        this.processDefinitionName = processDefinitionName;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(String assigneeId) {
        this.assigneeId = assigneeId;
    }

    public String getLinkUserId() {
        return linkUserId;
    }

    public void setLinkUserId(String linkUserId) {
        this.linkUserId = linkUserId;
    }

    public String getCandidateUserId() {
        return candidateUserId;
    }

    public void setCandidateUserId(String candidateUserId) {
        this.candidateUserId = candidateUserId;
    }

    public String getGourpId() {
        return gourpId;
    }

    public void setGourpId(String gourpId) {
        this.gourpId = gourpId;
    }

    public String getInitiatorId() {
        return initiatorId;
    }

    public void setInitiatorId(String initiatorId) {
        this.initiatorId = initiatorId;
    }

    public String getInitiatorName() {
        return initiatorName;
    }

    public void setInitiatorName(String initiatorName) {
        this.initiatorName = initiatorName;
    }

    @Override
    public String toString() {
        return "TodoList{" +
                "taskId='" + taskId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", formName='" + formName + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", businessKey='" + businessKey + '\'' +
                ", processDefinitionId='" + processDefinitionId + '\'' +
                ", processDefinitionName='" + processDefinitionName + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", startTime=" + startTime +
                ", initiatorId='" + initiatorId + '\'' +
                ", initiatorName='" + initiatorName + '\'' +
                ", assigneeId='" + assigneeId + '\'' +
                ", linkUserId='" + linkUserId + '\'' +
                ", candidateUserId='" + candidateUserId + '\'' +
                ", gourpId='" + gourpId + '\'' +
                '}';
    }
}
