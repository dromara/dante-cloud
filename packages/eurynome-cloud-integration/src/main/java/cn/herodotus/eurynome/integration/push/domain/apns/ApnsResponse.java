package cn.herodotus.eurynome.integration.push.domain.apns;

import com.google.common.base.MoreObjects;
import com.turo.pushy.apns.ApnsPushNotification;

/**
 * <p>Description: Apns 响应 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/5 16:15
 */
public class ApnsResponse {

    final ApnsPushNotification notification;
    final Boolean isAccepted;
    final String  reason;

    public ApnsResponse(ApnsPushNotification notification, Boolean isAccepted, String reason) {
        this.notification = notification;
        this.isAccepted = isAccepted;
        this.reason = reason;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("notification", notification)
                .add("isAccepted", isAccepted)
                .add("reason", reason)
                .toString();
    }
}
