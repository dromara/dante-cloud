package cn.herodotus.eurynome.bpmn.logic.service;

import cn.herodotus.eurynome.bpmn.logic.command.SaveHistoryCommentCommand;
import cn.herodotus.eurynome.bpmn.logic.constants.CommentType;
import cn.herodotus.eurynome.bpmn.logic.dto.Comment;
import cn.herodotus.eurynome.bpmn.logic.mapper.CommentRepository;
import org.flowable.engine.ManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/17 10:09
 */
@Service
public class CommentService {

    @Autowired
    private ManagementService managementService;
    @Autowired
    private CommentRepository commentRepository;

    public void addComment(Comment comment) {
        this.addComment(comment.getProcessInstanceId(), comment.getTaskId(), comment.getEmployeeId(), comment.getType(), comment.getMessage());
    }

    public void addComment(String processInstanceId, String taskId, String userId, String type, String message) {
        managementService.executeCommand(new SaveHistoryCommentCommand(processInstanceId, taskId, userId, type, message));
    }

    public List<Comment> getCommentsByProcessInstanceId(String processInstanceId) {
        List<Comment> comments = commentRepository.getCommentsByProcessInstanceId(processInstanceId);
        comments.forEach(comment -> comment.setTypeName(CommentType.getMessageByName(comment.getType())));
        return comments;
    }
}
