package cn.herodotus.eurynome.integration.push.service.apns;

import cn.herodotus.eurynome.integration.push.domain.apns.ApnsResponse;
import cn.herodotus.eurynome.integration.push.properties.ApnsProperties;
import com.turo.pushy.apns.ApnsClient;
import com.turo.pushy.apns.ApnsPushNotification;
import com.turo.pushy.apns.PushNotificationResponse;
import com.turo.pushy.apns.util.ApnsPayloadBuilder;
import com.turo.pushy.apns.util.SimpleApnsPushNotification;
import com.turo.pushy.apns.util.TokenUtil;
import com.turo.pushy.apns.util.concurrent.PushNotificationFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;

/**
 * <p>Description: Apns服务类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/5 14:41
 */
@Slf4j
@Service
public class ApnsService {

    private final Semaphore semaphore = new Semaphore(10000);

    @Autowired
    private ApnsProperties apnsProperties;
    @Autowired
    private ApnsClient apnsClient;

    private String createPayload(String alertTitle, String alertBody, int badge, Map<String, Object> customProperty) {
        ApnsPayloadBuilder payloadBuilder = new ApnsPayloadBuilder();

        if (alertBody != null && alertTitle != null) {
            payloadBuilder.setAlertBody(alertBody);
            payloadBuilder.setAlertTitle(alertTitle);
        }

        //如果badge小于0，则不推送这个右上角的角标，主要用于消息盒子新增或者已读时，更新此状态
        if (badge > 0) {
            payloadBuilder.setBadgeNumber(badge);
        }

        //将所有的附加参数全部放进去
        if (customProperty != null) {
            for (Map.Entry<String, Object> map : customProperty.entrySet()) {
                payloadBuilder.addCustomProperty(map.getKey(), map.getValue());
            }
        }

        // true：表示的是产品发布推送服务 false：表示的是产品测试推送服务
        payloadBuilder.setContentAvailable(!apnsProperties.isSandbox());

        return payloadBuilder.buildWithDefaultMaximumLength();
    }

    private SimpleApnsPushNotification createNotification(String deviceToken, String bundleId, String payload) {
        final String token = TokenUtil.sanitizeTokenString(deviceToken);
        SimpleApnsPushNotification pushNotification = new SimpleApnsPushNotification(token, bundleId, payload);
        log.debug("[Eurynome] |- Create Apns Push Notification for device: {}", deviceToken);
        return pushNotification;
    }

    private SimpleApnsPushNotification createPushNotification(String deviceToken, String alertTitle, String alertBody, int badge) {
        return createPushNotification(deviceToken, alertTitle, alertBody, badge, null);
    }

    private SimpleApnsPushNotification createPushNotification(String deviceToken, String alertTitle, String alertBody, int badge, Map<String, Object> customProperty) {
        String payload = createPayload(alertTitle, alertBody, badge, customProperty);
        return createNotification(deviceToken, apnsProperties.getBundleId(), payload);
    }

    private ApnsResponse getResult(PushNotificationFuture<ApnsPushNotification, PushNotificationResponse<ApnsPushNotification>> future) {
        try {
            PushNotificationResponse<ApnsPushNotification> pushNotificationResponse = future.get();
            return new ApnsResponse(pushNotificationResponse.getPushNotification(), pushNotificationResponse.isAccepted(), pushNotificationResponse.getRejectionReason());
        } catch (InterruptedException | ExecutionException e) {
            return new ApnsResponse(future.getPushNotification(), false, "sent Interrupted");
        }
    }

    private PushNotificationFuture<ApnsPushNotification, PushNotificationResponse<ApnsPushNotification>> asyncPushNotification(ApnsPushNotification apnsPushNotification) {
        try {
            semaphore.acquire();
            final PushNotificationFuture<ApnsPushNotification, PushNotificationResponse<ApnsPushNotification>> sendNotificationFuture = apnsClient.sendNotification(apnsPushNotification);
            sendNotificationFuture.addListener(future -> semaphore.release());
            return sendNotificationFuture;
        } catch (InterruptedException e) {
            log.error("[Eurynome] |- Failed to  acquire semaphore.");
            return null;
        }
    }

    public List<ApnsResponse> send(List<String> deviceTokens, String title, String body, int badge) {

        List<PushNotificationFuture<ApnsPushNotification, PushNotificationResponse<ApnsPushNotification>>> pushNotificationFutures = new ArrayList<>();

        // 异步发送推送请求
        for (String deviceToken : deviceTokens) {
            ApnsPushNotification apnsPushNotification = createPushNotification(deviceToken, title, body, badge);
            pushNotificationFutures.add(asyncPushNotification(apnsPushNotification));
        }

        // 阻塞直到最后一个请求完成
        List<ApnsResponse> result = new ArrayList<>();
        for (PushNotificationFuture<ApnsPushNotification, PushNotificationResponse<ApnsPushNotification>> pushNotificationFuture : pushNotificationFutures) {
            ApnsResponse response = getResult(pushNotificationFuture);
            result.add(response);
        }

        return result;
    }

    /**
     * 推送消息到指定设备
     *
     * @param deviceToken 设备token
     * @param title       消息标题
     * @param body        消息内容
     * @param badge       小图标
     * @return {@link ApnsResponse}
     */
    public ApnsResponse send(String deviceToken, String title, String body, int badge) {
        ApnsPushNotification apnsPushNotification = createPushNotification(deviceToken, title, body, badge);
        return getResult(asyncPushNotification(apnsPushNotification));
    }
}
