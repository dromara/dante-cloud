/*
 * Copyright 2019-2020 the original author or authors.
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
 * Project Name: luban-cloud
 * Module Name: luban-cloud-component-security
 * File Name: SecurityProperities.java
 * Author: gengwei.zheng
 * Date: 2020/2/3 下午5:33
 * LastModified: 2020/1/29 下午4:08
 */

package cn.herodotus.eurynome.security.properties;

import cn.herodotus.eurynome.common.constants.SymbolConstants;
import cn.herodotus.eurynome.common.enums.captcha.CaptchaFont;
import cn.herodotus.eurynome.common.enums.captcha.CaptchaLetterType;
import cn.herodotus.eurynome.common.enums.captcha.CaptchaType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p> Description : 多出都需要使用Security的配置信息，所以放到data组件中 </p>
 * <p>
 * loginPage()： 自定义登录页面
 * loginProcessingUrl()：将用户名和密码提交到的URL
 * defaultSuccessUrl()： 成功登录后跳转的URL。 如果是直接从登录页面登录，会跳转到该URL；如果是从其他页面跳转到登录页面，登录后会跳转到原来页面。可设置true来任何时候到跳转该URL。
 * successForwardUrl()：成功登录后重定向的URL
 * failureUrl()：登录失败后跳转的URL，指定的路径要能匿名访问
 * failureForwardUrl()：登录失败后重定向的URL
 *
 * @author : gengwei.zheng
 * @date : 2019/11/28 13:08
 */
@Slf4j
@ConfigurationProperties(prefix = "herodotus.platform.security")
public class SecurityProperties implements Serializable {

    private String signingKey = "eurynome-cloud";
    private String verifierKey = "alibaba-cloud";

    private Login login = new Login();

    private RememberMe rememberMe = new RememberMe();

    private VerificationCode verificationCode = new VerificationCode();

    private Interceptor interceptor = new Interceptor();

    public SecurityProperties() {
        log.debug("[Eurynome] |- Properties [Security] is Enabled.");
    }

    public String getSigningKey() {
        return signingKey;
    }

    public void setSigningKey(String signingKey) {
        this.signingKey = signingKey;
    }

    public String getVerifierKey() {
        return verifierKey;
    }

    public void setVerifierKey(String verifierKey) {
        this.verifierKey = verifierKey;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public RememberMe getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(RememberMe rememberMe) {
        this.rememberMe = rememberMe;
    }

    public VerificationCode getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(VerificationCode verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Interceptor getInterceptor() {
        return interceptor;
    }

    public void setInterceptor(Interceptor interceptor) {
        this.interceptor = interceptor;
    }

    public static class Login implements Serializable {
        private String usernameParameter = "username";
        private String passwordParameter = "password";
        private String loginUrl = "/login";
        private String loginProcessingUrl = loginUrl;
        private String defaultSuccessUrl = SymbolConstants.FORWARD_SLASH;
        private String successForwardUrl;
        private String failureUrl = loginUrl;
        private String failureForwardUrl;

        public String getUsernameParameter() {
            return usernameParameter;
        }

        public void setUsernameParameter(String usernameParameter) {
            this.usernameParameter = usernameParameter;
        }

        public String getPasswordParameter() {
            return passwordParameter;
        }

        public void setPasswordParameter(String passwordParameter) {
            this.passwordParameter = passwordParameter;
        }

        public String getLoginUrl() {
            return loginUrl;
        }

        public void setLoginUrl(String loginUrl) {
            this.loginUrl = loginUrl;
        }

        public String getLoginProcessingUrl() {
            return loginProcessingUrl;
        }

        public void setLoginProcessingUrl(String loginProcessingUrl) {
            this.loginProcessingUrl = loginProcessingUrl;
        }

        public String getDefaultSuccessUrl() {
            return defaultSuccessUrl;
        }

        public void setDefaultSuccessUrl(String defaultSuccessUrl) {
            this.defaultSuccessUrl = defaultSuccessUrl;
        }

        public String getSuccessForwardUrl() {
            return successForwardUrl;
        }

        public void setSuccessForwardUrl(String successForwardUrl) {
            this.successForwardUrl = successForwardUrl;
        }

        public String getFailureUrl() {
            return failureUrl;
        }

        public void setFailureUrl(String failureUrl) {
            this.failureUrl = failureUrl;
        }

        public String getFailureForwardUrl() {
            return failureForwardUrl;
        }

        public void setFailureForwardUrl(String failureForwardUrl) {
            this.failureForwardUrl = failureForwardUrl;
        }
    }

    public static class RememberMe implements Serializable {
        private String cookieName = "remember-me";
        private Integer validitySeconds = 3600;

        public String getCookieName() {
            return cookieName;
        }

        public void setCookieName(String cookieName) {
            this.cookieName = cookieName;
        }

        public Integer getValiditySeconds() {
            return validitySeconds;
        }

        public void setValiditySeconds(Integer validitySeconds) {
            this.validitySeconds = validitySeconds;
        }
    }

    public static class VerificationCode implements Serializable {

        private String sessionAttribute = "captcha";
        private boolean closed = false;
        private String verificationCodeParameter = sessionAttribute;
        private int width = 130;
        private int height = 48;
        private int length = 5;
        private CaptchaFont captchaFont = CaptchaFont.FONT_4;
        private CaptchaLetterType captchaLetterType = CaptchaLetterType.DEFAULT;
        private CaptchaType captchaType = CaptchaType.LETTERS;

        public String getSessionAttribute() {
            return sessionAttribute;
        }

        public void setSessionAttribute(String sessionAttribute) {
            this.sessionAttribute = sessionAttribute;
        }

        public String getVerificationCodeParameter() {
            return verificationCodeParameter;
        }

        public void setVerificationCodeParameter(String verificationCodeParameter) {
            this.verificationCodeParameter = verificationCodeParameter;
        }

        public boolean isClosed() {
            return closed;
        }

        public void setClosed(boolean closed) {
            this.closed = closed;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public CaptchaFont getCaptchaFont() {
            return captchaFont;
        }

        public void setCaptchaFont(CaptchaFont captchaFont) {
            this.captchaFont = captchaFont;
        }

        public CaptchaLetterType getCaptchaLetterType() {
            return captchaLetterType;
        }

        public void setCaptchaLetterType(CaptchaLetterType captchaLetterType) {
            this.captchaLetterType = captchaLetterType;
        }

        public CaptchaType getCaptchaType() {
            return captchaType;
        }

        public void setCaptchaType(CaptchaType captchaType) {
            this.captchaType = captchaType;
        }
    }

    public static class Interceptor implements Serializable {
        /**
         * 开启授权检查
         */
        private boolean openAuthorizationCheck = true;

        /**
         * 白名单，服务接口
         */
        private List<String> whitelist = new ArrayList<>();

        /**
         * Web Mvc 过滤的静态资源
         */
        private List<String> staticResource = new ArrayList<>();

        public boolean isOpenAuthorizationCheck() {
            return openAuthorizationCheck;
        }

        public void setOpenAuthorizationCheck(boolean openAuthorizationCheck) {
            this.openAuthorizationCheck = openAuthorizationCheck;
        }

        public List<String> getWhitelist() {
            return whitelist;
        }

        public void setWhitelist(List<String> whitelist) {
            this.whitelist = whitelist;
        }

        public List<String> getStaticResource() {
            return staticResource;
        }

        public void setStaticResource(List<String> staticResource) {
            this.staticResource = staticResource;
        }
    }
}
