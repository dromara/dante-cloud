package cn.herodotus.eurynome.integration.content.domain.aliyun.base;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

/**
 * <p>Description: 阿里审核返回结果通用对象 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/12 16:50
 */
public abstract class AbstractResult implements Serializable {

    /**
     * 检测场景，和调用请求中的场景对应。
     */
    private String scene;
    /**
     * 建议您执行的后续操作。取值：
     * pass：结果正常，无需进行其余操作。
     * review：结果不确定，需要进行人工审核。
     * block：结果违规，建议直接删除或者限制公开。
     */
    private String suggestion;
    /**
     * 检测结果的分类
     */
    private String label;
    /**
     * 置信度分数，取值范围：0（表示置信度最低）~100（表示置信度最高）。
     * 如果suggestion为pass，则置信度越高，表示内容正常的可能性越高；如果suggestion为review或block，则置信度越高，表示内容违规的可能性越高。
     */
    private Float rate;

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

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
                .add("scene", scene)
                .add("suggestion", suggestion)
                .add("label", label)
                .add("rate", rate)
                .toString();
    }
}
