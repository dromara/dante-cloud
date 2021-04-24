package cn.herodotus.eurynome.integration.content.domain.aliyun.video;

import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Description: 阿里视频异步检测结果查询返回数据实体对应AudioScanResult属性 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/19 16:21
 */
public class AudioScanResult implements Serializable {

    /**
     * 视频语音检测场景，唯一取值：antispam，表示语音反垃圾
     */
    private String audioScene;
    /**
     * 视频语音的检测结果分类。不同检测场景的结果分类不同，具体如下：
     * normal：正常
     * spam：包含垃圾信息
     * ad：广告
     * politics：涉政
     * terrorism：暴恐
     * abuse：辱骂
     * porn：色情
     * flood：灌水
     * contraband：违禁
     * customized：自定义（例如命中自定义关键词）
     */
    private String label;
    /**
     * 建议您执行的后续操作。取值：
     * pass：结果正常，无需进行其余操作。
     * review：结果不确定，需要进行人工审核。
     * block：结果违规，建议直接删除或者限制公开。
     */
    private String suggestion;
    /**
     * 置信度分数，取值范围：0（表示置信度最低）~100（表示置信度最高）。
     * 如果suggestion为pass，则置信度越高，表示内容正常的可能性越高；如果suggestion为review或block，则置信度越高，表示内容违规的可能性越高。
     */
    private Float rate;
    /**
     * 语音对应的文本详情（每一句文本对应一个元素），包含一个或者多个元素
     */
    private List<Detail> details;

    public String getAudioScene() {
        return audioScene;
    }

    public void setAudioScene(String audioScene) {
        this.audioScene = audioScene;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("audioScene", audioScene)
                .add("label", label)
                .add("suggestion", suggestion)
                .add("rate", rate)
                .toString();
    }
}
