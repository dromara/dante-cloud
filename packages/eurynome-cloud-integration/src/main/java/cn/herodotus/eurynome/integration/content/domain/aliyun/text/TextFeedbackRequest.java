package cn.herodotus.eurynome.integration.content.domain.aliyun.text;

import cn.herodotus.eurynome.integration.content.domain.aliyun.base.AbstractFeedbackRequest;
import com.google.common.base.MoreObjects;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>Description:  阿里文本检测内容反馈请求实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 11:07
 */
public class TextFeedbackRequest extends AbstractFeedbackRequest {

    /**
     * 内容安全服务器返回的，唯一标识该检测任务的ID。
     */
    @ApiModelProperty(name = "唯一标识该检测任务的ID", required = true, notes = "内容安全服务器返回的，唯一标识该检测任务的ID")
    private String taskId;
    /**
     * 对应的请求中的dataId。
     */
    private String dataId;
    /**
     * 被检测的内容，最长10,000个字符。
     */
    private String content;

    /**
     * 反馈的分类，与具体的scene对应。关于取值范围的说明，请参见文本反垃圾scene和label说明。
     */
    private String label;
    /**
     * 备注，比如文本中的关键文字。
     */
    private String note;


    @Override
    public String getTaskId() {
        return taskId;
    }

    @Override
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String getNote() {
        return note;
    }

    @Override
    public void setNote(String note) {
        this.note = note;
    }
}
