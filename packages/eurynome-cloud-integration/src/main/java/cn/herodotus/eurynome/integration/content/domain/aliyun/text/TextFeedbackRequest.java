package cn.herodotus.eurynome.integration.content.domain.aliyun.text;

import cn.herodotus.eurynome.integration.content.domain.aliyun.base.BodyRequest;
import com.google.common.base.MoreObjects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>Description:  阿里文本检测内容反馈请求实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 11:07
 */
@ApiModel(value = "阿里文本检测内容反馈请求参数实体")
public class TextFeedbackRequest extends BodyRequest<TextFeedbackBody> {

    /**
     * 被检测的内容，最长10,000个字符。
     */
    @ApiModelProperty(name = "被检测的内容，最长10,000个字符")
    private String content;

    /**
     * 反馈的分类，与具体的scene对应。关于取值范围的说明，请参见文本反垃圾scene和label说明。
     */
    @ApiModelProperty(name = "反馈的分类，与具体的scene对应")
    private String label;
    /**
     * 备注，比如文本中的关键文字。
     */
    @ApiModelProperty(name = "备注，比如文本中的关键文字")
    private String note;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
                .add("content", content)
                .add("label", label)
                .add("note", note)
                .toString();
    }
}
