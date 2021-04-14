package cn.herodotus.eurynome.integration.compliance.domain.aliyun.video;

import cn.herodotus.eurynome.integration.compliance.domain.aliyun.base.SyncTask;
import com.google.common.base.MoreObjects;

import java.util.List;

/**
 * <p>Description: 阿里视频检查同步任务实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 11:54
 */
public class VideoSyncTask extends SyncTask {

    /**
     * 待检测视频的截帧信息。frames中的每个元素是个结构体，关于每个元素的具体结构描述，请参见frame。
     */
    private List<TaskFrame> frames;

    /**
     * 截帧地址的前缀，与frame.url一起组成截帧的完整地址。视频截帧的完整地址格式为framePrefix + frame.url，请参见frame
     */
    private String framePrefix;

    public List<TaskFrame> getFrames() {
        return frames;
    }

    public void setFrames(List<TaskFrame> frames) {
        this.frames = frames;
    }

    public String getFramePrefix() {
        return framePrefix;
    }

    public void setFramePrefix(String framePrefix) {
        this.framePrefix = framePrefix;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("framePrefix", framePrefix)
                .toString();
    }
}
