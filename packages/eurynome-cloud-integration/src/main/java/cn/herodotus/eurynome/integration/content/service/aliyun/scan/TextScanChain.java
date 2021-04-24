package cn.herodotus.eurynome.integration.content.service.aliyun.scan;

import cn.herodotus.eurynome.integration.content.domain.aliyun.ScanRequest;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * <p>Description: 阿里文本扫描链 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/23 13:50
 */
@Service
public class TextScanChain extends AbstractScanChain{

    private static final int CONTENT_LENGTH = 10000;

    @Autowired
    private TextScanService textScanService;

    @Override
    public boolean execute(ScanRequest scanRequest) {
        String content = scanRequest.getText();
        if (StringUtils.isNotBlank(content)) {
            int length = StrUtil.length(content);
            List<String> contents;
            if (length > CONTENT_LENGTH) {
                String[] texts = StrUtil.split(content, CONTENT_LENGTH);
                contents = Arrays.asList(texts);
            } else {
                contents = ImmutableList.of(content);
            }
            return textScanService.executeScan(contents);
        }

        return false;
    }
}
