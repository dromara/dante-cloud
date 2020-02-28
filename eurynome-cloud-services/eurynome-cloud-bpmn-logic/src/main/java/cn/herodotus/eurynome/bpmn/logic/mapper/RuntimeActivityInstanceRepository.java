package cn.herodotus.eurynome.bpmn.logic.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p> Description : 运行时节点实例 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/16 11:48
 */
@Mapper
@Repository
public interface RuntimeActivityInstanceRepository {

    /**
     * 退回到指定流程节点。
     * 即把指定节点之后已经走过的节点全部删除。
     * @param processInstanceId 流程实例ID
     * @param activityInstanceId 节点实例ID
     */
    void deleteRuntimeActivityInstance(String processInstanceId, String activityInstanceId);
}
