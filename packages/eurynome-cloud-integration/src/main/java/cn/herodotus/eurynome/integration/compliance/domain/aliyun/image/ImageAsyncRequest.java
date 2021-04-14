package cn.herodotus.eurynome.integration.compliance.domain.aliyun.image;

import cn.herodotus.eurynome.integration.compliance.domain.aliyun.base.AbstractAsyncRequest;
import cn.herodotus.eurynome.integration.compliance.domain.aliyun.video.VideoAsyncTask;
import com.google.common.base.MoreObjects;

/**
 * <p>Description: 阿里图片异步检测请求实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 16:12
 */
public class ImageAsyncRequest extends AbstractAsyncRequest<VideoAsyncTask> {

    /**
     * 指定检测模式， 取值范围：
     * true：表示近线检测模式。近线检测模式下，您提交的任务不保证能够实时处理，但是可以排队并在24小时内开始检测。
     * false（默认）：表示实时检测模式。对于超过了并发路数限制的检测请求会直接拒绝。
     */
    private Boolean offline;

    public Boolean getOffline() {
        return offline;
    }

    public void setOffline(Boolean offline) {
        this.offline = offline;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("offline", offline)
                .toString();
    }
}
