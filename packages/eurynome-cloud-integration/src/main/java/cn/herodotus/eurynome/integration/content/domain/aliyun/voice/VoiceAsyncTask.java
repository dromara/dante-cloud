package cn.herodotus.eurynome.integration.content.domain.aliyun.voice;

import cn.herodotus.eurynome.integration.content.domain.aliyun.base.SyncTask;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 13:36
 */
public class VoiceAsyncTask extends SyncTask {

    /**
     * 语音直播流的ID。
     * 该参数用于语音直播任务去重，防止重复检测，如果传递该参数，会根据uid+bizType+liveId判断是否存在检测中的直播任务。如果存在，就直接返回已存在的直播检测taskId，不发起新的任务。
     */
    private String liveId;

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }
}
