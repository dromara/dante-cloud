package cn.herodotus.eurynome.integration.content.domain.aliyun.base;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 11:48
 */
public class AbstractFeedbackRequest implements Serializable {

    /**
     * 内容安全服务器返回的，唯一标识该检测任务的ID。
     */
    private String taskId;
    /**
     * 反馈的分类，与具体的scene对应。关于取值范围的说明，请参见文本反垃圾scene和label说明。
     */
    private String label;
    /**
     * 备注，比如文本中的关键文字。
     */
    private String note;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("taskId", taskId)
                .add("label", label)
                .add("note", note)
                .toString();
    }
}
