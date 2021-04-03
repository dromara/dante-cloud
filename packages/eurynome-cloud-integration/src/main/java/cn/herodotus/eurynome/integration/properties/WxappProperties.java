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
 * File Name: WxappProperties.java
 * Author: gengwei.zheng
 * Date: 2021/4/3 下午7:40
 * LastModified: 2021/4/3 下午2:41
 */

package cn.herodotus.eurynome.integration.properties;

import com.google.common.base.MoreObjects;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Project: eurynome-cloud </p>
 * <p>File: WxappProperties </p>
 *
 * <p>Description: 微信小程序配置属性 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/3/28 15:54
 */
@ConfigurationProperties(prefix = "herodotus.social.wx.wxapp")
public class WxappProperties implements Serializable {

    /**
     * 默认App Id
     */
    private String defaultAppId;

    /**
     * 小程序配置列表
     */
    private List<Config> configs;

    public String getDefaultAppId() {
        return defaultAppId;
    }

    public void setDefaultAppId(String defaultAppId) {
        this.defaultAppId = defaultAppId;
    }

    public List<Config> getConfigs() {
        return configs;
    }

    public void setConfigs(List<Config> configs) {
        this.configs = configs;
    }

    public static class Config {
        /**
         * 设置微信小程序的appid
         */
        private String appid;

        /**
         * 设置微信小程序的Secret
         */
        private String secret;

        /**
         * 设置微信小程序消息服务器配置的token
         */
        private String token;

        /**
         * 设置微信小程序消息服务器配置的EncodingAESKey
         */
        private String aesKey;

        /**
         * 消息格式，XML或者JSON
         */
        private String messageDataFormat;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getAesKey() {
            return aesKey;
        }

        public void setAesKey(String aesKey) {
            this.aesKey = aesKey;
        }

        public String getMessageDataFormat() {
            return messageDataFormat;
        }

        public void setMessageDataFormat(String messageDataFormat) {
            this.messageDataFormat = messageDataFormat;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("appid", appid)
                    .add("secret", secret)
                    .add("token", token)
                    .add("aesKey", aesKey)
                    .add("messageDataFormat", messageDataFormat)
                    .toString();
        }
    }
}
