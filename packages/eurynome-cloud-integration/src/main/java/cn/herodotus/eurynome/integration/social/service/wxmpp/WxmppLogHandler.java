package cn.herodotus.eurynome.integration.social.service.wxmpp;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <p>Description: 微信公众号日志处理 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/7 13:26
 */
@Slf4j
@Component
public class WxmppLogHandler implements WxMpMessageHandler {
	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
		// 代码逻辑未实现,仅仅简单打印信息
		log.info("\n接收到请求消息，内容：{}", JSON.toJSONString(wxMpXmlMessage));
		return null;
	}
}
