package cn.herodotus.eurynome.bpmn.logic.dto;

import cn.herodotus.eurynome.bpmn.logic.constants.CommentType;

/**
 * <p> Description : 完成任务参数 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/19 13:27
 */
public class CompleteTask extends BaseProcessDTO {

    public CompleteTask() {
        this.setType(CommentType.APPROVAL.name());
    }
}
