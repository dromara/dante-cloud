package cn.herodotus.eurynome.integration.compliance.domain.aliyun.webpage;

import cn.herodotus.eurynome.integration.compliance.domain.aliyun.base.AbstractResultResponse;
import com.google.common.base.MoreObjects;

/**
 * <p>Description: ImageResult </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 14:46
 */
public class ImageResult extends AbstractResultResponse<ImageResultInfo> {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("url", url)
                .toString();
    }
}
