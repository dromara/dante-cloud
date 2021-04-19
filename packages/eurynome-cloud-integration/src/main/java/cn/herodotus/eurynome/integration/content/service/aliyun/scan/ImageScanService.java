package cn.herodotus.eurynome.integration.content.service.aliyun.scan;

import cn.herodotus.eurynome.integration.content.domain.aliyun.base.Response;
import cn.herodotus.eurynome.integration.content.domain.aliyun.image.*;
import com.aliyuncs.green.model.v20180509.ImageAsyncScanRequest;
import com.aliyuncs.green.model.v20180509.ImageAsyncScanResultsRequest;
import com.aliyuncs.green.model.v20180509.ImageSyncScanRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Description: 阿里图片审核服务类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/14 16:04
 */
@Slf4j
@Service
public class ImageScanService extends AbstractScanService{

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
}
