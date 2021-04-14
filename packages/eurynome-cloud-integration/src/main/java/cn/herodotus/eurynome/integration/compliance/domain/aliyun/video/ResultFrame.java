package cn.herodotus.eurynome.integration.compliance.domain.aliyun.video;

import com.google.common.base.MoreObjects;

/**
 * <p>Description: 阿里视频检测结果相关frame实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 12:01
 */
public class ResultFrame extends TaskFrame{
    /**
     * 视频截帧的检测结果分类。
     */
    private String label;
    /**
     * 置信度分数，取值范围：0~100，置信度越高表示检测结果的可信度越高。建议您不要在业务中使用该分数。
     */
    private Float rate;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("label", label)
                .add("rate", rate)
                .toString();
    }
}
