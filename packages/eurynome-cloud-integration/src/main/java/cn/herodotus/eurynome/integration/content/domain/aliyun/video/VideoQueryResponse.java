package cn.herodotus.eurynome.integration.content.domain.aliyun.video;

import com.google.common.base.MoreObjects;

/**
 * <p>Description: 阿里视频异步检测结果查询返回数据实体 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/19 16:20
 */
public class VideoQueryResponse extends VideoSyncResponse{

    private AudioScanResult audioScanResults;

    public AudioScanResult getAudioScanResults() {
        return audioScanResults;
    }

    public void setAudioScanResults(AudioScanResult audioScanResults) {
        this.audioScanResults = audioScanResults;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("audioScanResults", audioScanResults)
                .toString();
    }
}
