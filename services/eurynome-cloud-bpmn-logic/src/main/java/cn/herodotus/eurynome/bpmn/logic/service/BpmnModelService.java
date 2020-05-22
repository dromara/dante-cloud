package cn.herodotus.eurynome.bpmn.logic.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/16 16:21
 */
@Service
public class BpmnModelService extends BaseProcessService {

    public BpmnModel getBpmnModelByProcessDefinitionId(String processDefinitionId) {
        return getRepositoryService().getBpmnModel(processDefinitionId);

    }

    public List<FlowNode> findFlowNodes(String processDefinitionId) {
        List<FlowNode> flowNodes = new ArrayList<>();
        BpmnModel bpmnModel = this.getBpmnModelByProcessDefinitionId(processDefinitionId);
        Process process = bpmnModel.getMainProcess();
        Collection<FlowElement> list = process.getFlowElements();
        list.forEach(flowElement -> {
            if (flowElement instanceof FlowNode) {
                flowNodes.add((FlowNode) flowElement);
            }
        });
        return flowNodes;
    }

    public List<EndEvent> findEndFlowElement(String processDefinitionId) {
        BpmnModel bpmnModel = this.getBpmnModelByProcessDefinitionId(processDefinitionId);
        if (bpmnModel != null) {
            Process process = bpmnModel.getMainProcess();
            return process.findFlowElementsOfType(EndEvent.class);
        } else {
            return null;
        }
    }


    public FlowNode findMainProcessActivityByActivityId(String processDefinitionId, String activityId) {
        FlowNode flowNode = null;
        BpmnModel bpmnModel = this.getBpmnModelByProcessDefinitionId(processDefinitionId);
        Process process = bpmnModel.getMainProcess();
        FlowElement flowElement = process.getFlowElement(activityId);
        if (flowElement != null) {
            flowNode = (FlowNode) flowElement;
        }
        return flowNode;
    }

    boolean isSubprocess(String processDefinitionId, String activityId, String taskDefinitionKey) {
        return checkActivitySubprocessByActivityId(processDefinitionId, activityId) && checkActivitySubprocessByActivityId(processDefinitionId, taskDefinitionKey);
    }

    public boolean checkActivitySubprocessByActivityId(String processDefinitionId, String activityId) {
        List<FlowNode> activities = this.findFlowNodesByActivityId(processDefinitionId, activityId);
        return !CollectionUtils.isNotEmpty(activities);
    }

    public FlowNode findFlowNodeByActivityId(String processDefinitionId, String activityId) {
        FlowNode flowNode = null;
        BpmnModel bpmnModel = this.getBpmnModelByProcessDefinitionId(processDefinitionId);
        List<Process> processes = bpmnModel.getProcesses();
        for (Process process : processes) {
            FlowElement flowElement = process.getFlowElementMap().get(activityId);
            if (ObjectUtils.isNotEmpty(flowElement)) {
                flowNode = (FlowNode) flowElement;
                break;
            }
        }
        return flowNode;
    }

    public List<FlowNode> findFlowNodesByActivityId(String processDefinitionId, String activityId) {
        List<FlowNode> flowNodes = new ArrayList<>();
        BpmnModel bpmnModel = this.getBpmnModelByProcessDefinitionId(processDefinitionId);
        List<Process> processes = bpmnModel.getProcesses();
        for (Process process : processes) {
            FlowElement flowElement = process.getFlowElement(activityId);
            if (ObjectUtils.isNotEmpty(flowElement)) {
                FlowNode flowNode = (FlowNode) flowElement;
                flowNodes.add(flowNode);
            }
        }
        return flowNodes;
    }


    public List<Activity> findActivitiesByActivityId(String processDefinitionId, String activityId) {
        List<Activity> activities = new ArrayList<>();
        BpmnModel bpmnModel = this.getBpmnModelByProcessDefinitionId(processDefinitionId);
        List<Process> processes = bpmnModel.getProcesses();
        for (Process process : processes) {
            FlowElement flowElement = process.getFlowElement(activityId);
            if (flowElement != null) {
                Activity activity = (Activity) flowElement;
                activities.add(activity);
            }
        }
        return activities;
    }

    public Activity findActivityByName(String processDefinitionId, String name) {
        Activity activity = null;
        BpmnModel bpmnModel = this.getBpmnModelByProcessDefinitionId(processDefinitionId);
        Process process = bpmnModel.getMainProcess();
        Collection<FlowElement> flowElements = process.getFlowElements();
        for (FlowElement flowElement : flowElements) {
            if (StringUtils.isNotBlank(name)) {
                if (name.equals(flowElement.getName())) {
                    activity = (Activity) flowElement;
                    break;
                }
            }
        }
        return activity;
    }

    /**
     * 获取可以驳回的流程节点
     *
     * @param processInstanceId 流程实例ID
     * @param taskId            任务ID
     */
    public void getDismissableActivitiesByProcessInstanceId(String processInstanceId, String taskId) {

    }
}
