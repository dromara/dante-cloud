/*
 * Copyright (c) 2019-2020 the original author or authors.
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
 * Module Name: eurynome-cloud-security
 * File Name: HerodotusSecurityMetadataSource.java
 * Author: gengwei.zheng
 * Date: 2020/6/8 上午11:56
 * LastModified: 2020/6/7 下午4:19
 */

package cn.herodotus.eurynome.security.authentication.access;

import cn.herodotus.eurynome.common.constants.SymbolConstants;
import cn.herodotus.eurynome.security.definition.RequestMapping;
import cn.herodotus.eurynome.security.properties.SecurityProperties;
import cn.herodotus.eurynome.security.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>Description: 自定义 FilterInvocationSecurityMetadataSource </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/20 12:24
 */
@Slf4j
public class HerodotusSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private final Map<RequestMatcher, Collection<ConfigAttribute>> matchers = new LinkedHashMap<>();
    private final Map<String, Map<String, Collection<ConfigAttribute>>> indexes = new LinkedHashMap<>();
    private final Set<ConfigAttribute> allConfigAttributes = new LinkedHashSet<>();

    private SecurityMetadataLocalStorage securityMetadataLocalStorage;
    private SecurityProperties securityProperties;

    public void setSecurityMetadataLocalStorage(SecurityMetadataLocalStorage securityMetadataLocalStorage) {
        this.securityMetadataLocalStorage = securityMetadataLocalStorage;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    /**
     * 判定用户请求的url是否在权限表中
     * 如果在权限表中，则返回给decide方法，用来判定用户是否有此权限
     * 如果不在权限表中则放行
     * <p>
     * 如果getAttributes(Object object)方法返回null的话，意味着当前这个请求不需要任何角色就能访问，甚至不需要登录
     * 此方法返回的是Collection<ConfigAttribute>。主要考虑是url路径的多重匹配，例如/user/**，逻辑上可以匹配多个路径。
     *
     * @param object 当前请求
     * @return 根据当前请求匹配的配置信息，此处为权限信息
     * @throws IllegalArgumentException 参数错误
     * @see :https://blog.csdn.net/u012373815/article/details/54633046
     */

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        //object 中包含用户请求的request 信息
        final HttpServletRequest request = ((FilterInvocation) object).getRequest();

        String url = request.getRequestURI();
        String method = request.getMethod();

        if(!securityProperties.getInterceptor().isOpenAuthorizationCheck()) {
            log.debug("[Eurynome] |- Properties OpenAuthorizationCheck is false, Passed!");
            return null;
        }

        if (WebUtils.isStaticResources(url)) {
            log.debug("[Eurynome] |- Is Static Resource : [{}], Passed!", url);
            return null;
        }

        if (WebUtils.isPathMatch(securityProperties.getInterceptor().getWhitelist(), url)) {
            log.debug("[Eurynome] |- Is White List Resource : [{}], Passed!", url);
            return null;
        }

        return findConfigAttribute(url, method, request);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        initRequestMappings();
        return allConfigAttributes;
    }

    /**
     * 这个与Shiro Realm中的support原理类似，通过不同的类的判断，例如UsernamepassordToken和 JWTToken，就可以支持多种不同方式
     *
     * @param clazz 认证的类型class
     * @return 是否使用该类
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    private Collection<ConfigAttribute> findConfigAttribute(String url, String method, HttpServletRequest request) {
        initRequestMappings();

        log.debug("[Eurynome] |- Current Request is : [{}] - [{}]", url, method);

        Collection<ConfigAttribute> configAttributes = getFromIndex(url, method);
        if (CollectionUtils.isNotEmpty(configAttributes)) {
            return configAttributes;
        } else {
            for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : matchers.entrySet()) {
                if (entry.getKey().matches(request)) {
                    return entry.getValue();
                }
            }
        }

        return null;
    }

    private void addRequestMapping(RequestMapping requestMapping) {
        String url = requestMapping.getUrl();
        String method = requestMapping.getRequestMethod();
        String code = requestMapping.getMetadataCode();
        if (StringUtils.isNotEmpty(url)) {
            SecurityConfig securityConfig = new SecurityConfig(code);
            if (StringUtils.contains(url, SymbolConstants.STAR)) {
                addMatcher(url, method, securityConfig);
            } else {
                addIndex(url, method, securityConfig);
            }

            allConfigAttributes.add(securityConfig);
        }
    }

    private Collection<ConfigAttribute> getFromIndex(String url, String method) {
        if (indexes.containsKey(url)) {
            Map<String, Collection<ConfigAttribute>> index = indexes.get(url);
            return index.get(method);
        }
        return null;
    }

    private void addIndex(String url, String methodName, SecurityConfig securityConfig) {
        Map<String, Collection<ConfigAttribute>> index = new LinkedHashMap<>();

        if (indexes.containsKey(url)) {
            index = indexes.get(url);
        }

        if (StringUtils.contains(methodName, SymbolConstants.COMMA)) {
            String[] methods = StringUtils.split(methodName, SymbolConstants.COMMA);
            Collection<ConfigAttribute> configAttributes = Collections.singletonList(securityConfig);
            for (String method : methods) {
                index.put(method, configAttributes);
            }
        } else {
            index.put(methodName, Collections.singletonList(securityConfig));
        }

        indexes.put(url, index);
    }

    private void addMatcher(String url, String methodName, SecurityConfig securityConfig) {
        RequestMatcher requestMatcher = new AntPathRequestMatcher(url, methodName);
        matchers.put(requestMatcher, Collections.singletonList(securityConfig));
    }

    private void initRequestMappings() {
        if (MapUtils.isEmpty(matchers) && MapUtils.isEmpty(indexes)) {
            List<RequestMapping> requestMappings = securityMetadataLocalStorage.findAll();
            if (CollectionUtils.isNotEmpty(requestMappings)) {
                requestMappings.forEach(this::addRequestMapping);
            }
        }
    }
}
