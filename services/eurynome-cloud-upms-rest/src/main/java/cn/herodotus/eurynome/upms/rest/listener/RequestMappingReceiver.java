package cn.herodotus.eurynome.upms.rest.listener;

import cn.herodotus.eurynome.upms.logic.processor.RequestMappingProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * <p>Description: 获取其它服务发送过来的RequestMapping信息, 后期如果有消息服务可以考虑移除 </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/21 13:58
 */
@Component
public class RequestMappingReceiver {

    @Autowired
    private RequestMappingProcessor requestMappingProcessor;

    @KafkaListener(topics = {"AUTHORITY_AUTO_SCAN"}, groupId = "LUBAN.AUTHORITY")
    public void serviceResourceMessage(String message) {
        requestMappingProcessor.storeRequestMappings(message);
    }
}
