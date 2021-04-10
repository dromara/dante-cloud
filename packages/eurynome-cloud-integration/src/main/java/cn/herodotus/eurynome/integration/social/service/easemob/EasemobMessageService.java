package cn.herodotus.eurynome.integration.social.service.easemob;

import cn.herodotus.eurynome.integration.social.domain.easemob.Request;
import cn.herodotus.eurynome.integration.social.domain.easemob.Response;
import com.ejlchina.okhttps.OkHttps;
import org.springframework.stereotype.Service;

/**
 * <p>Description: 环信消息API封装服务 </p>
 *
 * @author : gengwei.zheng
 * @date : 2021/4/6 11:44
 */
@Service
public class EasemobMessageService extends AbstractBaseService {

    public Response postMessage(Request request) {
        return http().sync("/messages")
                .bodyType(OkHttps.JSON)
                .addHeader(getTokenHeader())
                .setBodyPara(request)
                .post()
                .getBody()
                .toBean(Response.class);
    }
}
