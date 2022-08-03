/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2019-2022 ZHENGGENGWEI<码匠君>. All rights reserved.
 *
 * - Author: ZHENGGENGWEI<码匠君>
 * - Contact: herodotus@aliyun.com
 * - Blog and source code availability: https://gitee.com/herodotus/herodotus-cloud
 */

package cn.herodotus.dante.module.security.processor;

import cn.herodotus.engine.assistant.core.constants.HttpHeaders;
import cn.herodotus.engine.oauth2.core.processor.HerodotusSecurityConfigureHandler;
import cn.herodotus.engine.oauth2.metadata.matcher.HerodotusRequestMatcher;
import cn.herodotus.engine.oauth2.metadata.storage.SecurityMetadataLocalStorage;
import cn.herodotus.engine.web.core.utils.WebUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>Description: 自定义 FilterInvocationSecurityMetadataSource </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/20 12:24
 */
public class HerodotusSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private static final Logger log = LoggerFactory.getLogger(HerodotusSecurityMetadataSource.class);

    private SecurityMetadataLocalStorage securityMetadataLocalStorage;
    private HerodotusSecurityConfigureHandler herodotusSecurityConfigureHandler;

    public void setSecurityMetadataLocalStorage(SecurityMetadataLocalStorage securityMetadataLocalStorage) {
        this.securityMetadataLocalStorage = securityMetadataLocalStorage;
    }

    public void setHerodotusSecurityConfigureHandler(HerodotusSecurityConfigureHandler herodotusSecurityConfigureHandler) {
        this.herodotusSecurityConfigureHandler = herodotusSecurityConfigureHandler;
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
     * @see <a href="https://blog.csdn.net/u012373815/article/details/54633046">参考资料</a>
     */

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        //object 中包含用户请求的request 信息
        final HttpServletRequest request = ((FilterInvocation) object).getRequest();

        String url = request.getRequestURI();
        String method = request.getMethod();

        if (WebUtils.isStaticResources(url)) {
            log.trace("[Herodotus] |- Is Static Resource : [{}], Passed!", url);
            return null;
        }

        if (WebUtils.isPathMatch(herodotusSecurityConfigureHandler.getPermitAllArray(), url)) {
            log.trace("[Herodotus] |- Is White List Resource : [{}], Passed!", url);
            return null;
        }

        String feignInnerFlag = request.getHeader(HttpHeaders.X_HERODOTUS_FROM_IN);
        if (StringUtils.isNotBlank(feignInnerFlag)) {
            log.trace("[Herodotus] |- Is Feign Inner Invoke : [{}], Passed!", url);
            return null;
        }

        return findConfigAttribute(url, method, request);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Collection<ConfigAttribute> configAttributes = this.securityMetadataLocalStorage.getAllConfigAttributes();
        log.debug("[Herodotus] |- The count of all configAttributes is : [{}].", configAttributes.size());
        return configAttributes;
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

        log.debug("[Herodotus] |- Current Request is : [{}] - [{}]", url, method);

        Collection<ConfigAttribute> configAttributes = this.securityMetadataLocalStorage.getConfigAttribute(url, method);
        if (CollectionUtils.isNotEmpty(configAttributes)) {
            log.debug("[Herodotus] |- Get configAttributes from local storage for : [{}] - [{}]", url, method);
            return configAttributes;
        } else {
            LinkedHashMap<HerodotusRequestMatcher, Collection<ConfigAttribute>> compatible = this.securityMetadataLocalStorage.getCompatible();
            if (MapUtils.isNotEmpty(compatible)) {
                // 支持含有**通配符的路径搜索
                for (Map.Entry<HerodotusRequestMatcher, Collection<ConfigAttribute>> entry : compatible.entrySet()) {
                    if (entry.getKey().matches(request)) {
                        log.debug("[Herodotus] |- Request match the wildcard [{}] - [{}]", entry.getKey(), entry.getValue());
                        return entry.getValue();
                    }
                }
            }
        }

        return null;
    }
}
