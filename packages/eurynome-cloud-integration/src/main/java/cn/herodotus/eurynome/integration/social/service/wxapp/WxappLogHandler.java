package cn.herodotus.eurynome.integration.social.service.wxapp;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaKefuMessage;
import cn.binarywang.wx.miniapp.bean.WxMaMessage;
import cn.binarywang.wx.miniapp.message.WxMaMessageHandler;
import cn.binarywang.wx.miniapp.message.WxMaXmlOutMessage;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <p>Description: 微信小程序Log处理器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/7 12:44
 */
@Slf4j
@Component
public class WxappLogHandler implements WxMaMessageHandler {

	@Override
	public WxMaXmlOutMessage handle(WxMaMessage wxMaMessage, Map<String, Object> context, WxMaService wxMaService, WxSessionManager sessionManager) throws WxErrorException {
		log.info("收到消息：" + wxMaMessage.toString());
		wxMaService.getMsgService().sendKefuMsg(WxMaKefuMessage.newTextBuilder().content("收到信息为：" + wxMaMessage.toJson())
				.toUser(wxMaMessage.getFromUser()).build());
		return null;
	}
}
