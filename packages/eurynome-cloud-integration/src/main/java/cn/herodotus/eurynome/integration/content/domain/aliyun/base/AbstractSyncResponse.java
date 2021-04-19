package cn.herodotus.eurynome.integration.content.domain.aliyun.base;

import com.google.common.base.MoreObjects;

import java.util.List;

/**
 * <p>Description: 阿里同步审核返回结果通用对象 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/12 16:49
 */
public abstract class AbstractSyncResponse<T extends AbstractResult> extends BaseResponse {

    private List<T> results;

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .toString();
    }
}
