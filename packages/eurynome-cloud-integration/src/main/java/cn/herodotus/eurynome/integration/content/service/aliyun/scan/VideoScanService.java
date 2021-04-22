package cn.herodotus.eurynome.integration.content.service.aliyun.scan;

import cn.herodotus.eurynome.integration.content.domain.aliyun.AliyunConstants;
import cn.herodotus.eurynome.integration.content.domain.aliyun.base.CoreResponse;
import cn.herodotus.eurynome.integration.content.domain.aliyun.base.Response;
import cn.herodotus.eurynome.integration.content.domain.aliyun.video.*;
import com.aliyuncs.green.model.v20180509.VideoAsyncScanRequest;
import com.aliyuncs.green.model.v20180509.VideoAsyncScanResultsRequest;
import com.aliyuncs.green.model.v20180509.VideoSyncScanRequest;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Description: 阿里视频审核服务类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 15:45
 */
@Slf4j
@Service
public class VideoScanService extends AbstractScanService{

    @Autowired
    private VideoSyncScanRequest videoSyncScanRequest;
    @Autowired
    private VideoAsyncScanRequest videoAsyncScanRequest;
    @Autowired
    private VideoAsyncScanResultsRequest videoAsyncScanResultsRequest;

    public Response<List<VideoSyncResponse>> syncScan(VideoSyncRequest videoSyncRequest) {
        String jsonString = this.scan(videoSyncRequest, videoSyncScanRequest);
        Response<List<VideoSyncResponse>> entity = this.parseListResult(jsonString, VideoSyncResponse.class);
        log.debug("[Eurynome] |- Aliyun Video Sync Scan result is: {}", entity.toString());
        return entity;
    }

    public Response<List<VideoAsyncResponse>> asyncScan(VideoAsyncRequest videoAsyncRequest) {
        String jsonString = this.scan(videoAsyncRequest, videoAsyncScanRequest);
        Response<List<VideoAsyncResponse>> entity = this.parseListResult(jsonString, VideoAsyncResponse.class);
        log.debug("[Eurynome] |- Aliyun Video Async Scan result is: {}", entity.toString());
        return entity;
    }

    public Response<List<VideoQueryResponse>> queryResult(List<String> taskIds) {
        String jsonString = this.query(taskIds, videoAsyncScanResultsRequest);
        Response<List<VideoQueryResponse>> entity = this.parseListResult(jsonString, VideoQueryResponse.class);
        log.debug("[Eurynome] |- Aliyun Video Async Scan Query Result is: {}", entity.toString());
        return entity;
    }

    public VideoAsyncRequest buildAsyncRequest(List<VideoAsyncTask> tasks, List<String> scenes, String bizType, String seed, String callback) {
        VideoAsyncRequest videoAsyncRequest = new VideoAsyncRequest();
        videoAsyncRequest.setBizType(bizType);
        videoAsyncRequest.setScenes(scenes);
        videoAsyncRequest.setTasks(tasks);
        if (StringUtils.isNotBlank(seed) && StringUtils.isNotBlank(callback)) {
            videoAsyncRequest.setSeed(seed);
            videoAsyncRequest.setCallback(callback);
        }
        return videoAsyncRequest;
    }

    public VideoAsyncRequest buildAsyncRequest(List<VideoAsyncTask> tasks, List<String> scenes, String bizType)  {
        return this.buildAsyncRequest(tasks, scenes, bizType);
    }

    public VideoAsyncRequest buildDefaultAsyncRequest(List<String> urls, String seed, String callback) {
        List<VideoAsyncTask> tasks = urls.stream().map(url -> {
            VideoAsyncTask videoAsyncTask = new VideoAsyncTask();
            videoAsyncTask.setUrl(url);
            return videoAsyncTask;
        }).collect(Collectors.toList());

        List<String> scenes = ImmutableList.of(AliyunConstants.SCENE_PORN, AliyunConstants.SCENE_TERRORISM);

        return buildAsyncRequest(tasks, scenes, null, seed, callback);
    }

    public VideoAsyncRequest buildDefaultAsyncRequest(List<String> urls) {
        return this.buildDefaultAsyncRequest(urls, null, null);
    }

    public List<String> asyncAnalyse(List<VideoAsyncResponse> responses) {
        return responses.stream().map(CoreResponse::getTaskId).collect(Collectors.toList());
    }
}
