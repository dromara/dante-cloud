package cn.herodotus.eurynome.integration.content.domain.aliyun.video;

import com.google.common.base.MoreObjects;

/**
 * <p>Description: 阿里视频检查异步任务实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 12:36
 */
public class VideoAsyncTask extends VideoSyncTask {
    /**
     * 视频直播流的ID。
     * 该参数用于视频直播任务去重，防止重复检测，如果传递该参数，会根据uid+bizType+liveId判断是否存在检测中的直播任务。如果存在，就直接返回已存在的直播检测taskId，不发起新的任务。
     */
    private String liveId;
    /**
     * 视频截帧间隔，单位为秒，取值范围：1~600。默认值为1秒。
     */
    private Integer interval;
    /**
     * 系统对本次检测的视频进行截帧的张数上限，取值范围：5~3600，默认为200张。如需调整到更大，请提交工单联系我们。
     * 说明
     * 该字段仅在视频文件检测中生效（live=false）。如果是视频流检测（live=true），则该参数无效，视频流检测没有截帧数量上限。
     * 当使用OSS地址（以oss://开头）作为视频源地址，并且授权内容安全服务访问阿里云MTS服务后，最大可截取20,000张，该方式不会产生额外费用。关于授权内容安全访问阿里云MTS服务的方法，请参见授权访问MTS服务。
     */
    private Integer maxFrames;

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Integer getMaxFrames() {
        return maxFrames;
    }

    public void setMaxFrames(Integer maxFrames) {
        this.maxFrames = maxFrames;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("liveId", liveId)
                .add("interval", interval)
                .add("maxFrames", maxFrames)
                .toString();
    }
}
