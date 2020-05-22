package cn.herodotus.eurynome.bpmn.logic.dto;

import cn.herodotus.eurynome.bpmn.logic.constants.CommentType;

/**
 * <p> Description : 转办任务参数 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/19 12:02
 */
public class TurnTodoTask extends BaseProcessDTO {

    public TurnTodoTask() {
        this.setType(CommentType.TURN_TODO.name());
    }

    private String turnToUserId;

    public String getTurnToUserId() {
        return turnToUserId;
    }

    public void setTurnToUserId(String turnToUserId) {
        this.turnToUserId = turnToUserId;
    }

    @Override
    public String toString() {
        return "TurnTodoTask{" +
                "turnToUserId='" + turnToUserId + '\'' +
                '}';
    }
}
