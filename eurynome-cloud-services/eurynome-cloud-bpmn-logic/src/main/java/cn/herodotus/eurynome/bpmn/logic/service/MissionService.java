package cn.herodotus.eurynome.bpmn.logic.service;

import cn.hutool.core.bean.BeanUtil;
import cn.herodotus.eurynome.bpmn.logic.constants.ProcessConstants;
import cn.herodotus.eurynome.bpmn.logic.dto.ClaimTask;
import cn.herodotus.eurynome.bpmn.logic.dto.CompleteTask;
import cn.herodotus.eurynome.bpmn.logic.dto.RejectTask;
import cn.herodotus.eurynome.bpmn.logic.dto.TurnTodoTask;
import cn.herodotus.eurynome.bpmn.logic.exception.ExecutionNotFoundException;
import cn.herodotus.eurynome.bpmn.logic.exception.ProcessInstanceNotFoundException;
import cn.herodotus.eurynome.bpmn.logic.exception.TaskNotFoundException;
import org.apache.commons.lang3.ObjectUtils;
import org.flowable.bpmn.model.FlowNode;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p> Description : 流程任务服务，为了和flowable engine task service区分 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/15 16:35
 */
@Service
public class MissionService extends BaseProcessService {

    @Autowired
    private BpmnModelService bpmnModelService;
    @Autowired
    private ProcessInstanceService processInstanceService;

    /**
     * 签收操作。
     * 当某一个节点配置为候选组之类的有多个人员的时候，需要进行签收操作：指定当前任务由谁办理，避免同时办理冲突。
     *
     * @param claimTask 参数
     * @throws IllegalArgumentException 方法参数错误
     * @throws TaskNotFoundException    任务实例不存在
     */
    public void claim(ClaimTask claimTask) throws IllegalArgumentException, TaskNotFoundException {

        if (BeanUtil.isNotEmpty(claimTask)) {

            this.findTaskEntity(claimTask.getTaskId());
            this.addComment(claimTask);
            this.getTaskService().claim(claimTask.getTaskId(), claimTask.getUserId());

        } else {
            throw new IllegalArgumentException("Argument can not empty!");
        }
    }

    /**
     * 完成审批。即完成当前节点，进入下一节点。
     * <p>
     * 此处编写该代码是为了减少重复，因为其他地方会用到。
     * <p>
     * 可以用flowable的rest api实现同样效果。
     * 1. 设置当前任务的Assignee。
     * POST: /process-api/runtime/tasks/{taskId}/identitylinks
     * RequestBody:
     * {
     * "user" : "kermit",
     * "type" : "assginee",
     * }
     * {@see :https://flowable.com/open-source/docs/bpmn/ch15-REST/#create-an-identity-link-on-a-task}
     * 这个效果相当于claim。也可以用claim替代。
     * <p>
     * 文档上使用的是“userId”作为参数，实际使用下来发现不对。
     * {@link org.flowable.rest.service.api.runtime.task.TaskIdentityLinkCollectionResource#createIdentityLink}
     * 类中方法的参数：RestIdentityLink，用的是user而不是userId
     * <p>
     * 2. 完成任务
     * POST: /process-api/runtime/tasks/{taskId}
     * RequestBody:
     * {
     * "action" : "complete",
     * "variables" : []
     * }
     * {@link :https://flowable.com/open-source/docs/bpmn/ch15-REST/#task-actions}
     * <p>
     * 这里目前没有用到variables。所以先搁置
     *
     * @param completeTask 参数
     * @throws IllegalArgumentException 方法参数错误
     * @throws TaskNotFoundException    任务实例不存在
     */
    public void complete(CompleteTask completeTask) throws IllegalArgumentException, TaskNotFoundException {

        if (BeanUtil.isNotEmpty(completeTask)) {

            this.findTaskEntity(completeTask.getTaskId());
            this.addComment(completeTask);
            this.getTaskService().setAssignee(completeTask.getTaskId(), completeTask.getUserId());
            this.getTaskService().complete(completeTask.getTaskId());

        } else {
            throw new IllegalArgumentException("Argument can not empty!");
        }
    }

