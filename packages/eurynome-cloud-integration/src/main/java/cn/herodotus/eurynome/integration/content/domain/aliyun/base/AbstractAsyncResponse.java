package cn.herodotus.eurynome.integration.content.domain.aliyun.base;

import com.google.common.base.MoreObjects;

/**
 * <p>Description: 阿里异步步审核返回结果通用对象 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 11:45
 */
public class AbstractAsyncResponse extends BaseResponse {

    /**
     * 检测对象的URL。
     */
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
