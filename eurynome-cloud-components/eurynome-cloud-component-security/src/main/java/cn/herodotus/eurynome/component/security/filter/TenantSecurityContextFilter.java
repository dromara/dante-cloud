package cn.herodotus.eurynome.component.security.filter;

import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.component.common.enums.ResultStatus;
import cn.herodotus.eurynome.component.security.utils.ThreadLocalContextUtils;
import cn.herodotus.eurynome.component.security.utils.WebUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * <p> Description : TODO </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/5 15:09
 */
@Slf4j
@Order(FilterOrder.TENANT_IDENTIFICATION_FILTER)
public class TenantSecurityContextFilter extends GenericFilterBean {

    private static final List<String> params = Arrays.asList("client_id", "tenant_id", "clientId", "tenantId");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        String url = WebUtils.toHttp(request).getRequestURI();

        if (WebUtils.isStaticResources(url)) {
            log.debug("[Herodotus] |- Is Static Resource : [{}], Passed!", url);
            filterChain.doFilter(request, response);
        }

        // If the request is already authenticated we can assume that this
        // filter is not needed
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            filterChain.doFilter(request, response);
        }

        // Clear tenant security context holder, and if it's a logout request then clear tenant attribute from the session
        ThreadLocalContextUtils.clear();

        // 解析 Tenant ID
        String tenantId = null;

        String contentType = request.getContentType();
        if (StringUtils.contains(contentType, MediaType.APPLICATION_JSON_VALUE)) {
            tenantId = searchTenantIdFromPayload(request);
        } else {
            tenantId = searchTenantIdFromParameter(request);
        }

        if (StringUtils.isNotBlank(tenantId) && ObjectUtils.isNotEmpty(filterChain)) {
            ThreadLocalContextUtils.set("tenantId", tenantId);
            log.debug("[Herodotus] |- Request tenantId is : [{}]", tenantId);

        }

        filterChain.doFilter(request, response);

    }

    private String searchTenantIdFromParameter(ServletRequest request) {
        String tenantId = null;

        for (String param : params) {
            String value = request.getParameter(param);
            if (StringUtils.isNotBlank(value)) {
                tenantId = value;
                break;
            }
        }

        return tenantId;
    }

    private String searchTenantIdFromPayload(ServletRequest request) {
        String tenantId = null;

        String payload = WebUtils.getRequestPayload(request);
        if (StringUtils.isNotBlank(payload)) {
            JSONObject jsonObject = JSONObject.parseObject(payload);
            if (ObjectUtils.isNotEmpty(jsonObject)) {
                for (String param : params) {
                    String value = jsonObject.getString(param);
                    if (StringUtils.isNotBlank(value)) {
                        tenantId = value;
                        break;
                    }
                }
            }
        }

        return tenantId;
    }

}
