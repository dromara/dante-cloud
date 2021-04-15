package cn.herodotus.eurynome.integration.content.domain.aliyun.text;

import cn.herodotus.eurynome.integration.content.domain.aliyun.base.AbstractResult;
import com.google.common.base.MoreObjects;

import java.util.List;

/**
 * <p>Description: 阿里文本审核返回值：result对应实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/13 16:26
 */
public class TextResult extends AbstractResult {

    /**
     * 附加信息，扩展字段
     */
    private Extras extras;
    /**
     * 命中风险的详细信息，一条文本可能命中多条风险详情。具体结构描述，请参见detail。
     */
    private List<Detail> details;

    public Extras getExtras() {
        return extras;
    }

    public void setExtras(Extras extras) {
        this.extras = extras;
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
                .add("extras", extras)
                .toString();
    }
}
