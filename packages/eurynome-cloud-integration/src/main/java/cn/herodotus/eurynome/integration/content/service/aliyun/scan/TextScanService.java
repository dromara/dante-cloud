package cn.herodotus.eurynome.integration.content.service.aliyun.scan;

import cn.herodotus.eurynome.integration.content.domain.aliyun.base.Response;
import cn.herodotus.eurynome.integration.content.domain.aliyun.text.*;
import cn.herodotus.eurynome.integration.definition.AliyunConstants;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.green.model.v20180509.TextScanRequest;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public Response<String> feedback(List<TextFeedbackRequest> textFeedbackRequests) {
        String data = JSON.toJSONString(textFeedbackRequests);
        String response = this.scan(data, this.textFeedbackRequest);
        Response<String> feedback = this.parseResult(response, String.class);
        log.debug("[Eurynome] |- Aliyun Text Feedback result is: {}", feedback.toString());
        return feedback;
    }

    public TextSyncRequest buildSyncRequest(List<TextTask> tasks, List<String> scenes, String bizType) {
        TextSyncRequest textSyncRequest = new TextSyncRequest();
        textSyncRequest.setBizType(bizType);
        textSyncRequest.setScenes(scenes);
        textSyncRequest.setTasks(tasks);
        return textSyncRequest;
    }

    public TextSyncRequest buildDefaultAsyncRequest(List<String> contents) {
        List<TextTask> tasks = contents.stream().map(content -> {
            TextTask textTask = new TextTask();
            textTask.setContent(content);
            return textTask;
        }).collect(Collectors.toList());

        List<String> scenes = ImmutableList.of(AliyunConstants.SCENE_ANTISPAM);

        return buildSyncRequest(tasks, scenes, AliyunConstants.BIZ_TYPE);
    }

    public boolean asyncAnalyse(List<TextSyncResponse> responses) {
        for (TextSyncResponse textSyncResponse : responses) {
            List<TextResult> textResults = textSyncResponse.getResults();
            for (TextResult textResult : textResults) {
                if (textResult.getSuggestion().equals("block")) {
                    log.warn("[Eurynome] |- Catch the block content: {}", textResult);
                    return false;
                }
            }
        }

        log.debug("[Eurynome] |- Content check result is ok!");
        return true;
    }

    public boolean executeScan(List<String> contents) {
        TextSyncRequest textSyncRequest = this.buildDefaultAsyncRequest(contents);
        Response<List<TextSyncResponse>> response = this.scan(textSyncRequest);
        if (ObjectUtils.isNotEmpty(response) && response.getCode().equals(HttpStatus.SC_OK)) {
            return this.asyncAnalyse(response.getData());
        } else {
            log.error("[Eurynome] |- Aliyun Text Scan catch error! result: {}", response);
            return false;
        }
    }
}
