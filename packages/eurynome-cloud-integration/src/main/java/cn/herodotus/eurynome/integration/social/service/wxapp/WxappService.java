package cn.herodotus.eurynome.integration.social.service.wxapp;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.*;
import cn.herodotus.eurynome.integration.social.configuration.WxappConfiguration;
import cn.herodotus.eurynome.integration.social.domain.wxapp.WxappRequest;
import cn.herodotus.eurynome.integration.social.properties.WxappProperties;
import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

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
                log.error("[Eurynome] |- Must set [herodotus.social.wx.wxapp.default-app-id] property, or use getWxMaService(String appid)!");
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
            log.debug("[Eurynome] |- Weixin Mini App login successfully!");
            return sessionResult;
        } catch (WxErrorException e) {
            log.error("[Eurynome] |- Weixin Mini App login failed! For reason: {}", e.getMessage());
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
            log.debug("[Eurynome] |- Weixin Mini App user info is valid!");
            return true;
        } else {
            log.warn("[Eurynome] |- Weixin Mini App user check failed!");
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
        log.debug("[Eurynome] |- Weixin Mini App get user info successfully!");
        return wxMaUserInfo;
    }

    /**
     * 解密手机号
     * <p>
     * 确认下前后端传递的参数有没有做UrlEncode/UrlDecode，因为encryptedData里会包含特殊字符在传递参数时被转义，可能服务器端实际拿到的参数encryptedData并不是前端实际获取到的值，导致SDK调用微信相应接口时无法解密而报错，只要保证前端实际获取到的encryptedData和服务器端调用SDK时传入的encryptedData一致就不会报错的，SDK中方法并无问题；建议让前后台都打印下日志，看下服务端最终使用的参数值是否还是前端获取到的原始值呢。PS：SpringBoot某些场景下form表单参数是会自动做UrlDecode的...
     * <p>
     * {@see :https://github.com/Wechat-Group/WxJava/issues/359}
     *
     * @param sessionKey    会话密钥
     * @param encryptedData 消息密文
     * @param iv            加密算法的初始向量
     * @param wxMaService   微信小程序服务
     * @return {@link WxMaPhoneNumberInfo}
     */
    private WxMaPhoneNumberInfo getPhoneNumberInfo(String sessionKey, String encryptedData, String iv, WxMaService wxMaService) {
        log.info("[Eurynome] |- Weixin Mini App get encryptedData： {}", encryptedData);

        WxMaPhoneNumberInfo wxMaPhoneNumberInfo;
        try {
            wxMaPhoneNumberInfo = wxMaService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
            log.debug("[Eurynome] |- Weixin Mini App get phone number successfully!");
            log.debug("[Eurynome] |- WxMaPhoneNumberInfo : {}", wxMaPhoneNumberInfo.toString());
            return wxMaPhoneNumberInfo;
        } catch (Exception e) {
            log.error("[Eurynome] |- Weixin Mini App get phone number failed!");
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
            log.error("[Eurynome] |- Weixin Mini App login failed, please check code param!");
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
            log.error("[Eurynome] |- Weixin Mini App get user info failed!");
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
            log.error("[Eurynome] |- Weixin Mini App get phone number info failed!");
            return null;
        }
    }

    /**
     * 发送微信小程序订阅消息
     *
     * @param appId       小程序appId
     * @param toUser      发送订阅消息的目标用户OpenId
     * @param subscribeId WxappProperties 中 配置的subscribeId值，这个值需要配置到具体的SubscribeMessageHandler实现类注解上
     * @return true 发送成功，false 发送失败，或者参数subscribeId配置不对，无法获取相应的WxMaSubscribeMessage
     */
    public boolean sendSubscribeMessage(String appId, String toUser, String subscribeId) {
        WxMaSubscribeMessage wxMaSubscribeMessage = subscribeMessageFactory.getSubscribeMessage(toUser, subscribeId);
        if (BeanUtil.isNotEmpty(wxMaSubscribeMessage)) {
            return this.sendSubscribeMessage(appId, wxMaSubscribeMessage);
        } else {
            log.error("[Eurynome] |- Generate WxMaSubscribeMessage error");
            return false;
        }
    }

    public boolean sendSubscribeMessage(String appId, WxMaSubscribeMessage subscribeMessage) {
        WxMaService wxMaService = getWxMaService(appId);
        try {
            wxMaService.getMsgService().sendSubscribeMsg(subscribeMessage);
            log.debug("[Eurynome] |- Send Subscribe Message Successfully!");
            return true;
        } catch (WxErrorException e) {
            log.debug("[Eurynome] |- Send Subscribe Message Failed!", e);
            return false;
        }
    }

    /**
     * 检查一段文本是否含有违法违规内容。
     * 应用场景举例：
     * · 用户个人资料违规文字检测；
     * · 媒体新闻类用户发表文章，评论内容检测；
     * · 游戏类用户编辑上传的素材(如答题类小游戏用户上传的问题及答案)检测等。 频率限制：单个 appId 调用上限为 4000 次/分钟，2,000,000 次/天*
     * · 详情请见: https://developers.weixin.qq.com/miniprogram/dev/api/open-api/sec-check/msgSecCheck.html
     *
     * @param appId   小程序appId
     * @param message 需要检测的字符串
     * @return 是否违规 boolean
     */
    public boolean checkMessage(String appId, String message) {
        WxMaService wxMaService = getWxMaService(appId);
        try {
            wxMaService.getSecCheckService().checkMessage(message);
            log.debug("[Eurynome] |- Check Message Successfully!");
            return true;
        } catch (WxErrorException e) {
            log.debug("[Eurynome] |- Check Message Failed!", e);
            return false;
        }
    }

    /**
     * 校验一张图片是否含有违法违规内容
     *
     * @param appId   小程序appId
     * @param fileUrl 需要检测图片的网地址
     * @return 是否违规 boolean
     */
    public boolean checkImage(String appId, String fileUrl) {
        WxMaService wxMaService = getWxMaService(appId);
        try {
            wxMaService.getSecCheckService().checkImage(fileUrl);
            log.debug("[Eurynome] |- Check Image Successfully!");
            return true;
        } catch (WxErrorException e) {
            log.debug("[Eurynome] |- Check Image Failed! Detail is ：{}",  e.getMessage());
            return false;
        }
    }

    /**
     * 校验一张图片是否含有违法违规内容.
     * <p>
     * 应用场景举例：
     * 1）图片智能鉴黄：涉及拍照的工具类应用(如美拍，识图类应用)用户拍照上传检测；电商类商品上架图片检测；媒体类用户文章里的图片检测等；
     * 2）敏感人脸识别：用户头像；媒体类用户文章里的图片检测；社交类用户上传的图片检测等。频率限制：单个 appId 调用上限为 1000 次/分钟，100,000 次/天
     * 详情请见: https://developers.weixin.qq.com/miniprogram/dev/api/open-api/sec-check/imgSecCheck.html
     *
     * @param appId 小程序appId
     * @param file  图片文件
     * @return 是否违规 boolean
     */
    public boolean checkImage(String appId, File file) {
        WxMaService wxMaService = getWxMaService(appId);
        try {
            wxMaService.getSecCheckService().checkImage(file);
            log.debug("[Eurynome] |- Check Image Successfully!");
            return true;
        } catch (WxErrorException e) {
            log.debug("[Eurynome] |- Check Image Failed! Detail is ：{}",  e.getMessage());
            return false;
        }
    }

    /**
     * 异步校验图片/音频是否含有违法违规内容。
     * 应用场景举例：
     * 语音风险识别：社交类用户发表的语音内容检测；
     * 图片智能鉴黄：涉及拍照的工具类应用(如美拍，识图类应用)用户拍照上传检测；电商类商品上架图片检测；媒体类用户文章里的图片检测等；
     * 敏感人脸识别：用户头像；媒体类用户文章里的图片检测；社交类用户上传的图片检测等。
     * 频率限制：
     * 单个 appId 调用上限为 2000 次/分钟，200,000 次/天；文件大小限制：单个文件大小不超过10M
     * 详情请见:
     * https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/sec-check/security.mediaCheckAsync.html
     *
     * @param appId 小程序appId
     * @param mediaUrl 要检测的多媒体url
     * @param mediaType  媒体类型 {@link cn.binarywang.wx.miniapp.constant.WxMaConstants.SecCheckMediaType}
     * @return 微信检测结果 WxMaMediaAsyncCheckResult {@link WxMaMediaAsyncCheckResult}
     */
    public WxMaMediaAsyncCheckResult mediaAsyncCheck(String appId, String mediaUrl, int mediaType) {
        WxMaService wxMaService = getWxMaService(appId);

        WxMaMediaAsyncCheckResult wxMaMediaAsyncCheckResult = null;
        try {
            wxMaMediaAsyncCheckResult = wxMaService.getSecCheckService().mediaCheckAsync(mediaUrl, mediaType);
            log.debug("[Eurynome] |- Media Async Check Successfully!");
        } catch (WxErrorException e) {
            log.debug("[Eurynome] |- Media Async Check Failed! Detail is ：{}",  e.getMessage());
        }

        return wxMaMediaAsyncCheckResult;
    }

}
