package cn.herodotus.eurynome.integration.content.domain.aliyun.video;

import cn.herodotus.eurynome.integration.content.domain.aliyun.base.AbstractAsyncRequest;
import com.google.common.base.MoreObjects;

import java.util.List;

/**
 * <p>Description: 阿里视频异步检测请求实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 12:34
 */
public class VideoAsyncRequest extends AbstractAsyncRequest<VideoAsyncTask> {
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
    /**
     * 指定视频语音检测场景，唯一取值：antispam，表示语音反垃圾。
     * 不传入该参数时仅检测视频图像内容；如果传入该参数，则在检测视频中图像的同时，对视频中语音进行检测。
     * <p>
     * 说明 如果需要检测视频语音，则不支持通过上传视频截帧序列的方式（即在task中传入frames）进行检测，您必须传入视频或视频流的URL地址（即在task中传入url）进行检测。
     */
    private List<String> audioScenes;

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

    public List<String> getAudioScenes() {
        return audioScenes;
    }

    public void setAudioScenes(List<String> audioScenes) {
        this.audioScenes = audioScenes;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("live", live)
                .add("offline", offline)
                .toString();
    }
}
