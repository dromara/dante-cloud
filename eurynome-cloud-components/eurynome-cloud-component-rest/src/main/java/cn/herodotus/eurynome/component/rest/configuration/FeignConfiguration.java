package cn.herodotus.eurynome.component.rest.configuration;

import cn.herodotus.eurynome.component.common.constants.SecurityConstants;
import cn.herodotus.eurynome.component.rest.security.ThroughGatewayTrace;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * <p> Description : 为Feign调用，添加Gateway请求标识 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/4 12:09
 */
@Configuration
public class FeignConfiguration implements RequestInterceptor {

    @Autowired
    private ThroughGatewayTrace throughGatewayTrace;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 如果开启了gateway 的MustBeAccessed设置，才会在请求中设置参数
        if (throughGatewayTrace.isAccessedThroughGateway()) {
            String trace = throughGatewayTrace.get(SecurityConstants.GATEWAY_STORAGE_KEY);
            if (ObjectUtils.isNotEmpty(requestTemplate) && StringUtils.isNotBlank(trace)) {
                requestTemplate.header(SecurityConstants.GATEWAY_TRACE_HEADER, trace);
            }
        }
    }
}
