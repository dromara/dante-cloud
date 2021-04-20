package cn.herodotus.eurynome.integration.push.service.jpush;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.DefaultResult;
import cn.jpush.api.JPushClient;
import cn.jpush.api.device.AliasDeviceListResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>Description: 极光推送推送消息服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/20 9:13
 */
@Slf4j
@Service
public class JPushDeviceService {

    @Autowired
    private JPushClient jPushClient;

    /**
     * 查询指定别名下的设备Registration ID
     *
     * @param alias 待查询的别名
     * @return 关联的设备Registration ID列表
     */
    public AliasDeviceListResult getDevicesByAlias(String alias) {
        try {
            return jPushClient.getAliasDeviceList(alias, null);
        } catch (APIConnectionException e) {
            log.error("[Eurynome] |- JPush Server Connnect failed, when getAliasDeviceList!", e);
        } catch (APIRequestException e) {
            log.error("[Eurynome] |- The JPush Server is not responding, when getAliasDeviceList", e);
            log.error("[Eurynome] |- The error detail is : status={}, errorCode={}, errorMessage={}", e.getStatus(), e.getErrorCode(), e.getErrorMessage());
        }

        return null;
    }


    public DefaultResult bindMobile(String registrationId, String mobile) {
        try {
            return jPushClient.bindMobile(registrationId, mobile);
        } catch (APIConnectionException e) {
            log.error("[Eurynome] |- JPush Server Connnect failed, when bindMobile!", e);
        } catch (APIRequestException e) {
            log.error("[Eurynome] |- The JPush Server is not responding, when bindMobile", e);
            log.error("[Eurynome] |- The error detail is : status={}, errorCode={}, errorMessage={}", e.getStatus(), e.getErrorCode(), e.getErrorMessage());
        }

        return null;
    }
}
