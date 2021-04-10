package cn.herodotus.eurynome.integration.social.definition.wxapp;

import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.herodotus.eurynome.integration.social.properties.WxappProperties;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>Description: 小程序订阅消息信息处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/9 16:22
 */
public abstract class SubscribeMessageHandler {

    @Autowired
    private WxappProperties wxappProperties;

    /**
     * 组装模版信息并获取WxJava定义的WxMaSubscribeMessage
     *
     * @param toUser      发送订阅消息的目标用户OpenId
     * @param subscribeId WxappProperties中配置的subscribeId值，这个值需要配置到具体SubscribeMessageHandler实现类的注解上
     * @return {@link WxMaSubscribeMessage}
     */
    public abstract WxMaSubscribeMessage getSubscribeMessage(String toUser, String subscribeId);

    protected WxappProperties.Subscribe getSubscribeConfig(String messageId) {
        List<WxappProperties.Subscribe> subscribes = this.wxappProperties.getSubscribes();
        return subscribes.stream().filter(a -> a.getSubscribeId().equals(messageId)).findFirst().orElse(new WxappProperties.Subscribe());
    }
}
