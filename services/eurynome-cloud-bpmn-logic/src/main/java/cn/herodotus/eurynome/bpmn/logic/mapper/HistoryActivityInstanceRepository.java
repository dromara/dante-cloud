package cn.herodotus.eurynome.bpmn.logic.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/16 16:12
 */
@Mapper
@Repository
public interface HistoryActivityInstanceRepository {

    /**
     * 退回到指定流程节点。
     * 即把指定节点之后已经走过的节点全部删除。
     * @param processInstanceId 流程实例ID
     * @param activityInstanceId 节点实例ID
     */
    void deleteHistoryActivityInstance(String processInstanceId, String activityInstanceId);
}
