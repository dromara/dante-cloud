package cn.herodotus.eurynome.integration.content.service.aliyun.scan;

import cn.herodotus.eurynome.integration.content.domain.aliyun.base.CoreResponse;
import cn.herodotus.eurynome.integration.content.domain.aliyun.base.Response;
import cn.herodotus.eurynome.integration.content.domain.aliyun.image.*;
import cn.herodotus.eurynome.integration.definition.AliyunConstants;
import com.aliyuncs.green.model.v20180509.ImageAsyncScanRequest;
import com.aliyuncs.green.model.v20180509.ImageAsyncScanResultsRequest;
import com.aliyuncs.green.model.v20180509.ImageSyncScanRequest;
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
 * <p>Description: 阿里图片审核服务类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 16:04
 */
@Slf4j
@Service
public class ImageScanService extends AbstractScanService {

    @Autowired
    private ImageSyncScanRequest imageSyncScanRequest;
    @Autowired
    private ImageAsyncScanRequest imageAsyncScanRequest;
    @Autowired
    private ImageAsyncScanResultsRequest imageAsyncScanResultsRequest;

    public Response<List<ImageSyncResponse>> syncScan(ImageSyncRequest imageSyncRequest) {
        String jsonString = this.scan(imageSyncRequest, imageSyncScanRequest);
        Response<List<ImageSyncResponse>> entity = this.parseListResult(jsonString, ImageSyncResponse.class);
        log.debug("[Eurynome] |- Aliyun Image Sync Scan result is: {}", entity.toString());
        return entity;
    }

    public Response<List<ImageAsyncResponse>> asyncScan(ImageAsyncRequest imageAsyncRequest) {
        String jsonString = this.scan(imageAsyncRequest, imageAsyncScanRequest);
        Response<List<ImageAsyncResponse>> entity = this.parseListResult(jsonString, ImageAsyncResponse.class);
        log.debug("[Eurynome] |- Aliyun Image Async Scan result is: {}", entity.toString());
        return entity;
    }

    public Response<List<ImageQueryResponse>> queryResult(List<String> taskIds) {
        String jsonString = this.query(taskIds, imageAsyncScanResultsRequest);
        Response<List<ImageQueryResponse>> entity = this.parseListResult(jsonString, ImageQueryResponse.class);
        log.debug("[Eurynome] |- Aliyun Image Async Scan Query Result is: {}", entity.toString());
        return entity;
    }

    public ImageAsyncRequest buildAsyncRequest(List<ImageTask> tasks, List<String> scenes, String bizType, String seed, String callback) {
        ImageAsyncRequest imageAsyncRequest = new ImageAsyncRequest();
        imageAsyncRequest.setBizType(bizType);
        imageAsyncRequest.setScenes(scenes);
        imageAsyncRequest.setTasks(tasks);
        if (StringUtils.isNotBlank(seed) && StringUtils.isNotBlank(callback)) {
            imageAsyncRequest.setSeed(seed);
            imageAsyncRequest.setCallback(callback);
        }
        return imageAsyncRequest;
    }

    public ImageAsyncRequest buildDefaultAsyncRequest(List<String> urls, String seed, String callback) {
        List<ImageTask> tasks = urls.stream().map(url -> {
            ImageTask imageTask = new ImageTask();
            imageTask.setUrl(url);
            return imageTask;
        }).collect(Collectors.toList());

        List<String> scenes = ImmutableList.of(AliyunConstants.SCENE_PORN, AliyunConstants.SCENE_TERRORISM, AliyunConstants.SCENE_AD);

        return buildAsyncRequest(tasks, scenes, AliyunConstants.BIZ_TYPE, seed, callback);
    }

    public ImageAsyncRequest buildDefaultAsyncRequest(List<String> urls) {
        return this.buildDefaultAsyncRequest(urls, null, null);
    }

    public List<String> asyncAnalyse(List<ImageAsyncResponse> responses) {
        return responses.stream().map(CoreResponse::getTaskId).collect(Collectors.toList());
    }

    /**
     * 执行Image异步检测，如果检测执行成功返回具体的taskId列表。
     * <p>
     * 该方法使用的是异步返回模式，需要自己轮询结果。{@link ImageScanService#queryResult(List)}
     *
     * @param imageUrls 待检测图片url地址，支持多个
     * @return taskId列表。
     */
    public List<String> executeScan(List<String> imageUrls) {
        return this.executeScan(imageUrls, null, null);
    }

    /**
     * 执行Image异步检测，如果检测执行成功返回具体的taskId列表。
     * <p>
     * 该方法使用的callback模式，需要提供callback url给阿里
     *
     * @param imageUrls 待检测图片url地址，支持多个
     * @param seed      callback模式，加密验证所需的seed。当前是通过properties写死，最好是每次请求独立生成存储在Redis中。
     * @param callback  提供给阿里的callback地址。
     * @return taskId列表。使用callback模式，逻辑上不需要返回值，这个值是备用值。可以根据实际情况调整。
     */
    public List<String> executeScan(List<String> imageUrls, String seed, String callback) {
        ImageAsyncRequest imageAsyncRequest = this.buildDefaultAsyncRequest(imageUrls, seed, callback);
        Response<List<ImageAsyncResponse>> response = this.asyncScan(imageAsyncRequest);
        if (response.getCode().equals(HttpStatus.SC_OK)) {
            return this.asyncAnalyse(response.getData());
        } else {
            log.error("[Eurynome] |- Aliyun Image Async Scan catch error! result: {}", response);
            return new ArrayList<>();
        }
    }
}
