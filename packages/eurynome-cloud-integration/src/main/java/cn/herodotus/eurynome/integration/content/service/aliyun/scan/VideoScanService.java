package cn.herodotus.eurynome.integration.content.service.aliyun.scan;

import cn.herodotus.eurynome.integration.content.domain.aliyun.base.Response;
import cn.herodotus.eurynome.integration.content.domain.aliyun.video.VideoAsyncRequest;
import cn.herodotus.eurynome.integration.content.domain.aliyun.video.VideoAsyncResponse;
import cn.herodotus.eurynome.integration.content.domain.aliyun.video.VideoSyncRequest;
import cn.herodotus.eurynome.integration.content.domain.aliyun.video.VideoSyncResponse;
import com.aliyuncs.green.model.v20180509.VideoAsyncScanRequest;
import com.aliyuncs.green.model.v20180509.VideoSyncScanRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Response<List<VideoSyncResponse>> syncScan(VideoSyncRequest videoSyncRequest) {
        String jsonString = this.scan(videoSyncRequest, videoSyncScanRequest);
        Response<List<VideoSyncResponse>> entity = this.parseListResult(jsonString, VideoSyncResponse.class);
        log.debug("[Eurynome] |- Aliyun Video Sync Scan result is: {}", entity.toString());
        return entity;
    }

    public Response<VideoAsyncResponse> asyncScan(VideoAsyncRequest videoAsyncRequest) {
        String jsonString = this.scan(videoAsyncRequest, videoAsyncScanRequest);
        Response<VideoAsyncResponse> entity = this.parseResult(jsonString, VideoAsyncResponse.class);
        log.debug("[Eurynome] |- Aliyun Video Async Scan result is: {}", entity.toString());
        return entity;
    }
}
