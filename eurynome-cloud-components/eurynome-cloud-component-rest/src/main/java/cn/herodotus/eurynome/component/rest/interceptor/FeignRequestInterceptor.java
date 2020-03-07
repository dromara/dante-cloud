package cn.herodotus.eurynome.component.rest.interceptor;

import cn.herodotus.eurynome.component.common.constants.SecurityConstants;
import cn.herodotus.eurynome.component.rest.security.ThroughGatewayTrace;
import cn.hutool.extra.servlet.ServletUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;

/**
 * <p> Description : 为Feign调用，添加Gateway请求标识 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/4 12:09
 */
@Slf4j
public class FeignRequestInterceptor implements RequestInterceptor {

    private ThroughGatewayTrace throughGatewayTrace;

    public FeignRequestInterceptor(ThroughGatewayTrace throughGatewayTrace) {
        this.throughGatewayTrace = throughGatewayTrace;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest httpServletRequest = getHttpServletRequest();

        if (httpServletRequest != null) {
            Map<String, String> headers = ServletUtil.getHeaderMap(httpServletRequest);
            // 传递所有请求头,防止部分丢失
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                requestTemplate.header(entry.getKey(), entry.getValue());
            }
        }

        // 如果开启了gateway 的MustBeAccessed设置，才会在请求中设置参数
        if (throughGatewayTrace.isAccessedThroughGateway()) {
            String trace = throughGatewayTrace.get(SecurityConstants.GATEWAY_STORAGE_KEY);
            if (ObjectUtils.isNotEmpty(requestTemplate) && StringUtils.isNotBlank(trace)) {
                requestTemplate.header(SecurityConstants.GATEWAY_TRACE_HEADER, trace);
            }
        }

        log.trace("[Herodotus] |- Feign Request Interceptor [{}]", requestTemplate.toString());
    }

    private HttpServletRequest getHttpServletRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            log.error("[Herodotus] |- Feign Request Interceptor can not get Request.");
            return null;
        }
    }
}
