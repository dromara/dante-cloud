package cn.herodotus.eurynome.integration.content.domain.aliyun.webpage;

import cn.herodotus.eurynome.integration.content.domain.aliyun.base.AbstractResult;

import java.util.List;

/**
 * <p>Description: TextResultInfo </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 14:41
 */
public class TextResultInfo extends AbstractResult {
    /**
     * 命中风险的详细信息，一条文本可能命中多条风险详情。具体结构描述，请参见detail。
     */
    private List<Detail> details;

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }
}
