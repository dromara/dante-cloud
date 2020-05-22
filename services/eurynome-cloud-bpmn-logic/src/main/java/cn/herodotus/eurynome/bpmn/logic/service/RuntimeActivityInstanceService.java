package cn.herodotus.eurynome.bpmn.logic.service;

import cn.herodotus.eurynome.bpmn.logic.exception.TaskNotFoundException;
import cn.herodotus.eurynome.bpmn.logic.mapper.RuntimeActivityInstanceRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.flowable.engine.runtime.ActivityInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/16 13:23
 */
@Service
public class RuntimeActivityInstanceService extends BaseProcessService {

    @Autowired
    private RuntimeActivityInstanceRepository runtimeActivityInstanceRepository;

    public void deleteRuntimeActivityInstance(String processInstanceId, String activityInstanceId) {
        runtimeActivityInstanceRepository.deleteRuntimeActivityInstance(processInstanceId, activityInstanceId);
    }

    /**
     * 获取当前已完成的用户实例
     *
     * @param processInstanceId 流程实例ID
     * @return
     */
    public List<ActivityInstance> findAllCompletedUserTask(String processInstanceId) {
        String sql = "SELECT ara.* FROM act_ru_actinst ara WHERE ara.act_type_ = 'userTask' AND " +
                "ara.proc_inst_id_ = #{processInstanceId} AND ara.end_time_ IS NOT NULL";

        return this.getRuntimeService().createNativeActivityInstanceQuery().sql(sql)
                .parameter("processInstanceId", processInstanceId)
                .list();
    }

    /**
     * 获取非当前、已完成的平行网关
     *
     * @param processInstanceId 流程实例ID
     * @param activityId        当前节点
     * @return
     */
    public List<ActivityInstance> findAllNonCurrentCompletedParallelGateway(String processInstanceId, String activityId) {
        String sql = "SELECT ara.id_, ara.rev_, ara.proc_def_id_, ara.proc_inst_id_, ara.execution_id_, ara.act_id_, ara.task_id_, " +
                "ara.call_proc_inst_id_, ara.act_name_, ara.act_type_, ara.assignee_, ara.start_time_, MAX ( ara.end_time_ ) AS end_time_, " +
                "ara.duration_, ara.delete_reason_, ara.tenant_id_  FROM act_ru_actinst ara  WHERE ara.act_type_ = 'parallelGateway' " +
                "AND ara.proc_inst_id_ = #{processInstanceId} AND ara.end_time_ IS NOT NULL AND ara.act_id_ <> #{activityId} " +
                "GROUP BY ara.id_, ara.act_id_";
        return this.getRuntimeService().createNativeActivityInstanceQuery().sql(sql)
                .parameter("processInstanceId", processInstanceId)
                .parameter("activityId", activityId)
                .list();
    }


    public List<ActivityInstance> findAllCompleleActivity(String processInstanceId, String taskDefinitionKey) throws TaskNotFoundException {
        List<ActivityInstance> userTasks = this.findAllCompletedUserTask(processInstanceId);
        List<ActivityInstance> parallelGatewaies = this.findAllNonCurrentCompletedParallelGateway(processInstanceId, taskDefinitionKey);

        if (CollectionUtils.isNotEmpty(parallelGatewaies)) {
            userTasks.addAll(parallelGatewaies);
            userTasks.sort(Comparator.comparing(ActivityInstance::getEndTime));
        }

        return userTasks;
    }
}
