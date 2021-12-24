/*
 * Copyright (c) 2019-2021 Gengwei Zheng(herodotus@aliyun.com)
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
 * Module Name: eurynome-cloud-oauth
 * File Name: FormLoginWebAuthenticationDetails.java
 * Author: gengwei.zheng
 * Date: 2021/05/07 11:28:07
 */

package cn.herodotus.eurynome.oauth.authentication;

import cn.herodotus.eurynome.oauth.utils.SymmetricUtils;
import cn.herodotus.eurynome.security.properties.SecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p> Description : 自定义WebAuthenticationDetails，用于提供Login额外参数检测 </p>
 *
 * @author : gengwei.zheng
 * @date : 2019/11/29 14:29
 */
public class FormLoginWebAuthenticationDetails extends WebAuthenticationDetails {

    private final SecurityProperties securityProperties;
    private String code = null;
    private String identity = null;
    private String category = null;

    /**
     * Records the remote address and will also set the session Id if a session already
     * exists (it won't create one).
     *
     * @param request that the authentication request was received from
     */
    public FormLoginWebAuthenticationDetails(HttpServletRequest request, SecurityProperties securityProperties) {
        super(request);
        this.securityProperties = securityProperties;
        init(request);
    }

    private void init(HttpServletRequest request) {
        String encryptedCode = request.getParameter(securityProperties.getCaptcha().getCaptchaParameter());
        String key = request.getParameter("symmetric");

        HttpSession session = request.getSession();
        this.identity = session.getId();

        this.category = securityProperties.getCaptcha().getCategory();

        if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(encryptedCode)) {
            byte[] byteKey = SymmetricUtils.getDecryptedSymmetricKey(key);
            this.code = SymmetricUtils.decrypt(encryptedCode, byteKey);
        }
    }

    public String getCode() {
        return code;
    }

    public String getIdentity() {
        return identity;
    }

    public String getCategory() {
        return category;
    }

    public boolean isClose() {
        return securityProperties.getCaptcha().isClosed();
    }
}