    /**
     * 转办。当自己办不了的时候，转给其他人来办理。
     *
     * @param turnTodoTask 参数
     * @throws IllegalArgumentException 方法参数错误
     * @throws TaskNotFoundException    任务实例不存在
     */
    public void turnTodo(TurnTodoTask turnTodoTask) throws IllegalArgumentException, TaskNotFoundException {

        if (BeanUtil.isNotEmpty(turnTodoTask)) {

            TaskEntity currentTaskEntity = findTaskEntity(turnTodoTask.getTaskId());

            // 1.生成历史记录
            TaskEntity task = this.createSubTask(currentTaskEntity, turnTodoTask.getUserId());

            // 2.添加审批意见
            this.addComment(turnTodoTask);
            this.getTaskService().complete(task.getId());

            // 3.转办
            this.getTaskService().setAssignee(turnTodoTask.getTaskId(), turnTodoTask.getTurnToUserId());
            this.getTaskService().setOwner(turnTodoTask.getTaskId(), turnTodoTask.getUserId());
        } else {
            throw new IllegalArgumentException("Argument can not empty!");
        }
    }

    /**
     * 驳回任务
     *
     * @param rejectTask 参数
     * @throws TaskNotFoundException            TaskNotFoundException
     * @throws IllegalArgumentException         IllegalArgumentException
     * @throws ExecutionNotFoundException       ExecutionNotFoundException
     * @throws ProcessInstanceNotFoundException ProcessInstanceNotFoundException
     */
    public void reject(RejectTask rejectTask) throws TaskNotFoundException, IllegalArgumentException, ExecutionNotFoundException, ProcessInstanceNotFoundException {

        if (BeanUtil.isNotEmpty(rejectTask)) {

            TaskEntity taskEntity = findTaskEntity(rejectTask.getTaskId());

            // 1.设置审批人
            // TODO: 这里有空要去研究一下，下面语句和 taskService.setAssignee()。有什么不同。
            taskEntity.setAssignee(rejectTask.getUserId());
            this.getTaskService().saveTask(taskEntity);

            // 2.添加驳回意见
            this.addComment(rejectTask);

            // 3.如果退回节点为initiator 的节点，就需要给该节点设置userId，因此最初是状态，该节点的userId为空。
            FlowNode targetActivity = bpmnModelService.findFlowNodeByActivityId(taskEntity.getProcessDefinitionId(), rejectTask.getTargetActivityId());
            if (ObjectUtils.isNotEmpty(targetActivity)) {
                if (ProcessConstants.FLOW_SUBMITTER_ACTIVITY_ID.equals(targetActivity.getId())) {
                    ProcessInstance processInstance = processInstanceService.findProcessInstance(taskEntity.getProcessInstanceId());
                    processInstanceService.bindingUserIdToInitiatorActivity(rejectTask.getProcessInstanceId(), processInstance.getStartUserId());
                }
            }

            // 4.删除节点
            this.deleteActivity(rejectTask.getTargetActivityId(), taskEntity.getProcessInstanceId());

            // 5.判断节点是不是子流程内部的节点
            if (bpmnModelService.isSubprocess(taskEntity.getProcessDefinitionId(), rejectTask.getTargetActivityId(), taskEntity.getTaskDefinitionKey())) {
                // 6.1 子流程内部驳回
                String parentProcessInstanceId = this.findExecutionParentId(taskEntity.getExecutionId());
                this.moveExecutionsToSingleActivityId(parentProcessInstanceId, rejectTask.getTargetActivityId());
            } else {
                //6.2 普通驳回
                this.moveExecutionsToSingleActivityId(taskEntity.getProcessInstanceId(), rejectTask.getTargetActivityId());
            }
        } else {
            throw new IllegalArgumentException("Argument can not empty!");
        }
    }

}
