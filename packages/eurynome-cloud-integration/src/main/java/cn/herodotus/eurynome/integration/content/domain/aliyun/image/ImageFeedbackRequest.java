package cn.herodotus.eurynome.integration.content.domain.aliyun.image;

import cn.herodotus.eurynome.integration.content.domain.aliyun.base.AbstractFeedbackRequest;
import com.google.common.base.MoreObjects;

import java.util.List;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 11:51
 */
public class ImageFeedbackRequest extends AbstractFeedbackRequest {

    /**
     * 用户期望URL的检测结果，传递该参数时必须传递scenes参数。取值范围：
     * pass：正常
     * 说明 反馈pass的图片会流入云盾控制台对应的系统图库。
     * block：违规
     */
    private String suggestion;
    /**
     * 如果需要将图片样本流入控制台的系统回流库中，则必须传递该参数。
     */
    private String url;
    /**
     * 指定反馈的场景。可选值包括：
     * porn：智能鉴黄
     * terrorism：暴恐涉政识别
     * ad：广告识别
     * 说明 支持多场景（scenes）。例如，使用scenes=[“porn”, “terrorism”]同时指定智能鉴黄和暴恐涉政识别。
     */
    private List<String> scenes;

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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("suggestion", suggestion)
                .add("url", url)
                .toString();
    }
}
