package cn.herodotus.eurynome.integration.content.domain.aliyun.webpage;

import cn.herodotus.eurynome.integration.content.domain.aliyun.base.BaseResponse;
import com.google.common.base.MoreObjects;

import java.util.List;
import java.util.Map;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 14:32
 */
public class WebpageSyncResponse extends BaseResponse {

    /**
     * 建议您执行的后续操作，取值：
     * pass：结果正常，无需进行其余操作。
     * review：结果不确定，需要进行人工审核。
     * block：结果违规，建议直接删除或者限制公开。
     */
    private String suggestion;
    /**
     * 网页违规内容命中的风险类型和次数， 使用key-value键值对格式表示。其中，key表示命中的风险标签，value表示风险次数。
     * 关于风险标签的示例，请参见文本label和图片label。
     */
    private Map<String, Integer> riskFrequency;
    /**
     * 文本扫描结果。
     * 只有传入textScenes参数时，才返回该结果。该参数是一个JSON数组，关于每个元素返回的结构，请参见textResults。
     */
    private List<TextResult> textResults;
    /**
     * 图片扫描结果。
     * 只有传入imageScenes参数时，才返回该结果。该参数是一个JSON数组，关于每个元素返回的结构，请参见imageResults。
     */
    private List<ImageResult> imageResults;
    /**
     * 高亮的html。
     */
    private String highlightHtml;

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public Map<String, Integer> getRiskFrequency() {
        return riskFrequency;
    }

    public void setRiskFrequency(Map<String, Integer> riskFrequency) {
        this.riskFrequency = riskFrequency;
    }

    public List<TextResult> getTextResults() {
        return textResults;
    }

    public void setTextResults(List<TextResult> textResults) {
        this.textResults = textResults;
    }

    public List<ImageResult> getImageResults() {
        return imageResults;
    }

    public void setImageResults(List<ImageResult> imageResults) {
        this.imageResults = imageResults;
    }

    public String getHighlightHtml() {
        return highlightHtml;
    }

    public void setHighlightHtml(String highlightHtml) {
        this.highlightHtml = highlightHtml;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("suggestion", suggestion)
                .add("riskFrequency", riskFrequency)
                .add("textResults", textResults)
                .add("imageResults", imageResults)
                .add("highlightHtml", highlightHtml)
                .toString();
    }
}
