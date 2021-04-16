package cn.herodotus.eurynome.integration.content.service.aliyun.scan;

import cn.herodotus.eurynome.integration.content.domain.aliyun.base.CoreRequest;
import cn.herodotus.eurynome.integration.content.domain.aliyun.base.Response;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import com.aliyuncs.AcsResponse;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.RoaAcsRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * <p>Description: 阿里内容审核基础类 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/13 15:16
 */
@Slf4j
public abstract class AbstractScanService {

    @Autowired
    private IAcsClient iAcsClient;

    protected <R extends AcsResponse> String scan(CoreRequest coreRequest, RoaAcsRequest<R> roaAcsRequest) {
        String data = JSON.toJSONString(coreRequest);
        return this.scan(data, roaAcsRequest);
    }

    protected <R extends AcsResponse> String scan(String data, RoaAcsRequest<R> roaAcsRequest) {
        try {
            roaAcsRequest.setHttpContent(data.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8.name(), FormatType.JSON);
            HttpResponse httpResponse = iAcsClient.doAction(roaAcsRequest);
            if (httpResponse.isSuccess()) {
                String response = StrUtil.str(httpResponse.getHttpContent(), StandardCharsets.UTF_8);
                log.debug("[Eurynome] |- Aliyun scan response is: {}", response);
                return response;
            } else {
                log.error("[Eurynome] |- Aliyun scan text cannot get response");
                return null;
            }
        } catch (ClientException e) {
            log.error("[Eurynome] |- Aliyun scan create client got an error!");
            return null;
        }
    }

    protected <P> Response<P> parseResult(String json, Class<P> clazz) {
        return JSON.parseObject(json, new TypeReference<Response<P>>(clazz) {
        });
    }

    protected <P> Response<List<P>> parseListResult(String json, Class<P> clazz) {
        ParameterizedTypeImpl inner = new ParameterizedTypeImpl(new Type[]{clazz}, null, List.class);
        ParameterizedTypeImpl outer = new ParameterizedTypeImpl(new Type[]{inner}, null, Response.class);
        return JSON.parseObject(json, outer);
    }
}
