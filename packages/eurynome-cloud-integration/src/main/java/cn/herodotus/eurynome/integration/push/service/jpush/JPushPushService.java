package cn.herodotus.eurynome.integration.push.service.jpush;

import cn.herodotus.eurynome.integration.push.domain.jpush.JPushMessage;
import cn.herodotus.eurynome.integration.push.properties.JPushProperties;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosAlert;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Description: 极光推送操作 tag, alias服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/20 9:14
 */
@Slf4j
@Service
public class JPushPushService {

    @Autowired
    private JPushClient jPushClient;
    @Autowired
    private JPushProperties jPushProperties;

    /**
     * 执行推送
     *
     * @param pushPayload 消息体
     * @return 成功时返回JPush消息对象 {@link PushResult}，否则返回null
     */
    private PushResult execute(PushPayload pushPayload) {
        log.debug("[Eurynome] |- JPush begin to push message : {}", pushPayload.toJSON());

        try {
            return jPushClient.sendPush(pushPayload);
        } catch (APIConnectionException e) {
            log.error("[Eurynome] |- JPush Server Connnect failed, when sendPush!", e);
        } catch (APIRequestException e) {
            log.error("[Eurynome] |- The JPush Server is not responding, when sendPush", e);
            log.error("[Eurynome] |- The error detail is : status={}, errorCode={}, errorMessage={}", e.getStatus(), e.getErrorCode(), e.getErrorMessage());
        }

        return null;
    }

    private PushPayload createPushPayload(JPushMessage jPushMessage, Audience audience) {
        Preconditions.checkArgument(ObjectUtils.isNotEmpty(jPushMessage), "消息为空");
        Preconditions.checkArgument(StringUtils.isNotEmpty(jPushMessage.getContent()), "消息内容为空");

        IosAlert iosAlert = IosAlert.newBuilder().setTitleAndBody(jPushMessage.getTitle(), null, jPushMessage.getContent()).build();

        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(audience)
                .setOptions(Options.newBuilder().setApnsProduction(jPushProperties.getApnsProduction()).build())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(
                                IosNotification.newBuilder()
                                        .setAlert(iosAlert)
                                        .setSound("default")
                                        .setBadge(jPushMessage.getBadge())
                                        .addExtras(jPushMessage.getExtras()).build())
                        .addPlatformNotification(
                                AndroidNotification.newBuilder()
                                        .setAlert(jPushMessage.getContent())
                                        .setTitle(jPushMessage.getTitle())
                                        .setBuilderId(1)
                                        .setPriority(jPushMessage.getPriority())
                                        .addExtras(jPushMessage.getExtras()).build())
                        .build()
                ).build();
    }

    /**
     * 进行推送
     *
     * @param payload 消息体
     * @return 成功时返回消息ID
     */
    private PushResult push(PushPayload payload) {
        if (jPushProperties.getRetryMaxAttempts() != null && jPushProperties.getRetryMaxAttempts() > 0) {
            try {
                return pushWithRetry(payload);
            } catch (Exception e) {
                log.error("推送时发生异常", e);
            }
            return null;
        } else {
            return execute(payload);
        }
    }

    /**
     * 进行推送(支持重试)
     *
     * @param payload 消息体
     * @return 成功时返回消息ID
     */

    private PushResult pushWithRetry(PushPayload payload) throws Exception {
        PushResult msgId = execute(payload);
        if (msgId == null) {
            long sleepTime = jPushProperties.getRetryInterval() > 0 ? jPushProperties.getRetryInterval() : 500L;
            for (int i = 0; i < jPushProperties.getRetryMaxAttempts(); i++) {
                Thread.sleep(sleepTime);
                msgId = execute(payload);
                if (msgId != null) {
                    break;
                }
                sleepTime *= 2;
            }
        }
        return msgId;
    }

    /**
     * 根据设备ID推送
     *
     * @param registrationIds 设备Registration ID列表
     * @param jPushMessage    消息
     * @return 成功时返回消息ID
     */
    public PushResult pushToDevices(List<String> registrationIds, JPushMessage jPushMessage) {
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(registrationIds), "设备Registration ID为空");
        Preconditions.checkArgument(registrationIds.size() <= 1000, "设备Registration ID不超过1000个");
        return push(createPushPayload(jPushMessage, Audience.registrationId(registrationIds)));
    }

    /**
     * 根据别名推送
     *
     * @param alias        别名列表
     * @param jPushMessage 消息
     * @return 成功时返回消息ID
     */
    public PushResult pushToAliases(List<String> alias, JPushMessage jPushMessage) {
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(alias), "别名为空");
        Preconditions.checkArgument(alias.size() <= 1000, "别名不超过1000个");
        return push(createPushPayload(jPushMessage, Audience.alias(alias)));
    }

    /**
     * 根据标签推送
     *
     * @param tags         标签列表
     * @param jPushMessage 消息
     * @return 成功时返回消息ID
     */
    public PushResult pushToTags(List<String> tags, JPushMessage jPushMessage) {
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(tags), "标签为空");
        Preconditions.checkArgument(tags.size() <= 1000, "标签不超过1000个");
        return push(createPushPayload(jPushMessage, Audience.tag(tags)));
    }

    /**
     * 推送给所有客户端
     *
     * @param jPushMessage 消息
     * @return 成功时返回消息ID
     */
    public PushResult pushToAll(JPushMessage jPushMessage) {
        return push(createPushPayload(jPushMessage, Audience.all()));
    }
}
