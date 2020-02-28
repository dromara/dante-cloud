package cn.herodotus.eurynome.bpmn.logic.service;

import cn.herodotus.eurynome.bpmn.logic.constants.ProcessConstants;
import cn.herodotus.eurynome.component.common.constants.SymbolConstants;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.idm.api.User;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/19 15:44
 */
@Service
public class TaskInstanceApproverService extends BaseProcessService{

    private List<User> users = new ArrayList<>();
    private Map<String, List<HistoricTaskInstance>> taskInstanceIndex = new HashMap<>(8);

    public Map<String, String> getApproverIndex(String processInstanceId) {
        List<HistoricTaskInstance> historicTaskInstances = this.findCompletedHistoricTaskInstances(processInstanceId);
        this.parseHistoricTaskInstances(historicTaskInstances);
        return parseApproverIndex(processInstanceId);
    }

    private void parseHistoricTaskInstances(List<HistoricTaskInstance> historicTaskInstances) {
        List<String> userIds = new ArrayList<>();

        historicTaskInstances.forEach(historicTaskInstance -> {

            if (StringUtils.isNotBlank(historicTaskInstance.getAssignee())) {
                userIds.add(historicTaskInstance.getAssignee());
            }

            String taskDefinitionKey = historicTaskInstance.getTaskDefinitionKey();
            if (taskInstanceIndex.containsKey(historicTaskInstance.getTaskDefinitionKey())) {
                taskInstanceIndex.get(taskDefinitionKey).add(historicTaskInstance);
            } else {
                List<HistoricTaskInstance> taskInstances = new ArrayList<>();
                taskInstances.add(historicTaskInstance);
                taskInstanceIndex.put(taskDefinitionKey, taskInstances);
            }
        });

        if (CollectionUtils.isNotEmpty(userIds)) {
            users = this.findUsers(userIds);
        }
    }

    private Map<String, String> parseApproverIndex(String processInstanceId) {

        Map<String, User> userIndex = users.stream().collect(Collectors.toMap(User::getId, user -> user));
        Map<String, String> approverIndex = new HashMap<>(8);

        taskInstanceIndex.forEach((activityId, taskInstances) -> {
            StringBuilder displayApprovers = new StringBuilder();

            taskInstances.forEach(taskInstance -> {
                if (!taskInstance.getTaskDefinitionKey().equals(ProcessConstants.FLOW_SUBMITTER_ACTIVITY_ID)) {
                    User user = userIndex.get(taskInstance.getAssignee());
                    if (ObjectUtils.isNotEmpty(user)) {
                        if (StringUtils.indexOf(displayApprovers, user.getDisplayName()) == -1) {
                            displayApprovers.append(user.getDisplayName()).append(SymbolConstants.COMMA);
                        }
                    }
                } else {
                    User user = this.findStartUser(processInstanceId);
                    if (ObjectUtils.isNotEmpty(user)) {
                        displayApprovers.append(user.getDisplayName()).append(SymbolConstants.COMMA);
                    }
                }
            });

            approverIndex.put(activityId, StringUtils.removeEnd(displayApprovers.toString(), SymbolConstants.COMMA));
        });

        return approverIndex;
    }

}
