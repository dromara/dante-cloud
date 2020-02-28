package cn.herodotus.eurynome.bpmn.logic.dto;

import cn.herodotus.eurynome.bpmn.logic.constants.CommentType;

/**
 * <p> Description : 驳回任务参数 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/2/19 11:24
 */
public class RejectTask extends BaseProcessDTO {

    public RejectTask() {
        this.setType(CommentType.REJECT.name());
    }

    /**
     * 需要驳回的节点id 必填
     */
    private String targetActivityId;

    public String getTargetActivityId() {
        return targetActivityId;
    }

    public void setTargetActivityId(String targetActivityId) {
        this.targetActivityId = targetActivityId;
    }

    @Override
    public String toString() {
        return "RejectTask{" +
                "targetActivityId='" + targetActivityId + '\'' +
                '}';
    }
}
