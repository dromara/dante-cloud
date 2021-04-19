package cn.herodotus.eurynome.integration.content.domain.aliyun.video;

import cn.herodotus.eurynome.integration.content.domain.aliyun.base.AbstractFeedbackBody;
import com.google.common.base.MoreObjects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * <p>Description: 阿里视频检测内容反馈请求参数实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/19 14:28
 */
@ApiModel(value = "阿里视频检测内容反馈请求参数实体")
public class VideoFeedbackBody extends AbstractFeedbackBody {

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

    @ApiModelProperty(name = "用户认为属于该分类的截帧信息", required = true, notes = "frames中的每个元素是个结构体，关于结构的具体描述")
    private List<FeedbackFrame> frames;

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

    public List<FeedbackFrame> getFrames() {
        return frames;
    }

    public void setFrames(List<FeedbackFrame> frames) {
        this.frames = frames;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("suggestion", suggestion)
                .add("url", url)
                .toString();
    }
}
