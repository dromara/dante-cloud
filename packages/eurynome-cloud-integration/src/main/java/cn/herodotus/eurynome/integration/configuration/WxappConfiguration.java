/*
 * Copyright (c) 2019-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-integration
 * File Name: WxappConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/4/3 下午7:42
 * LastModified: 2021/3/28 下午5:00
 */

package cn.herodotus.eurynome.integration.configuration;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import cn.binarywang.wx.miniapp.message.WxMaMessageRouter;
import cn.herodotus.eurynome.integration.properties.WxappProperties;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxRuntimeException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: WxappConfiguration </p>
 *
 * <p>Description: 微信小程序启动配置类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/3/28 15:48
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(WxappProperties.class)
@ComponentScan(basePackages = {
        "cn.herodotus.eurynome.integration.service.wxapp",
        "cn.herodotus.eurynome.integration.controller.wxapp"
})
public class WxappConfiguration {

    private final WxappProperties wxappProperties;

    private static final Map<String, WxMaMessageRouter> wxMaMessagerouters = Maps.newHashMap();
    private static Map<String, WxMaService> wxMaServices = Maps.newHashMap();

    @Autowired
    public WxappConfiguration(WxappProperties wxappProperties) {
        this.wxappProperties = wxappProperties;
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
                    config.setAppid(a.getAppid());
                    config.setSecret(a.getSecret());
                    config.setToken(a.getToken());
                    config.setAesKey(a.getAesKey());
                    config.setMsgDataFormat(a.getMessageDataFormat());

                    WxMaService service = new WxMaServiceImpl();
                    service.setWxMaConfig(config);
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
}
