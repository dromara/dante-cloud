package cn.herodotus.eurynome.integration.content.service.aliyun.scan;

import cn.herodotus.eurynome.integration.content.domain.aliyun.base.CoreResponse;
import cn.herodotus.eurynome.integration.content.domain.aliyun.base.Response;
import cn.herodotus.eurynome.integration.content.domain.aliyun.video.*;
import cn.herodotus.eurynome.integration.definition.AliyunConstants;
import com.aliyuncs.green.model.v20180509.VideoAsyncScanRequest;
import com.aliyuncs.green.model.v20180509.VideoAsyncScanResultsRequest;
import com.aliyuncs.green.model.v20180509.VideoSyncScanRequest;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class VideoScanService extends AbstractScanService {

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

    public VideoAsyncRequest buildDefaultAsyncRequest(List<String> urls, String seed, String callback) {
        List<VideoAsyncTask> tasks = urls.stream().map(url -> {
            VideoAsyncTask videoAsyncTask = new VideoAsyncTask();
            videoAsyncTask.setUrl(url);
            return videoAsyncTask;
        }).collect(Collectors.toList());

        List<String> scenes = ImmutableList.of(AliyunConstants.SCENE_PORN, AliyunConstants.SCENE_TERRORISM);

        return buildAsyncRequest(tasks, scenes, AliyunConstants.BIZ_TYPE, seed, callback);
    }

    public VideoAsyncRequest buildDefaultAsyncRequest(List<String> urls) {
        return this.buildDefaultAsyncRequest(urls, null, null);
    }

    public List<String> asyncAnalyse(List<VideoAsyncResponse> responses) {
        return responses.stream().map(CoreResponse::getTaskId).collect(Collectors.toList());
    }

    /**
     * 执行Video异步检测，如果检测执行成功返回具体的taskId列表
     * <p>
     * 该方法使用的是异步返回模式，需要自己轮询结果。{@link VideoScanService#queryResult(List)}
     *
     * @param videoUrls 待检测视频url地址，支持多个
     * @return taskId列表。
     */
    public List<String> executeScan(List<String> videoUrls) {
        return this.executeScan(videoUrls, null, null);
    }

    /**
     * 执行Video异步检测，如果检测执行成功返回具体的taskId列表。
     * <p>
     * 该方法使用的callback模式，需要提供callback url给阿里
     *
     * @param videoUrls 待检测视频url地址，支持多个
     * @param seed      callback模式，加密验证所需的seed。当前是通过properties写死，最好是每次请求独立生成存储在Redis中。
     * @param callback  提供给阿里的callback地址。
     * @return taskId列表。使用callback模式，逻辑上不需要返回值，这个值是备用值。可以根据实际情况调整。
     */
    public List<String> executeScan(List<String> videoUrls, String seed, String callback) {
        VideoAsyncRequest videoAsyncRequest = this.buildDefaultAsyncRequest(videoUrls, seed, callback);
        Response<List<VideoAsyncResponse>> response = this.asyncScan(videoAsyncRequest);
        if (response.getCode().equals(HttpStatus.SC_OK)) {
            return this.asyncAnalyse(response.getData());
        } else {
            log.error("[Eurynome] |- Aliyun Video Async Scan catch error! result: {}", response);
            return new ArrayList<>();
        }
    }
}
