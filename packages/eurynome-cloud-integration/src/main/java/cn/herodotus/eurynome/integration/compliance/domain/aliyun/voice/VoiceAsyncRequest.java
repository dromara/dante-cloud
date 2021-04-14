package cn.herodotus.eurynome.integration.compliance.domain.aliyun.voice;

import cn.herodotus.eurynome.integration.compliance.domain.aliyun.base.AbstractAsyncRequest;
import com.google.common.base.MoreObjects;

/**
 * <p>Description: 阿里语音审核异步请求对象 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 12:58
 */
public class VoiceAsyncRequest extends AbstractAsyncRequest<VoiceAsyncTask> {

    /**
     * 是否直播。取值：
     * false（默认）：表示点播视频检测。
     * true：表示直播流检测。
     */
    private Boolean live;
    /**
     * 是否近线检测模式。
     * false（默认）：表示实时检测模式，对于超过了并发路数限制的检测请求会直接拒绝。
     * true：表示近线检测模式，提交的任务不保证实时处理，但是可以排队处理，在24小时内开始检测。
     * 说明 该参数仅适用于视频文件检测，视频流检测无需传入该参数。
     */
    private Boolean offline;

    public Boolean getLive() {
        return live;
    }

    public void setLive(Boolean live) {
        this.live = live;
    }

    public Boolean getOffline() {
        return offline;
    }

    public void setOffline(Boolean offline) {
        this.offline = offline;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("live", live)
                .add("offline", offline)
                .toString();
    }
}
