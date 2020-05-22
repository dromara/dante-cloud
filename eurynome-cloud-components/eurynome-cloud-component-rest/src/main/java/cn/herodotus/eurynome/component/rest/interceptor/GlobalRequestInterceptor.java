package cn.herodotus.eurynome.component.rest.interceptor;

import cn.herodotus.eurynome.common.constants.SecurityConstants;
import cn.herodotus.eurynome.common.domain.Result;
import cn.herodotus.eurynome.common.enums.ResultStatus;
import cn.herodotus.eurynome.component.rest.security.ThroughGatewayTrace;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * <p> Description : 服务全局拦截器，用于控制所有请求都通过gateway进行访问 </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/3/3 17:55
 */
@Slf4j
public class GlobalRequestInterceptor implements HandlerInterceptor {

    private ThroughGatewayTrace throughGatewayTrace;

    public GlobalRequestInterceptor(ThroughGatewayTrace throughGatewayTrace) {
        this.throughGatewayTrace = throughGatewayTrace;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!throughGatewayTrace.isAccessedThroughGateway()) {
            log.trace("[Herodotus] |- GlobalInterceptor Current Service set isAccessedThroughGateway to false!");
            return true;
        }

        String secretKey = request.getHeader(SecurityConstants.GATEWAY_TRACE_HEADER);
        if (StringUtils.isNotBlank(secretKey)) {
            String key = throughGatewayTrace.get(SecurityConstants.GATEWAY_STORAGE_KEY);
            if (StringUtils.isNotBlank(key) && secretKey.equals(key)) {
                log.debug("[Herodotus] |- GlobalInterceptor Request is come from gateway!");
                return true;
            }
        }

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(new Result<String>().failed().type(ResultStatus.BAD_REQUEST).message("Disable access from outside the gateway！")));

        log.warn("[Herodotus] |- GlobalInterceptor Request is not come from gateway!");
        return false;
    }
}
