package cn.herodotus.eurynome.bpmn.logic.service;

import cn.herodotus.eurynome.bpmn.logic.constants.CommentType;
import cn.herodotus.eurynome.bpmn.logic.constants.ProcessConstants;
import cn.herodotus.eurynome.bpmn.logic.dto.TaskApprover;
import cn.herodotus.eurynome.bpmn.logic.exception.ProcessInstanceNotFoundException;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.constants.BpmnXMLConstants;
import org.flowable.editor.language.json.converter.util.CollectionUtils;
import org.flowable.engine.runtime.ActivityInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/16 13:40
 */
@Service
public class ProcessInstanceService extends BaseProcessService {

    @Autowired
    private TaskInstanceApproverService taskInstanceApproverService;

    /**
     * 撤回自己发起的流程
     *
     * @param processInstanceId 实例ID
     * @param activityId        流程默认的起始任务节点
     * @param userId            操作人员ID
     * @param message           说明信息
     * @throws IllegalArgumentException         参数不能为空
     * @throws ProcessInstanceNotFoundException 没有查询到processInstance，不做任何操作
     */
    public void revoke(String processInstanceId, String activityId, String userId, String message) throws IllegalArgumentException, ProcessInstanceNotFoundException {

        if (StringUtils.isNotBlank(processInstanceId) && StringUtils.isNotBlank(activityId)) {
            ProcessInstance processInstance = findProcessInstance(processInstanceId);
            if (ObjectUtils.isNotEmpty(processInstance)) {

                //1.添加撤回意见
                this.addComment(processInstanceId, userId, CommentType.REVOKE.name(), message);

                //2.设置提交人
                bindingUserIdToInitiatorActivity(processInstanceId, userId);

                //3.删除运行和历史的节点信息
                this.deleteActivity(processInstanceId, activityId);

                //4.执行跳转
                this.moveExecutionsToSingleActivityId(processInstanceId, activityId);

            } else {
                throw new ProcessInstanceNotFoundException("ProcessInstanceId is not exist!");
            }
        } else {
            throw new IllegalArgumentException("Argument can not empty!");
        }
    }

    /**
     * 给initiator绑定userId， 一般用于撤回或者驳回时。
     *
     * @param processInstanceId 流程实例ID
     * @param userId            用户ID
     */
    public void bindingUserIdToInitiatorActivity(String processInstanceId, String userId) {
        this.getRuntimeService().setVariable(processInstanceId, ProcessConstants.FLOW_SUBMITTER_VARIABLE, userId);
    }

    /**
     * 获取驳回节点
     * TODO：后期验证的时候，要根据实际情况进行优化，原实例太复杂
     * @param processInstanceId
     * @param taskId
     * @return
     */
    public List<TaskApprover> getDismissableActivities(String processInstanceId, String taskId) {
        TaskEntity taskEntity = this.findTaskEntity(taskId);
        String currentActivityId = taskEntity.getTaskDefinitionKey();

        List<ActivityInstance> activityInstances = this.getRuntimeActivityInstanceService().findAllCompleleActivity(processInstanceId, currentActivityId);

        //分组节点
        int count = 0;
        Map<ActivityInstance, List<ActivityInstance>> parallelGatewayUserTasks = new HashMap<>(8);
        List<ActivityInstance> ordinaryUserTasks = new ArrayList<>();
        ActivityInstance currActivityInstance = null;
        for (ActivityInstance activityInstance : activityInstances) {
            if (BpmnXMLConstants.ELEMENT_GATEWAY_PARALLEL.equals(activityInstance.getActivityType())) {
                count++;
                if (count % 2 != 0) {
                    List<ActivityInstance> datas = new ArrayList<>();
                    currActivityInstance = activityInstance;
                    parallelGatewayUserTasks.put(currActivityInstance, datas);
                }
            }
            if (BpmnXMLConstants.ELEMENT_TASK_USER.equals(activityInstance.getActivityType())) {
                if (count % 2 == 0) {
                    ordinaryUserTasks.add(activityInstance);
                } else {
                    if (parallelGatewayUserTasks.containsKey(currActivityInstance)) {
                        parallelGatewayUserTasks.get(currActivityInstance).add(activityInstance);
                    }
                }
            }
        }

        Map<String, String> activityIdApproverIndex = taskInstanceApproverService.getApproverIndex(processInstanceId);

        List<TaskApprover> dismissableActivities = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(ordinaryUserTasks)) {
            ordinaryUserTasks.forEach(activityInstance -> {
                TaskApprover taskApprover = new TaskApprover();
                taskApprover.setActivityId(activityInstance.getActivityId());
                taskApprover.setActivityName(activityInstance.getActivityName());
                taskApprover.setEndTime(activityInstance.getEndTime());
                taskApprover.setUserName(activityIdApproverIndex.get(activityInstance.getActivityId()));
                dismissableActivities.add(taskApprover);
            });
        }

        //组装会签节点数据
        if (MapUtils.isNotEmpty(parallelGatewayUserTasks)) {
            parallelGatewayUserTasks.forEach((activity, activities) -> {
                TaskApprover taskApprover = new TaskApprover();
                taskApprover.setActivityId(activity.getActivityId());
                taskApprover.setEndTime(activity.getEndTime());
                StringBuffer nodeNames = new StringBuffer("会签:");
                StringBuffer userNames = new StringBuffer("审批人员:");
                if (CollectionUtils.isNotEmpty(activities)) {
                    activities.forEach(activityInstance -> {
                        nodeNames.append(activityInstance.getActivityName()).append(",");
                        userNames.append(activityIdApproverIndex.get(activityInstance.getActivityId())).append(",");
                    });
                    taskApprover.setActivityName(nodeNames.toString());
                    taskApprover.setUserName(userNames.toString());
                    dismissableActivities.add(taskApprover);
                }
            });
        }
        //去重合并
        List<TaskApprover> datas = dismissableActivities.stream().collect(
                Collectors.collectingAndThen(Collectors.toCollection(() ->
                        new TreeSet<>(Comparator.comparing(TaskApprover::getActivityId))), ArrayList::new));

        //排序
        datas.sort(Comparator.comparing(TaskApprover::getEndTime));
        return datas;
    }
}
