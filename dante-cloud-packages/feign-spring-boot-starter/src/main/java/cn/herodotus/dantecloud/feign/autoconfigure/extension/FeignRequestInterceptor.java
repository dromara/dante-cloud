/*
 * Copyright 2020-2030 码匠君<herodotus@aliyun.com>
 *
 * Dante Engine licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Dante Engine 是 Dante Cloud 系统核心组件库，采用 APACHE LICENSE 2.0 开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1. 请不要删除和修改根目录下的LICENSE文件。
 * 2. 请不要删除和修改 Dante Engine 源码头部的版权声明。
 * 3. 请保留源码和相关描述文件的项目出处，作者声明等。
 * 4. 分发源码时候，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 5. 在修改包名，模块名称，项目代码等时，请注明软件出处 <https://gitee.com/dromara/dante-cloud>
 * 6. 若您的项目无法满足以上几点，可申请商业授权
 */

package cn.herodotus.dantecloud.feign.autoconfigure.extension;

import cn.herodotus.dante.core.constant.HerodotusHeaders;
import cn.herodotus.dante.core.constant.SymbolConstants;
import cn.herodotus.dante.core.context.TenantContextHolder;
import cn.hutool.v7.http.server.servlet.ServletUtil;
import com.google.common.net.HttpHeaders;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collection;
import java.util.Map;

/**
 * <p>Description: 自定义FeignRequestInterceptor </p>
 *
 * @author : gengwei.zheng
 * @date : 2020/6/8 12:01
 */
public class FeignRequestInterceptor implements RequestInterceptor {

    private static final Logger log = LoggerFactory.getLogger(FeignRequestInterceptor.class);

    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest httpServletRequest = getHttpServletRequest();

        if (httpServletRequest != null) {
            Map<String, String> headers = ServletUtil.getHeaderMap(httpServletRequest);
            // 传递所有请求头,防止部分丢失
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                // 跳过content-length值的复制。因为服务之间调用需要携带一些用户信息之类的 所以实现了Feign的RequestInterceptor拦截器复制请求头，复制的时候是所有头都复制的,可能导致Content-length长度跟body不一致
                // @see https://blog.csdn.net/qq_39986681/article/details/107138740
                if (Strings.CI.equals(key, HttpHeaders.CONTENT_LENGTH)) {
                    continue;
                }

                // 如果 RequestTemplate 已经包含了 content_type, 那么就不传递 content_type
                // 以防上游 content_type 与下游不同，传递后产生干扰。
                if (Strings.CI.equals(key, HttpHeaders.CONTENT_TYPE)) {
                    Map<String, Collection<String>> requestHeaders = requestTemplate.headers();
                    if (requestHeaders.containsKey(HttpHeaders.CONTENT_TYPE)) {
                        continue;
                    }
                }

                // 解决 UserAgent 信息被修改后，AppleWebKit/537.36 (KHTML,like Gecko)部分存在非法字符的问题
                if (Strings.CI.equals(key, HttpHeaders.USER_AGENT)) {
                    value = Strings.CS.replace(value, SymbolConstants.NEW_LINE, SymbolConstants.BLANK);
                    entry.setValue(value);
                }

                // 解决使用edge浏览器并使用feign，报错Unexpected char 0x0a at 25 in sec-ch-ua value: "Microsoft Edge";v="107...
                if (Strings.CI.equals(key, HttpHeaders.SEC_CH_UA)) {
                    value = Strings.CS.replace(value, SymbolConstants.NEW_LINE, SymbolConstants.BLANK);
                    entry.setValue(value);
                }

                requestTemplate.header(key, value);
            }

            log.debug("[Herodotus] |- Feign Request Interceptor copy all need transfer header!");

            // 检查 Tenant Id 的可用性。
            String tenantIdHeader = HerodotusHeaders.X_HERODOTUS_TENANT_ID;
            if (!headers.containsKey(tenantIdHeader)) {
                String tenantId = TenantContextHolder.getTenantId();
                if (StringUtils.isNotBlank(tenantId)) {
                    log.info("[Herodotus] |- Feign Request Interceptor Tenant is : {}", tenantId);
                    requestTemplate.header(tenantIdHeader, tenantId);
                }
            }

            // 微服务之间传递的唯一标识,区分大小写所以通过httpServletRequest查询
            if (headers.containsKey(HttpHeaders.X_REQUEST_ID)) {
                String traceId = headers.get(HttpHeaders.X_REQUEST_ID);
                MDC.put("traceId", traceId);
                log.info("[Herodotus] |- Feign Request Interceptor Trace: {}", traceId);
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
