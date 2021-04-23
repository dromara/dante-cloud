package cn.herodotus.eurynome.integration.content.service.aliyun.scan;

import cn.herodotus.eurynome.integration.content.domain.aliyun.ScanRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Description: 图片扫描链 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/23 9:47
 */
@Service
public class ImageScanChain extends AbstractScanChain {

    @Autowired
    private ImageScanService imageScanService;

    @Autowired
    private TextScanChain textScanChain;

    @Override
    public boolean execute(ScanRequest scanRequest) {

        List<String> imageUrls = scanRequest.getImageUrls();
        if (CollectionUtils.isNotEmpty(imageUrls)) {
            imageScanService.executeScan(imageUrls);
        }

        return textScanChain.execute(scanRequest);
    }
}
