package cn.herodotus.eurynome.integration.content.domain.aliyun.callback;

import cn.herodotus.eurynome.integration.content.domain.aliyun.base.AbstractResultResponse;
import cn.herodotus.eurynome.integration.content.domain.aliyun.common.HitLibInfo;
import com.google.common.base.MoreObjects;

/**
 * <p>Description: 阿里内容审核异步审核Callback返回结果实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/20 15:46
 */
public class CallbackResponse extends AbstractResultResponse<CallbackResult> {

    private HitLibInfo extras;

    private String url;

    public HitLibInfo getExtras() {
        return extras;
    }

    public void setExtras(HitLibInfo extras) {
        this.extras = extras;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("extras", extras)
                .add("url", url)
                .toString();
    }
}
