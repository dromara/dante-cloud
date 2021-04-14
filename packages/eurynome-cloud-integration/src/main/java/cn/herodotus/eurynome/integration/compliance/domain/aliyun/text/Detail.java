package cn.herodotus.eurynome.integration.compliance.domain.aliyun.text;

import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Description: 阿里文本审核返回值：detail对应实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/13 16:28
 */
public class Detail implements Serializable {

    /**
     * 文本命中风险的分类。取值：
     * spam：含垃圾信息
     * ad：广告
     * politics：涉政
     * terrorism：暴恐
     * abuse：辱骂
     * porn：色情
     * flood：灌水
     * contraband：违禁
     * meaningless：无意义
     * customized：自定义（例如命中自定义关键词）
     */
    private String label;
    /**
     * 命中该风险的上下文信息。具体结构描述，请参见context。
     */
    private List<Context> contexts;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Context> getContexts() {
        return contexts;
    }

    public void setContexts(List<Context> contexts) {
        this.contexts = contexts;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("label", label)
                .toString();
    }
}
