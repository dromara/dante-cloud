package cn.herodotus.eurynome.integration.content.service.aliyun.scan;

import cn.herodotus.eurynome.integration.content.domain.aliyun.ScanRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Description: 视频扫描链 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/23 13:31
 */
@Service
public class VideoScanChain extends AbstractScanChain {

    @Autowired
    private VideoScanService videoScanService;

    @Autowired
    private ImageScanChain imageScanChain;

    @Override
    public boolean execute(ScanRequest scanRequest) {
        List<String> videoUrls = scanRequest.getVideoUrls();
        if (CollectionUtils.isNotEmpty(videoUrls)) {
            videoScanService.executeScan(videoUrls);
        }
        return imageScanChain.execute(scanRequest);
    }
}
