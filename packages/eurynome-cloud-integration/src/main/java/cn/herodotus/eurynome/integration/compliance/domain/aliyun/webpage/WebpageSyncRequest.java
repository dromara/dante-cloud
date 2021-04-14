package cn.herodotus.eurynome.integration.compliance.domain.aliyun.webpage;

import cn.herodotus.eurynome.integration.compliance.domain.aliyun.base.SyncRequest;
import com.google.common.base.MoreObjects;

import java.util.List;

/**
 * <p>Description: 阿里网页审核同步请求对象 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 12:48
 */
public class WebpageSyncRequest extends SyncRequest<WebpageTask> {

    /**
     * 表示网页文本要检测的场景，唯一取值：antispam。
     * 说明 textScenes和imageScenes字段至少选择一个传入。
     */
    private List<String> textScenes;
    /**
     * 表示网页图片要检测的场景，取值：
     * porn：鉴黄
     * ad：广告
     * terrorism：暴恐涉政
     * live：不良场景
     * 说明 textScenes和imageScenes字段至少选择一个传入。
     */
    private List<String> imageScenes;
    /**
     * 指定是否高亮显示违规内容，取值：
     * true：高亮显示违规内容。
     * false（默认）：不高亮显示违规内容。
     */
    private boolean returnHighlightHtml;

    public List<String> getTextScenes() {
        return textScenes;
    }

    public void setTextScenes(List<String> textScenes) {
        this.textScenes = textScenes;
    }

    public List<String> getImageScenes() {
        return imageScenes;
    }

    public void setImageScenes(List<String> imageScenes) {
        this.imageScenes = imageScenes;
    }

    public boolean isReturnHighlightHtml() {
        return returnHighlightHtml;
    }

    public void setReturnHighlightHtml(boolean returnHighlightHtml) {
        this.returnHighlightHtml = returnHighlightHtml;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("returnHighlightHtml", returnHighlightHtml)
                .toString();
    }
}
