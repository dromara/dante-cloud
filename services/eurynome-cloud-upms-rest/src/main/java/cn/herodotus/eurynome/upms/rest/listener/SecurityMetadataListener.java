package cn.herodotus.eurynome.upms.rest.listener;

import cn.herodotus.eurynome.message.stream.channel.SecurityMetadataProcessor;
import cn.herodotus.eurynome.upms.logic.processor.SecurityMetadataAsyncStorage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * <p>Description: 获取其它服务发送过来的RequestMapping信息, 后期如果有消息服务可以考虑移除 </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/21 13:58
 */
@Slf4j
@Component
public class SecurityMetadataListener {

    @Autowired
    private SecurityMetadataAsyncStorage securityMetadataAsyncStorage;

    @StreamListener(SecurityMetadataProcessor.INPUT)
    public void receiveSecurityMetadata(String message) {
        if (StringUtils.isNotBlank(message)) {
            securityMetadataAsyncStorage.store(message);
        } else {
            log.error("[Herodotus] |- Message from SecurityMetadataProcessor is null!");
        }
    }
}
