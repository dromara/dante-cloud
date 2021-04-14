package cn.herodotus.eurynome.integration.compliance.domain.aliyun.base;

import java.util.List;

/**
 * <p>Description: AbstractResultResponse </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 14:15
 */
public abstract  class AbstractResultResponse<T extends AbstractResult> extends BaseResponse{

    private List<T> results;

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}
