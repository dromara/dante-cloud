package cn.herodotus.eurynome.integration.content.domain.aliyun.image;

import cn.herodotus.eurynome.integration.content.domain.aliyun.base.AbstractFeedbackBody;
import com.google.common.base.MoreObjects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * <p>Description: 阿里图片检测内容反馈请求参数实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 11:51
 */
@ApiModel(value = "阿里图片检测内容反馈请求参数实体")
public class ImageFeedbackBody extends AbstractFeedbackBody {

    /**
     * 用户期望URL的检测结果，传递该参数时必须传递scenes参数。取值范围：
     * pass：正常
     * 说明 反馈pass的图片会流入云盾控制台对应的系统图库。
     * block：违规
     */
    @ApiModelProperty(name = "用户期望URL的检测结果，传递该参数时必须传递scenes参数", notes = "pass：正常, block：违规")
    private String suggestion;

    /**
     * 如果需要将图片样本流入控制台的系统回流库中，则必须传递该参数。
     */
    @ApiModelProperty(name = "如果需要将图片样本流入控制台的系统回流库中，则必须传递该参数")
    private String url;

    @ApiModelProperty(name = "指定反馈的场景", notes = "porn：智能鉴黄, terrorism：暴恐涉政识别, ad：广告识别")
    private List<String> scenes;

    /**
     * 反馈的分类，与具体的scene对应。关于取值范围的说明，请参见文本反垃圾scene和label说明。
     */
    @ApiModelProperty(name = "传递该参数表示您认为该图片属于的细分类类别")
    private String label;
    /**
     * 备注。
     */
    @ApiModelProperty(name = "备注")
    private String note;

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getScenes() {
        return scenes;
    }

    public void setScenes(List<String> scenes) {
        this.scenes = scenes;
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
                .add("suggestion", suggestion)
                .add("url", url)
                .add("label", label)
                .add("note", note)
                .toString();
    }
}
