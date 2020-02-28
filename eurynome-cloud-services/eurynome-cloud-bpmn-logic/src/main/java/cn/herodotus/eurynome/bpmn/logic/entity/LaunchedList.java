package cn.herodotus.eurynome.bpmn.logic.entity;

import cn.herodotus.eurynome.component.common.domain.AbstractDomain;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p> Description : 我发起的流程事项 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/15 11:13
 */
@Entity
@Table(name = "act_task_launched_list")
public class LaunchedList extends AbstractDomain {

    @Id
    @Column(name = "process_instance_id")
    private String processInstanceId;

    @Column(name = "process_definition_id")
    private String processDefinitionId;

    @Column(name = "process_definition_name")
    private String processDefinitionName;

    @Column(name = "form_name")
    private String formName;

    @Column(name = "tenant_id")
    private String tenantId;

    @Column(name = "business_key")
    private String businessKey;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "start_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @Column(name = "end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    @Column(name = "duration")
    private BigDecimal duration;

    @Column(name = "is_finished")
    private boolean finished;

    @Column(name = "initiator_id")
    private String initiatorId;

    @Column(name = "initiator_name")
    private String initiatorName;

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
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

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getDuration() {
        return duration;
    }

    public void setDuration(BigDecimal duration) {
        this.duration = duration;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
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

    public String getProcessDefinitionName() {
        return processDefinitionName;
    }

    public void setProcessDefinitionName(String processDefinitionName) {
        this.processDefinitionName = processDefinitionName;
    }

    @Override
    public String toString() {
        return "LaunchedList{" +
                "processInstanceId='" + processInstanceId + '\'' +
                ", processDefinitionId='" + processDefinitionId + '\'' +
                ", processDefinitionName='" + processDefinitionName + '\'' +
                ", formName='" + formName + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", businessKey='" + businessKey + '\'' +
                ", taskName='" + taskName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", duration=" + duration +
                ", finished=" + finished +
                ", initiatorId='" + initiatorId + '\'' +
                ", initiatorName='" + initiatorName + '\'' +
                '}';
    }
}
