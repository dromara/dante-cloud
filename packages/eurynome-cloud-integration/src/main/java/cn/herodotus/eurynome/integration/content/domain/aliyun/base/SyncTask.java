package cn.herodotus.eurynome.integration.content.domain.aliyun.base;

import com.google.common.base.MoreObjects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>Description: 阿里云内容检测通用同步检测请求任务实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 13:50
 */
@ApiModel(value = "阿里云内容检测通用同步检测请求任务实体")
public class SyncTask extends AbstractClientTask {

    @ApiModelProperty(name = "待检测图像的URL", required = true)
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
