package cn.herodotus.eurynome.upms.rest.service;

import cn.herodotus.eurynome.message.stream.channel.SecurityMetadataSink;
import cn.herodotus.eurynome.upms.logic.processor.SecurityMetadataAsyncStorage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
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
@EnableBinding(SecurityMetadataSink.class)
public class SecurityMetadataMessageConsumer {

    @Autowired
    private SecurityMetadataAsyncStorage securityMetadataAsyncStorage;

    @StreamListener(SecurityMetadataSink.INPUT)
    public void receive(String message) {
        if (StringUtils.isNotBlank(message)) {
            securityMetadataAsyncStorage.store(message);
        } else {
            log.error("[Herodotus] |- Message from SecurityMetadataSource is null!");
        }
    }
}
