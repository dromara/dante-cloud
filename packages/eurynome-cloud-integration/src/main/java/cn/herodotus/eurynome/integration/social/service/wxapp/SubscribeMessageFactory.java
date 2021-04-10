package cn.herodotus.eurynome.integration.social.service.wxapp;

import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.herodotus.eurynome.integration.social.definition.wxapp.SubscribeMessageHandler;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Description: 订阅消息模版类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/9 16:27
 */
@Service
public class SubscribeMessageFactory {

	Map<String, SubscribeMessageHandler> handlers = new ConcurrentHashMap<>();

	public WxMaSubscribeMessage getSubscribeMessage(String toUser, String messageType) throws Exception {
		SubscribeMessageHandler handler = handlers.get(messageType);
		if (ObjectUtils.isNotEmpty(handler)) {
			return handler.getSubscribeMessage(toUser, messageType);
		} else {
			throw new RuntimeException("No SubscribeMessageHandler defined");
		}
	}
}
