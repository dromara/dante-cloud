package cn.herodotus.eurynome.integration.compliance.domain.aliyun.image;

import cn.herodotus.eurynome.integration.compliance.domain.aliyun.base.AbstractSyncResponse;
import cn.herodotus.eurynome.integration.compliance.domain.aliyun.common.HitLibInfo;
import com.google.common.base.MoreObjects;

/**
 * <p>Description: 阿里图片审核返回值对应实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 11:22
 */
public class ImageSyncResponse extends AbstractSyncResponse<ImageResult> {
    /**
     * 检测对象的URL
     */
    private String url;
    /**
     * 如果用户开启了证据转存到OSS存储空间的功能，并且检测任务符合配置的规则，则会把图片转存到用户的OSS存储空间，并返回对应的HTTP URL。
     */
    private String storeUrl;
    private HitLibInfo extras;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStoreUrl() {
        return storeUrl;
    }

    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
    }

    public HitLibInfo getExtras() {
        return extras;
    }

    public void setExtras(HitLibInfo extras) {
        this.extras = extras;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("url", url)
                .add("storeUrl", storeUrl)
                .add("extras", extras)
                .toString();
    }
}
