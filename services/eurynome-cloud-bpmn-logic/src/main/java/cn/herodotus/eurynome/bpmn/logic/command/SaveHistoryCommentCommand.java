package cn.herodotus.eurynome.bpmn.logic.command;

import cn.herodotus.eurynome.bpmn.logic.constants.CommentType;
import org.apache.commons.lang3.StringUtils;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.impl.persistence.entity.CommentEntity;
import org.flowable.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.engine.task.Comment;
import org.flowable.engine.task.Event;

/**
 * <p> Description : 备注保存命令 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/16 17:59
 */
public class SaveHistoryCommentCommand implements Command<Comment> {

    protected String taskId;
    protected String userId;
    protected String processInstanceId;
    protected String type;
    protected String message;

    public SaveHistoryCommentCommand(String processInstanceId, String taskId, String userId, String type, String message) {
        this.taskId = taskId;
        this.userId = userId;
        this.processInstanceId = processInstanceId;
        this.type = type;
        this.message = message;
    }

    @Override
    public Comment execute(CommandContext commandContext) {

        if (processInstanceId != null) {
            HistoricProcessInstanceEntity historicProcessInstanceEntity = CommandContextUtil.getHistoricProcessInstanceEntityManager(commandContext).findById(processInstanceId);
            if (historicProcessInstanceEntity == null) {
                throw new FlowableObjectNotFoundException("HistoricProcessInstance " + processInstanceId + " doesn't exist", HistoricProcessInstanceEntity.class);
            }
        }

        CommentEntity comment = CommandContextUtil.getCommentEntityManager(commandContext).create();
        comment.setUserId(userId);
        comment.setType((type == null) ? CommentType.APPROVAL.name() : type);
        comment.setTime(CommandContextUtil.getProcessEngineConfiguration(commandContext).getClock().getCurrentTime());
        comment.setTaskId(taskId);
        comment.setProcessInstanceId(processInstanceId);
        comment.setAction(Event.ACTION_ADD_COMMENT);

        String eventMessage = StringUtils.isNotBlank(message) ? message.replaceAll("\\s+", " ") : "";
        if (eventMessage.length() > 3900) {
            eventMessage = eventMessage.substring(0, 3900) + "...";
        }
        comment.setMessage(eventMessage);
        comment.setFullMessage(message);
        CommandContextUtil.getCommentEntityManager(commandContext).insert(comment);
        return comment;
    }
}
