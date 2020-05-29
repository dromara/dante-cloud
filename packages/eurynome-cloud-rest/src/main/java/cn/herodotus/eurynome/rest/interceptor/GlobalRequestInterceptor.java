package cn.herodotus.eurynome.rest.interceptor;

import cn.herodotus.eurynome.common.domain.Result;
import cn.herodotus.eurynome.common.enums.ResultStatus;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(new Result<String>().failed().type(ResultStatus.BAD_REQUEST).message("Disable access from outside the gateway！")));

        log.warn("[Herodotus] |- GlobalInterceptor Request is not come from gateway!");
        return false;
    }
}
