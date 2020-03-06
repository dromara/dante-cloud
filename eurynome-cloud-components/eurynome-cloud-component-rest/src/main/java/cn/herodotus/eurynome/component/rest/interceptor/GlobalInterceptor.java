package cn.herodotus.eurynome.component.rest.interceptor;

import cn.herodotus.eurynome.component.common.constants.SecurityConstants;
import cn.herodotus.eurynome.component.common.domain.Result;
import cn.herodotus.eurynome.component.common.enums.ResultStatus;
import cn.herodotus.eurynome.component.rest.security.ThroughGatewayTrace;
import com.alibaba.fastjson.JSON;
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
public class GlobalInterceptor implements HandlerInterceptor {

    private ThroughGatewayTrace throughGatewayTrace;

    public GlobalInterceptor(ThroughGatewayTrace throughGatewayTrace) {
        this.throughGatewayTrace = throughGatewayTrace;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!throughGatewayTrace.isAccessedThroughGateway()) {
            return true;
        }

        String secretKey = request.getHeader(SecurityConstants.GATEWAY_TRACE_HEADER);
        if (StringUtils.isNotBlank(secretKey)) {
            String key = throughGatewayTrace.get(SecurityConstants.GATEWAY_STORAGE_KEY);
            if (StringUtils.isNotBlank(secretKey) && secretKey.equals(key)) {
                return true;
            }
        }

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(new Result<String>().failed().type(ResultStatus.BAD_REQUEST).message("Disable access from outside the gateway！")));
        return false;
    }
}
