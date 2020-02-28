package cn.herodotus.eurynome.bpmn.logic.service;

import cn.hutool.core.util.IdUtil;
import cn.herodotus.eurynome.bpmn.logic.dto.BaseProcessDTO;
import cn.herodotus.eurynome.bpmn.logic.exception.ExecutionNotFoundException;
import cn.herodotus.eurynome.bpmn.logic.exception.HistoricTaskInstanceNotFoundException;
import cn.herodotus.eurynome.bpmn.logic.exception.ProcessInstanceNotFoundException;
import cn.herodotus.eurynome.bpmn.logic.exception.TaskNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.flowable.engine.*;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.idm.api.User;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/15 16:34
 */
@Slf4j
@Transactional
public abstract class BaseProcessService {

    @Autowired
    private ManagementService managementService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    protected IdentityService identityService;
    @Autowired
    private RuntimeActivityInstanceService runtimeActivityInstanceService;
    @Autowired
    private HistoryActivityInstanceService historyActivityInstanceService;
    @Autowired
    private CommentService commentService;

    public TaskService getTaskService() {
        return taskService;
    }

    public ManagementService getManagementService() {
        return managementService;
    }

    public RuntimeService getRuntimeService() {
        return runtimeService;
    }

    public RepositoryService getRepositoryService() {
        return repositoryService;
    }

    public HistoryService getHistoryService() {
        return historyService;
    }

    public IdentityService getIdentityService() {
        return identityService;
    }

    public RuntimeActivityInstanceService getRuntimeActivityInstanceService() {
        return runtimeActivityInstanceService;
    }

    /**
     * 添加审批意见
     *
     * @param processInstanceId 流程实例id
     * @param taskId            任务id
     * @param userId            处理人工号
     * @param type              审批类型
     * @param message           审批意见
     */
    protected void addComment(String processInstanceId, String taskId, String userId, String type, String message) {
        //1.添加备注
        commentService.addComment(processInstanceId, taskId, userId, type, message);
        //TODO 2.修改扩展的流程实例表的状态以备查询待办的时候能带出来状态
        //TODO 3.发送mongodb的数据到消息队列里面
    }

    protected void addComment(String processInstanceId, String userId, String type, String message) {
        this.addComment(processInstanceId, null, userId, type, message);
    }

    protected void addComment(BaseProcessDTO baseProcessDTO) {
        this.addComment(baseProcessDTO.getProcessInstanceId(), baseProcessDTO.getTaskId(), baseProcessDTO.getUserId(), baseProcessDTO.getType(), baseProcessDTO.getMessage());
    }

    protected void deleteActivity(String processInstanceId, String activityId) {
        runtimeActivityInstanceService.deleteRuntimeActivityInstance(processInstanceId, activityId);
        log.debug("[Luban] |- Flowable deleteRuntimeActivity");

        historyActivityInstanceService.deleteHistoryActivityInstance(processInstanceId, activityId);
        log.debug("[Luban] |- Flowable deleteHistoryActivity");
    }

    /**
     * 执行跳转
     */
    protected void moveExecutionsToSingleActivityId(String processInstanceId, String activityId) throws ExecutionNotFoundException {
        List<Execution> executions = this.getRuntimeService().createExecutionQuery().parentId(processInstanceId).list();
        if (CollectionUtils.isNotEmpty(executions)) {
            List<String> executionIds = executions.stream().map(Execution::getId).collect(Collectors.toList());
            this.getRuntimeService().createChangeActivityStateBuilder().moveExecutionsToSingleActivityId(executionIds, activityId).changeState();
        } else {
            throw new ExecutionNotFoundException("Executions Can not found!");
        }
    }

    protected Execution findExecution(String executionId) throws ExecutionNotFoundException {
        Execution execution = this.getRuntimeService().createExecutionQuery().executionId(executionId).singleResult();
        if (ObjectUtils.isNotEmpty(execution)) {
            return execution;
        } else {
            throw new ExecutionNotFoundException("Execution Can not found!");
        }
    }

    protected TaskEntity findTaskEntity(String taskId) {
        TaskEntity taskEntity = (TaskEntity) this.getTaskService().createTaskQuery().taskId(taskId).singleResult();
        if (ObjectUtils.isNotEmpty(taskEntity)) {
            return taskEntity;
        } else {
            throw new TaskNotFoundException("Task Instance not found!");
        }
    }

    protected List<User> findUsers(List<String> userIds) {
        return this.getIdentityService().createUserQuery().userIds(userIds).list();
    }

    protected User findUser(String userId) {
        return this.getIdentityService().createUserQuery().userId(userId).singleResult();
    }

    protected User findStartUser(String processInstanceId) {
        ProcessInstance processInstance  = this.findProcessInstance(processInstanceId);
        String startUserId = processInstance.getStartUserId();
        return this.findUser(startUserId);
    }

    protected String findExecutionParentId(String executionId) throws ExecutionNotFoundException {
        return this.findExecution(executionId).getParentId();
    }

    protected List<HistoricTaskInstance> findCompletedHistoricTaskInstances(String processInstanceId) throws HistoricTaskInstanceNotFoundException {
        List<HistoricTaskInstance> historicTaskInstances = this.getHistoryService().createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).finished().list();
        if (CollectionUtils.isNotEmpty(historicTaskInstances)) {
            return historicTaskInstances;
        } else {
            throw new HistoricTaskInstanceNotFoundException("HistoricTaskInstance can not found!");
        }
    }



    protected TaskEntity createSubTask(TaskEntity parentTaskEntity, String assignee) {
        return this.createSubTask(parentTaskEntity, parentTaskEntity.getId(), assignee);
    }

    protected ProcessInstance findProcessInstance(String processInstanceId) throws ProcessInstanceNotFoundException {
        ProcessInstance processInstance = this.getRuntimeService().createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (ObjectUtils.isNotEmpty(processInstance)) {
            return processInstance;
        }else {
            throw new ProcessInstanceNotFoundException("ProcessInstanceId is not exist!");
        }
    }

    /**
     * 创建子任务
     *
     * @param parentTaskEntity 创建子任务
     * @param assignee         子任务的执行人
     * @return
     */
    protected TaskEntity createSubTask(TaskEntity parentTaskEntity, String parentTaskId, String assignee) {
        TaskEntity taskEntity = null;
        if (ObjectUtils.isNotEmpty(parentTaskEntity)) {
            //1.生成子任务
            taskEntity = (TaskEntity) taskService.newTask(IdUtil.randomUUID());
            taskEntity.setCategory(parentTaskEntity.getCategory());
            taskEntity.setDescription(parentTaskEntity.getDescription());
            taskEntity.setTenantId(parentTaskEntity.getTenantId());
            taskEntity.setAssignee(assignee);
            taskEntity.setName(parentTaskEntity.getName());
            taskEntity.setParentTaskId(parentTaskId);
            taskEntity.setProcessDefinitionId(parentTaskEntity.getProcessDefinitionId());
            taskEntity.setProcessInstanceId(parentTaskEntity.getProcessInstanceId());
            taskEntity.setTaskDefinitionKey(parentTaskEntity.getTaskDefinitionKey());
            taskEntity.setTaskDefinitionId(parentTaskEntity.getTaskDefinitionId());
            taskEntity.setPriority(parentTaskEntity.getPriority());
            taskEntity.setCreateTime(new Date());
            taskService.saveTask(taskEntity);
        }
        return taskEntity;
    }
}
