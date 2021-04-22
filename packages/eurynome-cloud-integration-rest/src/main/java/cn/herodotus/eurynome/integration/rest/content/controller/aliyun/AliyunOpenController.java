package cn.herodotus.eurynome.integration.rest.content.controller.aliyun;

import cn.herodotus.eurynome.integration.content.domain.aliyun.image.ImageQueryResponse;
import cn.herodotus.eurynome.integration.content.domain.aliyun.video.VideoQueryResponse;
import cn.herodotus.eurynome.integration.content.properties.AliyunProperties;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
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
public class AliyunOpenController {

    @Autowired
    private AliyunProperties aliyunProperties;

    @PostMapping("/video/callback")
    public VideoQueryResponse videoCallback(String checksum, String content) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(checksum), "checksum 为空");
        Preconditions.checkArgument(StringUtils.isNotEmpty(content), "content 为空");

        log.debug("[Eurynome] |- Aliyun Content Scan Video Callback get result:- checksum: {}, content: {}", checksum, content);

        String verify = SecureUtil.sha256(aliyunProperties.getUid() + aliyunProperties.getUid() + content);
        if (verify.equals(checksum)) {
            VideoQueryResponse result = JSON.parseObject(content, VideoQueryResponse.class);
            System.out.println(JSON.toJSONString(result));
            return result;
        } else {
            log.error("[Eurynome] |- Aliyun Content Scan Video Callback get checksum is not matched");
            return null;
        }
    }

    @PostMapping("/image/callback")
    public ImageQueryResponse imageCallback(String checksum, String content) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(checksum), "checksum 为空");
        Preconditions.checkArgument(StringUtils.isNotEmpty(content), "content 为空");

        log.debug("[Eurynome] |- Aliyun Content Scan Image Callback get result:- checksum: {}, content: {}", checksum, content);

        String verify = SecureUtil.sha256(aliyunProperties.getUid() + aliyunProperties.getUid() + content);
        if (verify.equals(checksum)) {
            ImageQueryResponse result = JSON.parseObject(content, ImageQueryResponse.class);
            System.out.println(JSON.toJSONString(result));
            return result;
        } else {
            log.error("[Eurynome] |- Aliyun Content Scan Image Callback get checksum is not matched");
            return null;
        }
    }
}
