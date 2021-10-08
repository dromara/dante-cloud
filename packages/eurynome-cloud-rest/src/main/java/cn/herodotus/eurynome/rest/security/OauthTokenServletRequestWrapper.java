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
 * Module Name: eurynome-cloud-rest
 * File Name: OauthTokenServletRequestWrapper.java
 * Author: gengwei.zheng
 * Date: 2021/10/08 19:19:08
 */

package cn.herodotus.eurynome.rest.security;

import cn.herodotus.eurynome.rest.crypto.InterfaceCryptoProcessor;
import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>Description: OauthToken 请求参数包装器 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/10/8 19:19
 */
public class OauthTokenServletRequestWrapper extends HttpServletRequestWrapper {

    private static final Logger log = LoggerFactory.getLogger(OauthTokenServletRequestWrapper.class);
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    private final InterfaceCryptoProcessor interfaceCryptoProcessor;
    private final String sessionId;

    public OauthTokenServletRequestWrapper(HttpServletRequest request, String sessionId, InterfaceCryptoProcessor interfaceCryptoProcessor) {
        super(request);
        this.sessionId = sessionId;
        this.interfaceCryptoProcessor = interfaceCryptoProcessor;
    }

    private String decrypt(String name, String sessionId, String content) {
        byte[] bytes = interfaceCryptoProcessor.decrypt(sessionId, content);
        String result = StrUtil.str(bytes, StandardCharsets.UTF_8);
        log.debug("[Herodotus] |- Oauth Token Parameter [{}] value [{}] is decrypted to [{}].", name, content, result);
        return result;
    }

    private String[] decrypt(String key, String[] parameters) {
        if (StringUtils.equalsIgnoreCase(key, USERNAME) || StringUtils.equalsIgnoreCase(key, PASSWORD)) {
            List<String> cleanParameters = Arrays.stream(parameters).map(parameter -> decrypt(key, sessionId, parameter)).collect(Collectors.toList());
            String[] results = new String[cleanParameters.size()];
            return cleanParameters.toArray(results);
        } else {
            return parameters;
        }
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> parameterMap = super.getParameterMap();
        return parameterMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> decrypt(entry.getKey(), entry.getValue())));
    }
}
