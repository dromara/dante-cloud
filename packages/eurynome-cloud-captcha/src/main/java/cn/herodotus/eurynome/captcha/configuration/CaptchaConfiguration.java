/*
 * Copyright (c) 2019-2021 Gengwei Zheng (herodotus@aliyun.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Project Name: eurynome-cloud
 * Module Name: eurynome-cloud-captcha
 * File Name: CaptchaConfiguration.java
 * Author: gengwei.zheng
 * Date: 2021/12/17 21:59:17
 */

package cn.herodotus.eurynome.captcha.configuration;

import cn.herodotus.eurynome.cache.configuration.CacheConfiguration;
import cn.herodotus.eurynome.captcha.definition.enums.CaptchaCategory;
import cn.herodotus.eurynome.captcha.properties.CaptchaProperties;
import cn.herodotus.eurynome.captcha.provider.ResourceProvider;
import cn.herodotus.eurynome.captcha.renderer.CaptchaRendererFactory;
import cn.herodotus.eurynome.captcha.renderer.behavior.JigsawCaptchaRenderer;
import cn.herodotus.eurynome.captcha.renderer.behavior.WordClickCaptchaRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * <p>Description: 验证码配置类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/12/17 21:59
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({CaptchaProperties.class})
@AutoConfigureAfter({CacheConfiguration.class})
public class CaptchaConfiguration {

    private static final Logger log = LoggerFactory.getLogger(CacheConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("[Herodotus] |- Component [Herodotus Captcha] Auto Configure.");
    }

    @Bean
    public ResourceProvider resourceProvider(CaptchaProperties captchaProperties) {
        ResourceProvider resourceProvider = new ResourceProvider(captchaProperties);
        log.trace("[Herodotus] |- Bean [Resource Provider] Auto Configure.");
        return resourceProvider;
    }

    @Bean(CaptchaCategory.JIGSAW_CAPTCHA)
    @ConditionalOnBean(ResourceProvider.class)
    public JigsawCaptchaRenderer jigsawCaptchaRenderer(ResourceProvider resourceProvider) {
        JigsawCaptchaRenderer jigsawCaptchaRenderer = new JigsawCaptchaRenderer();
        jigsawCaptchaRenderer.setResourceProvider(resourceProvider);
        log.trace("[Herodotus] |- Bean [Jigsaw Captcha Renderer] Auto Configure.");
        return jigsawCaptchaRenderer;
    }

    @Bean(CaptchaCategory.WORD_CLICK_CAPTCHA)
    @ConditionalOnBean(ResourceProvider.class)
    public WordClickCaptchaRenderer wordClickCaptchaRenderer(ResourceProvider resourceProvider) {
        WordClickCaptchaRenderer wordClickCaptchaRenderer = new WordClickCaptchaRenderer();
        wordClickCaptchaRenderer.setResourceProvider(resourceProvider);
        log.trace("[Herodotus] |- Bean [Word Click Captcha Renderer] Auto Configure.");
        return wordClickCaptchaRenderer;
    }

    @Bean
    public CaptchaRendererFactory captchaFactory() {
        CaptchaRendererFactory captchaRendererFactory = new CaptchaRendererFactory();
        log.trace("[Herodotus] |- Bean [Captcha Factory] Auto Configure.");
        return captchaRendererFactory;
    }
}