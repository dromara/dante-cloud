package cn.herodotus.eurynome.integration.rest.content.controller.aliyun;

import cn.herodotus.eurynome.integration.content.domain.aliyun.callback.CallbackResponse;
import cn.herodotus.eurynome.integration.content.domain.aliyun.callback.CallbackResult;
import cn.herodotus.eurynome.integration.content.properties.AliyunProperties;
import cn.herodotus.eurynome.integration.rest.content.logic.aliyun.AliyunScanCallbackHandler;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Description: 阿里云内容检测开放接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/22 16:22
 */
@Slf4j
@RestController
@RequestMapping("/integration/open/aliyun")
@Api(tags = {"第三方集成开放接口", "阿里云开放接口", "阿里云内容检测开放接口"})
public class AliyunCallbackController {

    @Autowired
    private AliyunProperties aliyunProperties;
    @Autowired
    private AliyunScanCallbackHandler aliyunScanCallbackHandler;

    @ApiOperation(value = "阿里视频异步审核Callback支持接口", notes = "阿里云Callback主动调用接口，内部不要调用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checksum", required = true, value = "由用户uid + seed + content拼成字符串，通过SHA256算法生成", dataType = "String", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "content", required = true, value = "callback返回内容", dataType = "String", dataTypeClass = String.class, paramType = "query")
    })
    @PostMapping("/video/callback")
    public CallbackResponse videoCallback(String checksum, String content) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(checksum), "checksum 为空");
        Preconditions.checkArgument(StringUtils.isNotEmpty(content), "content 为空");

        log.debug("[Eurynome] |- Aliyun Content Scan Video Callback get result:- checksum: {}, content: {}", checksum, content);

        String verify = SecureUtil.sha256(aliyunProperties.getUid() + aliyunProperties.getSeed() + content);
        if (verify.equals(checksum)) {
            CallbackResponse callbackResponse = JSON.parseObject(content, CallbackResponse.class);
            log.debug("[Eurynome] |- Aliyun Video Callback Result is: {}", callbackResponse);
            this.deleteFile(callbackResponse);
            return callbackResponse;
        } else {
            log.error("[Eurynome] |- Aliyun Content Scan Video Callback get checksum is not matched");
            return null;
        }
    }

    @ApiOperation(value = "阿里图片异步审核Callback支持接口", notes = "阿里云Callback主动调用接口，内部不要调用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "checksum", required = true, value = "由用户uid + seed + content拼成字符串，通过SHA256算法生成", dataType = "String", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "content", required = true, value = "callback返回内容", dataType = "String", dataTypeClass = String.class, paramType = "query")
    })
    @PostMapping("/image/callback")
    public CallbackResponse imageCallback(String checksum, String content) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(checksum), "checksum 为空");
        Preconditions.checkArgument(StringUtils.isNotEmpty(content), "content 为空");

        log.debug("[Eurynome] |- Aliyun Content Scan Image Callback get result:- checksum: {}, content: {}", checksum, content);

        String verify = SecureUtil.sha256(aliyunProperties.getUid() + aliyunProperties.getSeed() + content);
        if (verify.equals(checksum)) {
            CallbackResponse callbackResponse = JSON.parseObject(content, CallbackResponse.class);
            log.debug("[Eurynome] |- Aliyun Image Callback Result is: {}", callbackResponse);
            this.deleteFile(callbackResponse);
            return callbackResponse;
        } else {
            log.error("[Eurynome] |- Aliyun Content Scan Image Callback get checksum is not matched");
            return null;
        }
    }

    private void deleteFile(CallbackResponse callbackResponse) {
        if (ObjectUtils.isNotEmpty(callbackResponse) && CollectionUtils.isNotEmpty(callbackResponse.getResults())) {
            for (CallbackResult callbackResult : callbackResponse.getResults()) {
                if (callbackResult.getSuggestion().equals("block")) {
                    String fileName = this.getFileName(callbackResponse.getUrl());
                    aliyunScanCallbackHandler.deleteIllegalMedia(fileName);
                }
            }
        }
    }

    private String getFileName(String url) {
        if (StringUtils.contains(url, "?")) {
            url = StringUtils.substringBefore(url, "?");
        }
        return StringUtils.substringAfter(url, aliyunProperties.getOss().getBaseUrl() + "/");
    }
}
