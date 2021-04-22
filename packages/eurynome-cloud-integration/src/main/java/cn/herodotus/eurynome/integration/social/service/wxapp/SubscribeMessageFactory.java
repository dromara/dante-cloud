package cn.herodotus.eurynome.integration.social.service.wxapp;

import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.herodotus.eurynome.integration.social.definition.wxapp.SubscribeMessageHandler;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private final Map<String, SubscribeMessageHandler> handlers = new ConcurrentHashMap<>();

	/**
	 * 根据subscribeId，找到对应的SubscribeMessageHandler，直接获取拼装好的WxMaSubscribeMessage对象。
	 *
	 * @param toUser 目标用户的微信小程序OpenId
	 * @param subscribeId 自定义的模版消息标记，与具体的SubscribeMessageHandler以及微信小程序配置属性中subscribes的message-type值对应
	 * @return WxJava封装的WxMaSubscribeMessage对象 {@link WxMaSubscribeMessage}，如果没有找到对应的SubscribeMessageHandler实现就是空
	 */
	public WxMaSubscribeMessage getSubscribeMessage(String toUser, String subscribeId) {
		return this.getSubscribeMessage(toUser, subscribeId, null);
	}

	/**
	 * 	根据subscribeId，找到对应的SubscribeMessageHandler，利用传入的templateParams中的值，拼装WxMaSubscribeMessage对象。
	 * @param toUser 目标用户的微信小程序OpenId
	 * @param subscribeId 自定义的模版消息标记，与具体的SubscribeMessageHandler以及微信小程序配置属性中subscribes的message-type值对应
	 * @param templateParams 与订阅消息模版参数对应的属性值
	 * @return WxJava封装的WxMaSubscribeMessage对象 {@link WxMaSubscribeMessage}，如果没有找到对应的SubscribeMessageHandler实现就是空
	 */
	public WxMaSubscribeMessage getSubscribeMessage(String toUser, String subscribeId, Map<String, Object> templateParams) {
		SubscribeMessageHandler handler = handlers.get(subscribeId);
		if (ObjectUtils.isNotEmpty(handler)) {
			return handler.getSubscribeMessage(toUser, subscribeId, templateParams);
		} else {
			return null;
		}
	}
}
