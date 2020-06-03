package cn.herodotus.eurynome.upms.rest.service;

import cn.herodotus.eurynome.upms.logic.processor.SecurityMetadataAsyncStorage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * <p>Description: 获取其它服务发送过来的RequestMapping信息, 后期如果有消息服务可以考虑移除 </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/21 13:58
 */
@Slf4j
@Service
public class SecurityMetadataConsumer {

    @Autowired
    private SecurityMetadataAsyncStorage securityMetadataAsyncStorage;

    @KafkaListener(topics = {"security.metadata"}, groupId = "herodotus.upms")
    public void serviceResourceMessage(String message) {
        if (StringUtils.isNotBlank(message)) {
            securityMetadataAsyncStorage.store(message);
        } else {
            log.error("[Herodotus] |- Message from Security Metadata Producer is null!");
        };
    }
}
