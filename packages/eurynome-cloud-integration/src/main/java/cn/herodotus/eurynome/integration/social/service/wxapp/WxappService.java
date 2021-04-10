package cn.herodotus.eurynome.integration.social.service.wxapp;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.herodotus.eurynome.integration.social.configuration.WxappConfiguration;
import cn.herodotus.eurynome.integration.social.domain.wxapp.WxappRequest;
import cn.herodotus.eurynome.integration.social.properties.WxappProperties;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>Description: 微信小程序相关服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/3/29 9:27
 */
@Slf4j
@Service
public class WxappService {

	private final WxappProperties wxappProperties;
	private final SubscribeMessageFactory subscribeMessageFactory;

	@Autowired
	public WxappService(WxappProperties wxappProperties, SubscribeMessageFactory subscribeMessageFactory) {
		this.wxappProperties = wxappProperties;
		this.subscribeMessageFactory = subscribeMessageFactory;
	}

	private WxMaService getWxMaService() {
		return this.getWxMaService(null);
	}

	private WxMaService getWxMaService(String appid) {
		if (StringUtils.isBlank(appid)) {
			if (StringUtils.isNotBlank(wxappProperties.getDefaultAppId())) {
				return getWxMaService(wxappProperties.getDefaultAppId());
			} else {
				log.error("[Herodotus] |- Must set [herodotus.social.wx.wxapp.default-app-id] property, or use getWxMaService(String appid)!");
				return null;
			}
		} else {
			return WxappConfiguration.getWxMaService(appid);
		}
	}

	/**
	 * 获取登录后的session信息.
	 *
	 * @param code        登录时获取的 code
	 * @param wxMaService 微信小程序服务
	 * @return {@link WxMaJscode2SessionResult}
	 */
	private WxMaJscode2SessionResult getSessionInfo(String code, WxMaService wxMaService) {
		try {
			WxMaJscode2SessionResult sessionResult = wxMaService.getUserService().getSessionInfo(code);
			log.debug("[Herodotus] |- Weixin Mini App login successfully!");
			return sessionResult;
		} catch (WxErrorException e) {
			log.error("[Herodotus] |- Weixin Mini App login failed! For reason: {}", e.getMessage());
			return null;
		}
	}

	/**
	 * 验证用户完整性
	 *
	 * @param sessionKey  会话密钥
	 * @param rawData     微信用户基本信息
	 * @param signature   数据签名
	 * @param wxMaService 微信小程序服务
	 * @return true 完整， false 不完整
	 */
	private boolean checkUserInfo(String sessionKey, String rawData, String signature, WxMaService wxMaService) {
		if (wxMaService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
			log.debug("[Herodotus] |- Weixin Mini App user info is valid!");
			return true;
		} else {
			log.warn("[Herodotus] |- Weixin Mini App user check failed!");
			return false;
		}
	}

	/**
	 * 解密用户信息
	 *
	 * @param sessionKey    会话密钥
	 * @param encryptedData 消息密文
	 * @param iv            加密算法的初始向量
	 * @param wxMaService   微信小程序服务
	 * @return {@link WxMaUserInfo}
	 */
	private WxMaUserInfo getUserInfo(String sessionKey, String encryptedData, String iv, WxMaService wxMaService) {
		WxMaUserInfo wxMaUserInfo = wxMaService.getUserService().getUserInfo(sessionKey, encryptedData, iv);
		log.debug("[Herodotus] |- Weixin Mini App get user info successfully!");
		return wxMaUserInfo;
	}

