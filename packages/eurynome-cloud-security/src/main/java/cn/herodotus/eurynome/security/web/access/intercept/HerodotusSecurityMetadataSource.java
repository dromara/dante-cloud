package cn.herodotus.eurynome.security.web.access.intercept;

import cn.herodotus.eurynome.localstorage.entity.SecurityMetadata;
import cn.herodotus.eurynome.localstorage.service.SecurityMetadataService;
import cn.herodotus.eurynome.security.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>Description: TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/5/20 12:24
 */
@Slf4j
public class HerodotusSecurityMetadataSource implements FilterInvocationSecurityMetadataSource, InitializingBean {

    private Map<RequestMatcher, Collection<ConfigAttribute>> requestMatchers = new LinkedHashMap<>();

    private SecurityMetadataService securityMetadataService;

    public void setSecurityMetadataService(SecurityMetadataService securityMetadataService) {
        this.securityMetadataService = securityMetadataService;
    }

    /**
     * 判定用户请求的url是否在权限表中
     * 如果在权限表中，则返回给decide方法，用来判定用户是否有此权限
     * 如果不在权限表中则放行
     *
     * 如果getAttributes(Object object)方法返回null的话，意味着当前这个请求不需要任何角色就能访问，甚至不需要登录
     * 此方法返回的是Collection<ConfigAttribute>。主要考虑是url路径的多重匹配，例如/user/**，逻辑上可以匹配多个路径。
     *
     * @see：https://blog.csdn.net/u012373815/article/details/54633046
     *
     * @param object 当前请求
     * @return 根据当前请求匹配的配置信息，此处为权限信息
     * @throws IllegalArgumentException 参数错误
     */

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        //object 中包含用户请求的request 信息
        final HttpServletRequest request = ((FilterInvocation) object).getRequest();

        String url = request.getRequestURI();
        String method = request.getMethod();

        if (WebUtils.isStaticResources(url)) {
            log.debug("[Luban] |- Is Static Resource : [{}], Passed!", url);
            return null;
        }

//        if (WebUtils.isPathMatch(securityProperities.getInterceptor().getWhiteList(), url)) {
//            log.debug("[Luban] |- Is White List Resource : [{}], Passed!", url);
//            return null;
//        }

        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMatchers
                .entrySet()) {
            if (entry.getKey().matches(request)) {
                log.debug("[Luban] |- Current Request is : [{}] - [{}]", url, method);
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> allAttributes = new HashSet<>();

        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMatchers
                .entrySet()) {
            allAttributes.addAll(entry.getValue());
        }

        return allAttributes;
    }

    /**
     * 这个与Shiro Realm中的support原理类似，通过不同的类的判断，例如UsernamepassordToken和 JWTToken，就可以支持多种不同方式
     * @param clazz 认证的类型class
     * @return 是否使用该类
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<SecurityMetadata> securityMetadataCollection = securityMetadataService.findAll();
        if (CollectionUtils.isNotEmpty(securityMetadataCollection)) {
            securityMetadataCollection.forEach(securityMetadata -> {
                if (StringUtils.isNotEmpty(securityMetadata.getUrl())) {
                    RequestMatcher requestMatcher = new AntPathRequestMatcher(securityMetadata.getUrl(), securityMetadata.getRequestMethod());
                    Collection<ConfigAttribute> attributes = Collections.singletonList(new SecurityConfig(securityMetadata.getMetadataCode()));
                    requestMatchers.put(requestMatcher, attributes);
                }
            });
        }
    }
}
