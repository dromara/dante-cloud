package cn.herodotus.eurynome.integration.social.configuration;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import cn.binarywang.wx.miniapp.message.WxMaMessageRouter;
import cn.herodotus.eurynome.integration.social.properties.WxappProperties;
import cn.herodotus.eurynome.integration.social.service.wxapp.WxappLogHandler;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxRuntimeException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>Description: 微信小程序后台接口 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/3/29 9:27
 */
@Slf4j
@EnableConfigurationProperties(WxappProperties.class)
@ComponentScan(basePackages = {
        "cn.herodotus.eurynome.integration.social.service.wxapp"
})
public class WxappConfiguration {

    private final WxappProperties wxappProperties;
    private final WxappLogHandler wxappLogHandler;

    private static final Map<String, WxMaMessageRouter> wxMaMessagerouters = Maps.newHashMap();
    private static Map<String, WxMaService> wxMaServices = Maps.newHashMap();

    @Autowired
    public WxappConfiguration(WxappProperties wxappProperties, WxappLogHandler wxappLogHandler) {
        this.wxappProperties = wxappProperties;
        this.wxappLogHandler = wxappLogHandler;
    }

    @PostConstruct
    public void init() {
        List<WxappProperties.Config> configs = this.wxappProperties.getConfigs();
        if (configs == null) {
            throw new WxRuntimeException("Weixin Mini App Configuraiton is not setting!");
        }

        wxMaServices = configs.stream()
                .map(a -> {
                    WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
//                WxMaDefaultConfigImpl config = new WxMaRedisConfigImpl(new JedisPool());
                    // 使用上面的配置时，需要同时引入jedis-lock的依赖，否则会报类无法找到的异常
                    config.setAppid(a.getAppId());
                    config.setSecret(a.getSecret());
                    config.setToken(a.getToken());
                    config.setAesKey(a.getAesKey());
                    config.setMsgDataFormat(a.getMessageDataFormat());

                    WxMaService service = new WxMaServiceImpl();
                    service.setWxMaConfig(config);
                    wxMaMessagerouters.put(a.getAppId(), this.newRouter(service));
                    return service;
                }).collect(Collectors.toMap(s -> s.getWxMaConfig().getAppid(), a -> a));

        log.info("[Eurynome] |- Bean [Weixin Mini App] Auto Configure.");
    }

    public static WxMaService getWxMaService(String appid) {
        WxMaService wxMaService = wxMaServices.get(appid);
        if (ObjectUtils.isEmpty(wxMaService)) {
            throw new IllegalArgumentException(String.format("Cannot find the configuration of appid=[%s], please check!", appid));
        }

        return wxMaService;
    }

    private WxMaMessageRouter newRouter(WxMaService wxMaService) {
        final WxMaMessageRouter router = new WxMaMessageRouter(wxMaService);
        router.rule().handler(wxappLogHandler).next();
        return router;
    }

    public static WxMaMessageRouter getRouter(String appid) {
        return wxMaMessagerouters.get(appid);
    }
}
