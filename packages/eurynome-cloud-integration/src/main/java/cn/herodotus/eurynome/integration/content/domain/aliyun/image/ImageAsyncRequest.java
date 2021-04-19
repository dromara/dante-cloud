package cn.herodotus.eurynome.integration.content.domain.aliyun.image;

import cn.herodotus.eurynome.integration.content.domain.aliyun.base.AbstractAsyncRequest;
import com.google.common.base.MoreObjects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>Description: 阿里云图片审核异步请求参数实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 16:12
 */
@ApiModel(value = "阿里云图片审核异步请求参数实体")
public class ImageAsyncRequest extends AbstractAsyncRequest<ImageTask> {

    /**
     * 指定检测模式， 取值范围：
     * true：表示近线检测模式。近线检测模式下，您提交的任务不保证能够实时处理，但是可以排队并在24小时内开始检测。
     * false（默认）：表示实时检测模式。对于超过了并发路数限制的检测请求会直接拒绝。
     */
    @ApiModelProperty(name = "指定检测模式")
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
