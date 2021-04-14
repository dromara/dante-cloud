package cn.herodotus.eurynome.integration.compliance.service.aliyun;

import cn.herodotus.eurynome.integration.compliance.domain.aliyun.base.Response;
import cn.herodotus.eurynome.integration.compliance.domain.aliyun.text.TextFeedbackRequest;
import cn.herodotus.eurynome.integration.compliance.domain.aliyun.text.TextSyncRequest;
import cn.herodotus.eurynome.integration.compliance.domain.aliyun.text.TextSyncResponse;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.green.model.v20180509.TextScanRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Description: 阿里文本审核服务类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/13 15:38
 */
@Slf4j
@Service
public class TextScanService extends AbstractScanService {

    @Autowired
    private TextScanRequest textScanRequest;
    @Autowired
    private com.aliyuncs.green.model.v20180509.TextFeedbackRequest textFeedbackRequest;

    public Response<List<TextSyncResponse>> scan(TextSyncRequest textSyncRequest) {
        String jsonString = this.scan(textSyncRequest, textScanRequest);
        Response<List<TextSyncResponse>> entity = this.parseListResult(jsonString, TextSyncResponse.class);
        log.debug("[Eurynome] |- Aliyun Text Scan result is: {}", entity.toString());
        return entity;
    }

    public Response<String> feedback(TextFeedbackRequest textFeedbackRequest) {
        String data = JSON.toJSONString(textFeedbackRequest);
        String response = this.scan(data, this.textFeedbackRequest);
        Response<String> feedback = this.parseResult(response, String.class);
        log.debug("[Eurynome] |- Aliyun Text Feedback result is: {}", feedback.toString());
        return feedback;
    }
}
