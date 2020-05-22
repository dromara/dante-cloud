package cn.herodotus.eurynome.bpmn.logic.mapper;

import cn.herodotus.eurynome.bpmn.logic.dto.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p> Description : 备注repository </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/17 9:19
 */
@Mapper
@Repository
public interface CommentRepository {

    /**
     * 通过流程实例id获取审批意见列表
     * @param processInstanceId 流程实例id
     * @return
     */
    public List<Comment> getCommentsByProcessInstanceId(String processInstanceId);
}