	/**
	 * 解密手机号
	 *
	 * 确认下前后端传递的参数有没有做UrlEncode/UrlDecode，因为encryptedData里会包含特殊字符在传递参数时被转义，可能服务器端实际拿到的参数encryptedData并不是前端实际获取到的值，导致SDK调用微信相应接口时无法解密而报错，只要保证前端实际获取到的encryptedData和服务器端调用SDK时传入的encryptedData一致就不会报错的，SDK中方法并无问题；建议让前后台都打印下日志，看下服务端最终使用的参数值是否还是前端获取到的原始值呢。PS：SpringBoot某些场景下form表单参数是会自动做UrlDecode的...
	 *
	 * {@see :https://github.com/Wechat-Group/WxJava/issues/359}
	 *
	 * @param sessionKey    会话密钥
	 * @param encryptedData 消息密文
	 * @param iv            加密算法的初始向量
	 * @param wxMaService   微信小程序服务
	 * @return {@link WxMaPhoneNumberInfo}
	 */
	private WxMaPhoneNumberInfo getPhoneNumberInfo(String sessionKey, String encryptedData, String iv, WxMaService wxMaService) {
		log.info("[Herodotus] |- Weixin Mini App get encryptedData： {}", encryptedData);

		WxMaPhoneNumberInfo wxMaPhoneNumberInfo;
		try {
			wxMaPhoneNumberInfo = wxMaService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
			log.debug("[Herodotus] |- Weixin Mini App get phone number successfully!");
			log.debug("[Herodotus] |- WxMaPhoneNumberInfo : {}", wxMaPhoneNumberInfo.toString());
			return wxMaPhoneNumberInfo;
		} catch (Exception e) {
			log.error("[Herodotus] |- Weixin Mini App get phone number failed!");
			return null;
		}
	}

	private boolean checkUserInfo(String rawData, String signature) {
		return StringUtils.isNotBlank(rawData) && StringUtils.isNotBlank(signature);
	}

	public WxMaJscode2SessionResult login(WxappRequest wxappRequest) {
		return login(wxappRequest.getCode(), wxappRequest.getAppId());
	}

	public WxMaJscode2SessionResult login(String code, String appId) {
		WxMaService wxMaService = getWxMaService(appId);
		if (StringUtils.isNotBlank(code) && ObjectUtils.isNotEmpty(wxMaService)) {
			return this.getSessionInfo(code, wxMaService);
		} else {
			log.error("[Herodotus] |- Weixin Mini App login failed, please check code param!");
			return null;
		}
	}

	public WxMaUserInfo getUserInfo(WxappRequest wxappRequest) {

		WxMaService wxMaService = getWxMaService(wxappRequest.getAppId());

		if (ObjectUtils.isNotEmpty(wxMaService)) {
			// 用户信息校验
			if (checkUserInfo(wxappRequest.getRawData(), wxappRequest.getSignature())) {
				if (checkUserInfo(wxappRequest.getSessionKey(), wxappRequest.getRawData(), wxappRequest.getSignature(), wxMaService)) {
					return null;
				}
			}

			return this.getUserInfo(wxappRequest.getSessionKey(), wxappRequest.getEncryptedData(), wxappRequest.getIv(), wxMaService);
		} else {
			log.error("[Herodotus] |- Weixin Mini App get user info failed!");
			return null;
		}
	}

	public WxMaPhoneNumberInfo getPhoneNumberInfo(WxappRequest wxappRequest) {

		WxMaService wxMaService = getWxMaService(wxappRequest.getAppId());

		if (ObjectUtils.isNotEmpty(wxMaService)) {
			// 用户信息校验
			if (checkUserInfo(wxappRequest.getRawData(), wxappRequest.getSignature())) {
				if (checkUserInfo(wxappRequest.getSessionKey(), wxappRequest.getRawData(), wxappRequest.getSignature(), wxMaService)) {
					return null;
				}
			}

			return this.getPhoneNumberInfo(wxappRequest.getSessionKey(), wxappRequest.getEncryptedData(), wxappRequest.getIv(), wxMaService);
		} else {
			log.error("[Herodotus] |- Weixin Mini App get phone number info failed!");
			return null;
		}
	}

	public boolean sendSubscribeMessage(String appId, String toUserOpenId, String messageType) {
		try {
			WxMaSubscribeMessage wxMaSubscribeMessage = subscribeMessageFactory.getSubscribeMessage(toUserOpenId, messageType);
			return this.sendSubscribeMessage(appId, wxMaSubscribeMessage);
		} catch (Exception e) {
			log.error("[Herodotus] |- Weixin Mini App send subscribe message error: {}", e.getMessage());
			return false;
		}
	}

	public boolean sendSubscribeMessage(String appId, WxMaSubscribeMessage subscribeMessage) {
		WxMaService wxMaService = getWxMaService(appId);
		try {
			wxMaService.getMsgService().sendSubscribeMsg(subscribeMessage);
			log.debug("[Herodotus] |- Send Subscribe Message Successfully!");
			return true;
		} catch (WxErrorException e) {
			log.debug("[Herodotus] |- Send Subscribe Message Failed!", e);
			return false;
		}
	}
}
